package com.somnus.json;

import java.io.IOException;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
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
     * 简单序列化
     * 默认为null的属性序列化为""，想要去掉需要加注解@JsonInclude(Include.NON_EMPTY)
     */
    @Test
    public void simpleTest() throws JsonProcessingException{
        User user = new User("admin",null);
        user.setBirthday(new Date());
        List<Plot> list = Arrays.asList(new Plot("diudiu",new Date()),new Plot("dudu",new Date()) );
        user.setList(list);

        ObjectMapper objectMapper = new ObjectMapper();
        /*objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);*/
        /*objectMapper.setSerializationInclusion(Include.NON_EMPTY);*/
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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);

        String jsonStr = objectMapper.writeValueAsString(map);
        System.out.println(jsonStr);
    }

    /**
     * 简单反序列化
     */
    @Test
    public void simpleDeserializeTest() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStr = "{\"username\":\"owen\",\"password\":\"passw0rd\", \"age\":24}";

        User user = objectMapper.readValue(jsonStr, User.class);
        System.out.println(user);
    }

    /**
     * 泛型反序列化
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void genericTypeDeserializeTest() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

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
    static class User {

        /** @com.fasterxml.jackson.annotation.JsonIgnore*/
        @JsonInclude(Include.NON_EMPTY)
        private String username;

        @JsonInclude(Include.NON_EMPTY)
        private String password;

        @JsonInclude(Include.NON_DEFAULT)
        private int age;

        @JsonInclude(Include.NON_EMPTY)
        private List<Plot> list;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonInclude(Include.NON_EMPTY)
        private Date birthday;

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
