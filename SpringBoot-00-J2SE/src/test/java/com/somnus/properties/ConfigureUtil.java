package com.somnus.properties;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
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
public class ConfigureUtil {

    private static final String CONFIG_FILE = "wxpay/config.properties";

    private static Configuration config;

    static {
        try {
            Configurations configs = new Configurations();

            String path = Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE).getPath();

            config = configs.properties(path);

        } catch (ConfigurationException e) {
            // 报错，系统文件不存在！
            throw new RuntimeException(String.format("系统错误：系统配置文件[%s]不存在", CONFIG_FILE));
        }
    }

    /**
     * @param key
     * @return
     */
    public static String getString(String key) {

        if (config.containsKey(key)) {

            return config.getString(key).trim();

        }

        return StringUtils.EMPTY;
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public static String getString(String key, Object... args) {

        if (config.containsKey(key)) {

            String value = config.getString(key).trim();

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
        System.out.println(ConfigureUtil.getString("api.key"));
        System.out.println(ConfigureUtil.getString("100001", "sign"));
    }

    // 读取properties的全部信息
    @Test
    public void readAllProperties() {

        config.setProperty("age", "22");

        for (Iterator<String> it = config.getKeys(); it.hasNext(); ) {
            String key = (String) it.next();
            String value = config.getString(key);
            System.out.println(key + "=" + value);
        }
    }
}
