package com.somnus.properties;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * ClassName:PropertiesUtil <br/>
 * Function: 读取属性配置. <br/>
 * Date:     2015年11月11日 下午1:43:42 <br/>
 *
 * @see
 * @since JDK 1.7
 */
public class PropertiesUtil {

    private static final String CONFIG_FILE = "wxpay/config.properties";

    private static Properties holder;

    static {
        try {
            holder = new Properties();

            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);

            holder.load(is);

        } catch (IOException e) {
            // 报错，系统文件不存在！
            throw new RuntimeException(String.format("系统错误：系统配置文件[%s]不存在", CONFIG_FILE));
        }
    }

    /**
     * @param key
     * @return
     */
    public static String getString(String key) {

        if (holder.containsKey(key)) {

            return holder.getProperty(key).trim();

        }

        return StringUtils.EMPTY;
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public static String getString(String key, Object... args) {

        if (holder.containsKey(key)) {

            String value = holder.getProperty(key).trim();

            return MessageFormat.format(value.trim(), args);

        }

        return StringUtils.EMPTY;
    }

    /**
     * @param key
     * @param param
     * @return
     */
    public static String getString(String key, String param) {
        return getString(key, new Object[]{param});
    }

    // 根据key读取value
    @Test
    public void readValue() {
        System.out.println(PropertiesUtil.getString("api.key"));
        System.out.println(PropertiesUtil.getString("100001", "sign"));
    }

    // 读取properties的全部信息
    @Test
    public void readAllProperties() {
        Enumeration<?> em = holder.propertyNames();
        while (em.hasMoreElements()) {
            String key = (String) em.nextElement();
            String Property = holder.getProperty(key);
            System.out.println(key + "=" + Property);
        }
    }
}
