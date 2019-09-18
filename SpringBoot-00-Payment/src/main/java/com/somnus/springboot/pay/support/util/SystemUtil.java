package com.somnus.springboot.pay.support.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author Somnus
 * @date 2016年1月21日 下午2:51:49
 * @version V1.0
 */
public class SystemUtil {

    private static String ip = null;
    private static String hostName = null;

    private static void init() throws UnknownHostException, SocketException {
        InetAddress  localhost = null;
        try {
            //windows系统
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //linux系统
            localhost = getLocalHost();
        }
        ip = localhost.getHostAddress().toString();
        hostName = localhost.getHostName().toString();
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "本地";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String getLocalIp(){
        if(ip == null){
            try {
                init();
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    public static String getLocalHostName(){
        if(hostName == null){
            try {
                init();
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        }
        return hostName;
    }


    /**
     * linux 如若想用InetAddress.getLocalHost()方式获取本机ip，需要保证以下一点
     * '/etc/hosts'配置中 有一行配置
     *                 			127.0.0.1  主机名
     *  centOS主机名在'/etc/sysconfig/network' 	配置中可以找到
     *  Ubuntu主机名在'/etc/network/interfaces' 	配置中可以找到
     *  不然就只能采取现提供的方法
     * @return String
     */
    private static InetAddress getLocalHost() throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces != null && interfaces.hasMoreElements()) {
            Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
            while (addresses != null && addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (acceptableAddress(address)) {
                    return address;
                }
            }
        }
        throw new UnknownHostException();
    }
    
    private static boolean acceptableAddress(InetAddress address) {
        return address != null && !address.isLoopbackAddress() && !address.isAnyLocalAddress() && !address.isLinkLocalAddress();
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp());
        System.out.println(getLocalHostName());
    }

}
