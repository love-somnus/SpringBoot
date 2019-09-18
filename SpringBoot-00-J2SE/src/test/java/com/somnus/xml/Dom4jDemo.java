package com.somnus.xml;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Somnus
 * @version V1.0
 * @Title: Dom4jDemo.java
 * @Package com.somnus.xml
 * @Description: TODO
 * @date 2015年6月8日 上午11:32:03
 */
public class Dom4jDemo {

    @SuppressWarnings("unchecked")
    @Test
    public void test1() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getClassLoader().getResourceAsStream("user.xml"));
        Element root = document.getRootElement();
        /*Iterator<Element> it = root.elements().iterator();*/
        /*List<Element> list = root.elements("Account");*/
        for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
            Element element = it.next();

            String account = element.getName();
            System.out.println("account:" + account);
            System.out.println("属性-所属区域: " + element.attributeValue("type"));
            //*************************
            //获取单个
            //*************************
            System.out.println(element.elementText("code"));
            System.out.println(element.element("code").getText());

            for (Iterator<Element> child = element.elementIterator(); child.hasNext(); ) {
                Element el = child.next();
                System.out.println(el.getName() + " : " + el.getText());
            }
            System.out.println("*************************************");
        }
    }


    public void createXml(String filename) throws Exception {
        //创建文档并设置文档的根元素节点：第一种方式
//		Document document = DocumentHelper.createDocument();
//		Element root = DocumentHelper.createElement("student");
//		document.setRootElement(root);

        //创建文档并设置文档的根元素节点：第二种方式
        Element rootElement = DocumentHelper.createElement("user");
        //注释
        rootElement.addComment("***************************");

        Document document = DocumentHelper.createDocument(rootElement);

        //*******设置第一个********
        Element accountElement = rootElement.addElement("Account");
        //属性
        accountElement.addAttribute("type", "mobile");

        Element nameElement = accountElement.addElement("name");
        nameElement.setText("移动");

        Element codeElement = accountElement.addElement("code");
        codeElement.setText("10086");
        Element passElement = accountElement.addElement("pass");
        passElement.setText("123456");

        Element moneyElement = accountElement.addElement("money");
        moneyElement.setText("999999999");


        //*******设置第二个********
        Element accountElement2 = rootElement.addElement("Account");
        //属性
        accountElement2.addAttribute("type", "mobile");

        Element nameElement2 = accountElement2.addElement("name");
        nameElement2.setText("联通");

        Element codeElement2 = accountElement2.addElement("code");
        codeElement2.setText("10010");

        Element passElement2 = accountElement2.addElement("pass");
        passElement2.setText("123456");

        Element moneyElement2 = accountElement2.addElement("money");
        moneyElement2.setText("88888888");


        OutputFormat format = new OutputFormat("    ", true);

        XMLWriter writer = new XMLWriter(new FileOutputStream(new File(filename)), format);

        writer.write(document);

        writer.close();
    }
}
   
 
