package com.somnus.xstream;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** 
 * @Title: XmlUtil.java 
 * @Package com.somnus.xstream 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月30日 下午5:59:59 
 * @version V1.0 
 */
public class XmlUtil {
    /**
     * java 转换成xml
     * @Title: toXml 
     * @Description: TODO 
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String bean2Xml(Object obj){
        XStream xstream = new XStream();
        /*XStream xstream=new XStream(new DomDriver());*/
        /*XStream xstream=new XStream(new DomDriver("utf-8"));*/
         
        /*如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性*/
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
     
    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean 
     * @Description: TODO 
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Bean(String xmlStr,Class<T> cls){
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;         
    }
    
    public static String map2Xml(Map<String,Object> param){
        XStream xstream = new XStream();
        //通过注解方式的，一定要有这句话
        xstream.processAnnotations(param.getClass()); 
        /*xstream.registerConverter(new MapConverter());*/
        return xstream.toXML(param);
    }
    
    public static Map<String,Object> xml2Map(String xmlStr){
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(Map.class);
        @SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) xstream.fromXML(xmlStr);
        return map;         
    }
    
    public static class MapConverter implements Converter{
    	@Override
		public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
			return AbstractMap.class.isAssignableFrom(clazz); 
		}
		@SuppressWarnings("unchecked")
		@Override
		public void marshal(Object value,HierarchicalStreamWriter writer,MarshallingContext context) {
			 AbstractMap<String,String> map = (AbstractMap<String,String>) value;
			    for (Entry<String,String> entry : map.entrySet()) {
			        writer.startNode(entry.getKey().toString());
			        writer.setValue(entry.getValue().toString());
			        writer.endNode();
			    }
		}
		@Override
		public Object unmarshal(HierarchicalStreamReader reader,UnmarshallingContext context) {
			Map<String, String> map = new HashMap<String, String>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                String key = reader.getNodeName();
                String value = reader.getValue();
                map.put(key, value);
                reader.moveUp();
            }
            return map;
		}
    }
}
