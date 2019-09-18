package com.somnus.springboot.pay.thirdpay.weixin.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLUtil {

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        for(Iterator<Element> it = root.getChildren().iterator(); it.hasNext();){
            Element element = it.next();
            String k = element.getName();
            String v = "";
            List<Element> children = element.getChildren();
            if (children.isEmpty()) {
                v = element.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            map.put(k, v);
        }
        //关闭流
        in.close();
        return map;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List<Element> children) {
        StringBuffer sb = new StringBuffer();
        for(Iterator<Element> it = children.iterator(); it.hasNext();){
            Element element = it.next();
            String name = element.getName();
            String value = element.getTextNormalize();
            List<Element> list = element.getChildren();
            sb.append("<" + name + ">");
            if (!list.isEmpty()) {
                sb.append(getChildrenText(list));
            }
            sb.append(value);
            sb.append("</" + name + ">");
        }
        return sb.toString();
    }

    /**
     * 获取xml编码字符集
     * @param strxml
     * @return
     * @throws IOException 
     * @throws JDOMException 
     */
    public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
        InputStream in = IOUtils.toInputStream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        in.close();
        return (String) doc.getProperty("encoding");
    }

}
