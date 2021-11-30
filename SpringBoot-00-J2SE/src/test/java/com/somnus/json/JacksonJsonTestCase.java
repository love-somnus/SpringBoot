package com.somnus.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @Description: TODO
 * @author Somnus
 * @date 2015年11月20日 下午2:10:04 
 * @version V1.0
 */
public class JacksonJsonTestCase {

    /**
     * 默认日期时间格式
     */
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        objectMapper.enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);

        // java8日期日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 简单序列化
     * 默认为null的属性序列化为""，想要去掉需要加注解@JsonInclude(Include.NON_EMPTY)
     */
    @Test
    public void simpleTest() throws JsonProcessingException{
        User user = new User("admin",null);
        user.setBirthday(new Date());
        user.setBorntime(LocalDateTime.now());
        user.setPrice(new BigDecimal("0.01"));
        List<Plot> list = Arrays.asList(new Plot("diudiu",new Date()),new Plot("dudu",new Date()) );
        user.setList(list);

        String jsonStr = objectMapper.writeValueAsString(user);
        System.out.println(jsonStr);
    }

    /**
     * 复杂序列化
     */
    @Test
    public void complexTest() throws JsonProcessingException {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("username", "owen");
        map.put("age", 25);
        map.put("sex", "男");

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", "jack");
        temp.put("age", 18);

        map.put("girinfo", temp);

        List<String> list = new ArrayList<String>();
        list.add("爬山");
        list.add("电影");
        list.add("旅游");

        map.put("hobby", list);

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);

        String jsonStr = objectMapper.writeValueAsString(map);
        System.out.println(jsonStr);
    }

    /**
     * 简单反序列化
     */
    @Test
    public void simpleDeserializeTest() throws Exception {
        String jsonStr = "{\"username\":\"owen\",\"password\":\"passw0rd\", \"price\":0.01 ,\"age\":24, \"birthday\":\"2021-11-25 06:44:17\", \"borntime\":\"2021-11-25 15:06:41\"}";

        User user = objectMapper.readValue(jsonStr, User.class);
        System.out.println(user);

        Map<String, Object> map = objectMapper.readValue(jsonStr, Map.class);
        System.out.println(map);
    }

    /**
     * 泛型反序列化
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void genericTypeDeserializeTest() throws JsonParseException, JsonMappingException, IOException {

        String jsonStr = "{\"user\":{\"username\":\"owen\",\"password\":\"passw0rd\", \"age\":24}}";
        Map<String, User> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, User>>(){});
        System.out.println(map.get("user")+"\n");

        String jsonStr2 = "{\"user\":[{\"username\":\"owen\", \"age\":24}, {\"username\":\"jack\", \"age\":18}]}";
        Map<String, List<User>> users = objectMapper.readValue(jsonStr2, new TypeReference<Map<String, List<User>>>(){});
        for (User user : users.get("user")) {
            System.out.println(user);
            System.out.println("------------------");
        }

        System.out.println("\n");

        String jsonStr3 = "[{\"username\":\"owen\", \"age\":24}, {\"username\":\"jack\", \"age\":18}]";
        List<User> list = objectMapper.readValue(jsonStr3, new TypeReference<List<User>>(){});
        for (User user : list) {
            System.out.println(user);
            System.out.println("------------------");
        }
    }

    @Data
    @NoArgsConstructor
    static class User {

        /** @com.fasterxml.jackson.annotation.JsonIgnore*/
        @JsonInclude(Include.NON_EMPTY)
        private String username;

        @JsonInclude(Include.NON_EMPTY)
        private String password;

        @JsonInclude(Include.NON_DEFAULT)
        private int age;

        @JsonInclude(Include.NON_DEFAULT)
        private BigDecimal price;

        @JsonInclude(Include.NON_EMPTY)
        private List<Plot> list;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonInclude(Include.NON_EMPTY)
        private Date birthday;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonInclude(Include.NON_EMPTY)
        private LocalDateTime borntime;

        @JsonInclude(Include.NON_EMPTY)
        private Map<String,List<String>> map;

        public User(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    @Data
    @AllArgsConstructor
    static class Plot{

        private String name;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonInclude(Include.NON_EMPTY)
        private Date birthday;

    }

}
