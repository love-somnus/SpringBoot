package com.somnus.springboot.pay.support.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:     HTMLUtil.java
 * @Description:   组返回HTML信息的工具类
 * @author         Somnus
 * @version        V1.0  
 * @Date           2017年1月3日 下午3:30:27
 */
public class HTMLUtil {

    private static  Logger     logger = LoggerFactory.getLogger(HTMLUtil.class);
    public static final String ENCODING = "utf-8";
    public static final String ENCODING_GBK = "gbk";
    public static final String REQUEST_METHOD_POST = "post";
    public static final String REQUEST_METHOD_GET = "get";

    /**
     * 构造直接浏览器地址请求html
     *
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return 构造好的HTTP
     */
    public static String createHtmlHref(String action, Map<String, Object> hiddens, String encoding) {
        encoding = StringUtils.isNotBlank(encoding)?encoding:ENCODING;
        String url = action + "?";
        Set<Map.Entry<String, Object>> set = hiddens.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> ey = it.next();
            String key = ey.getKey();
            String value = ey.getValue().toString();
            url += key+"="+value+"&";
        }
        url = url.substring(0,url.length() -1);
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+encoding+"\" />");
        sbHtml.append("<script>window.location.href=\"" + url  + "\"</script>");
        return sbHtml.toString();
    }

    /**
     * 构造直接浏览器地址请求html
     *
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return 构造好的HTTP
     */
    public static String createHtml(String action, Map<String, Object> hiddens, String encoding, String method) {
        encoding = StringUtils.isNotBlank(encoding)?encoding: ENCODING;
        method = StringUtils.isNotBlank(method)?REQUEST_METHOD_GET: REQUEST_METHOD_POST;
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
                + encoding + "\" />");
        sbHtml.append("<form id=\"payFormAutoSubmit\" name=\"payFormAutoSubmit\" action=\""
                + action + "\" method=\"" + method + "\">");
        Set<Map.Entry<String, Object>> set = hiddens.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> ey = it.next();
            String name = ey.getKey();
            String value = ey.getValue().toString();
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        sbHtml.append("<input type=\"submit\" value=\"payFormAutoSubmit\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['payFormAutoSubmit'].submit();</script>");
        return sbHtml.toString();
    }


    /**
     * 构造HTTP POST交易表单的方法示例
     *
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public static String createSubmitHtml(String action, Map<String, String> hiddens,String actionType,String encoding) {
        actionType = StringUtils.isNotBlank(actionType)?actionType:"post";
        encoding = StringUtils.isNotBlank(encoding)?encoding:"UTF-8";
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+encoding+"\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + action + "\" method=\"" + actionType + "\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\""
                        + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    /**
     * 获得前端js脚本的执行输出结果
     * 默认输出""
     * @return
     */
    public static String getJSScriptReturn(String jsScriptStr,String defaultStr){
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByExtension("js");
        Object res = StringUtils.isNotBlank(defaultStr)?defaultStr:"";
        try{
            res = engine.eval(jsScriptStr);
        }catch(Exception e){
            logger.error("getJSScriptReturn has Exception---", e);
        }
        return res.toString();
    }

}
