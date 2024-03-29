package com.somnus.apache;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.*;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.somnus.apache.Person.Pet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Somnus
 * @version V1.0
 * @Title: CommonsLang.java
 * @Package com.somnus.apache
 * @Description: TODO
 * @date 2015年6月12日 下午5:39:52
 */
public class CommonsLang {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(100));;
        String name = "abcabc";
        String name_ = name.replace("b", "a");
        System.out.println(name + "-->" + name_);

        String[] countryCodes = new String[]{"86", "886", "852", "853", "65"};

        String number = "8860919919560";

        String countryCode = Arrays.stream(countryCodes).filter(item -> StringUtils.startsWith(number, item)).findFirst().orElse("86");

        String newnumber = StringUtils.startsWith(number, countryCode) ? StringUtils.substringAfter(number, countryCode) : number;

        System.out.println(countryCode);

        System.out.println(newnumber);

        String line = "One-inch Punch".replaceAll("['\";:,.?¿\\-!¡]+", "");
        System.out.println(line);
        Pattern p = Pattern.compile("\\S+");
        Matcher ms = p.matcher(line);
        AtomicInteger atomicInteger = new AtomicInteger();
        while(ms.find()){
            atomicInteger.addAndGet(1);
        }
        System.out.println(atomicInteger.get());
    }

    @Test
    public void SerializationUtils() {
        System.out.println(this.getClass().getName());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

        Person person = new Person("admin", "password", new Date(), BigDecimal.TEN, new Pet("diu diu"));

        /** 深拷贝 **/
        Person person2 = (Person) SerializationUtils.clone(person);
        person2.getPet().setName("mie mie");

        System.out.println("是否相同？ " + (person == person2));

        System.out.println("宠物是否相同？ " + (person.getPet() == person2.getPet()));

        System.out.println(person.getPet() + "||" + person2.getPet());

        somnus();
    }

    public static void somnus() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()
                + "\n"
                + Thread.currentThread().getStackTrace()[1].getMethodName()
                + "\n"
                + Thread.currentThread().getStackTrace()[1].getLineNumber());
    }

    @Test
    public void ArrayUtils() {
        /**判断数组是否为空*/
        System.out.println(ArrayUtils.isEmpty(new String[]{"21", "是"}));//false
        System.out.println(ArrayUtils.isEmpty(new String[]{""}));//false
        System.out.println(ArrayUtils.isEmpty(new String[]{null}));//false
        System.out.println(ArrayUtils.isEmpty(new String[]{}));//true
        /**将两个数组合并为一个数组*/
        String[] s1 = new String[]{"1", "2", "3"};
        String[] s2 = new String[]{"a", "b", "c"};
        String[] s = (String[]) ArrayUtils.addAll(s1, s2);
        System.out.println("ArrayUtils.toString---->" + ArrayUtils.toString(s));
        System.out.println("Arrays.toString-------->" + Arrays.toString(s));
        /**移除*/
        String[] s3 = new String[]{"", "", "a", "", "b", "c"};
        for (int i = 0; i < s3.length; i++) {
            if (StringUtils.isEmpty(s3[i])) {
                s3 = ArrayUtils.remove(s3, i);
                i--;
            }
        }
        System.out.println(ArrayUtils.toString(s3));
        String[] ss = ArrayUtils.subarray(s, 1, 4);
        System.out.println(ArrayUtils.toString(ss));

        /** Arrays.asList确实会返回一个ArrayList对象，但是该类是Arrays类 中一个私有静态内部类，
         * 而不是常见的java.util.ArrayList类。这个java.util.Arrays.ArrayList类具有 set()，get()，contains()等方法，
         * 但是不具有任何添加或移除元素的任何方法。**/
        /*List<String> list = new ArrayList<>(Arrays.asList(new String[]{"21","是"}));*/

        List<String> list2 = new ArrayList<>();
        Collections.addAll(list2, new String[]{"21", "是"});
    }

    @Test
    public void ClassUtils() {
        // 取得类名
        System.out.println(ClassUtils.getShortClassName(CommonsLang.class));
        // 取得其包名
        System.out.println(ClassUtils.getPackageName(CommonsLang.class));
        // 五位的随机字母和数字 
        System.out.println(RandomStringUtils.randomAlphanumeric(5));
    }

    @Test
    public void StringEscapeUtils() {
        System.out.println(StringEscapeUtils.escapeHtml4("<html>"));//&lt;html&gt;
        System.out.println(StringEscapeUtils.unescapeHtml4("https://arena.163.com/fansclub?cnm=%E8%A7%82%E6%B5%B7%E5%90%AC%E6%B6%9B&amp;inm=%E8%A7%82%E6%B5%B7&amp;uid=3450157"));//&lt;html&gt;
        System.out.println(StringEscapeUtils.escapeJava("String你好"));//String\u4F60\u597D
        System.out.println(StringEscapeUtils.unescapeJava("String\u4F60\u597D"));//String你好
    }

    @Test
    @SneakyThrows
    public void StringUtils() {
        String url = "https://arena.163.com/download/?game=arena&task=12&mobile=18538855430";
        System.out.println(StringUtils.substringBefore(url ,"?"));

        System.out.println(StringUtils.substringAfter(url, "?"));

        System.out.println(StringUtils.substringBefore("copier@163@com","@"));
        System.out.println(StringUtils.substringAfter("copier@163@com", "@"));
        System.out.println(StringUtils.substringBeforeLast("copier@163@com","@"));
        System.out.println(StringUtils.substringAfterLast("copier@163@com","@"));

        //缩写省略字符串
        System.out.println(StringUtils.abbreviate("abcdefghijklmno", -1, 10));
        // 判断该字符串是不是为数字(0~9)组成，如果是，返回true 但该方法不识别有小数点和请注意
        System.out.println(StringUtils.isNumeric("454534"));// true
        // StringUtils,判断是否是空格字符
        /**
         * StringUtils.isBlank(null)      = true
         * StringUtils.isBlank("")        = true
         * StringUtils.isBlank(" ")       = true *
         * StringUtils.isBlank("bob")     = false
         * StringUtils.isBlank("  bob  ") = false
         */
        System.out.println(StringUtils.isBlank("   "));
        /**
         * StringUtils.isEmpty(null)      = true
         * StringUtils.isEmpty("")        = true
         * StringUtils.isEmpty(" ")       = false *
         * StringUtils.isEmpty("bob")     = false
         * StringUtils.isEmpty("  bob  ") = false
         */
        System.out.println(StringUtils.isEmpty("   "));
        // 将数组中的内容以,分隔
        System.out.println(StringUtils.join(new String[]{"1", "2", "3"}, ","));//1,2,3
        // 将集合中的内容以,分隔
        System.out.println(StringUtils.join(Arrays.asList("1", "2", "3"), ","));//1,2,3
        // 在左边加下字符,使之总长度为6
        System.out.println(StringUtils.leftPad("abc", 6, 'T'));//TTTabc
        // 在右边加下字符,使之总长度为6
        System.out.println(StringUtils.rightPad("abc", 6, 'T'));//abcTTT
        // 首字母大写 
        System.out.println(StringUtils.capitalize("abc"));//Abc
        // Deletes all whitespaces from a String 删除所有空格
        System.out.println(StringUtils.deleteWhitespace("   ab  c  "));//abc
        // 判断是否包含这个字符 
        System.out.println(StringUtils.contains("abc", "ba"));//false
        System.out.println(StringUtils.containsAny("abc", 'a', 'd'));//true
        System.out.println(StringUtils.containsAny("abc", "ab", "bd"));//true

        System.out.println(StringUtils.containsOnly("abc", "ba"));//false
        // 表示左边两个字符 
        System.out.println(StringUtils.left("abc", 2));//ab
        // 表示左边两个字符 
        System.out.println(StringUtils.right("abc", 2));//bc
        /**
         * StringUtils.remove(null, *)        = null
         * StringUtils.remove("", *)          = ""
         * StringUtils.remove(*, null)        = *
         * StringUtils.remove(*, "")          = *
         * StringUtils.remove("queued", "ue") = "qd"
         * StringUtils.remove("queued", "zz") = "queued"
         */
        System.out.println(StringUtils.remove("2015-10-01", "-"));//20151001
        System.out.println(StringUtils.isNumeric("100"));//true
        /**
         * StringUtils.strip(null)     = null
         * StringUtils.strip("")       = ""
         * StringUtils.strip("   ")    = ""
         * StringUtils.strip("abc")    = "abc"
         * StringUtils.strip("  abc")  = "abc"
         * StringUtils.strip("abc  ")  = "abc"
         * StringUtils.strip(" abc ")  = "abc"
         * StringUtils.strip(" ab c ") = "ab c"
         */
        //去掉字符串两端的空白符(whitespace) 
        System.out.println(StringUtils.strip("abc"));
        /**
         * StringUtils.trim(null)          = null
         * StringUtils.trim("")            = ""
         * StringUtils.trim("     ")       = ""
         * StringUtils.trim("abc")         = "abc"
         * StringUtils.trim("    abc    ") = "abc"
         * StringUtils.trim("     \n\tss   \b") = "ss"
         * StringUtils.trim("  \b \t \n \f \r    ") = ""
         */
        //去掉字符串两端的控制符(control characters, char <= 32) 
        System.out.println(StringUtils.trim("abc"));
        //去掉 str 两端的在 stripChars 中的字符
        System.out.println(StringUtils.strip("  abcyx", "xyz")); // "  abc"  
        //去掉 str 前端的在 stripChars 中的字符
        System.out.println(StringUtils.stripStart("yxabc  ", "xyz")); // "abc  "
        //去掉 str 末端的在 stripChars 中的字符
        System.out.println(StringUtils.stripEnd("  abcyx", "xyz")); // "  abc"
        //默认半角空格分割  
        String str = "aaa bbb ccc";
        String[] s = StringUtils.split(str);
        System.out.println(ArrayUtils.toString(s));//{aaa,bbb,ccc}
        //指定分隔符  
        String str2 = "aaa,bbb,ccc";
        String[] s2 = StringUtils.split(str2, ",");
        System.out.println(ArrayUtils.toString(s2));//{aaa,bbb,ccc}
        String str22 = "aaa|bbb|ccc";
        String[] s22 = StringUtils.split(str22, "[|]");
        System.out.println(ArrayUtils.toString(s22));//{aaa,bbb,ccc}
        //去除空字符串 
        String str3 = "aaa,,ccc,";
        String[] s3 = StringUtils.split(str3, ",");
        System.out.println(ArrayUtils.toString(s3));//{aaa,ccc}
        //去除空字符串 
        String str4 = "aaa,,ccc,";
        String[] s4 = StringUtils.splitPreserveAllTokens(str4, ",");
        System.out.println(ArrayUtils.toString(s4));//{aaa,,ccc,}
        //指定分割的最大次数（超过后不分割）  
        String str5 = "aaa,bbb,ccc,ddd,eee";
        String[] s5 = StringUtils.split(str5, ",", 3);
        System.out.println(ArrayUtils.toString(s5));//{"aaa","bbb","ccc,ddd,eee"}
        //生成n*String或n*char长度的字符串
        System.out.println(StringUtils.repeat("ab", 5));
        //两边填充
        System.out.println(StringUtils.center("ab", 4));
        System.out.println(StringUtils.center("ab", 6, "*"));
        //截取()中间的字符串
        System.out.println(StringUtils.substringBetween("abc(123)cde", "(", ")"));
        Map<String, String> map = new HashMap<String, String>();
        System.out.println(map.get("abc"));
        System.out.println(StringUtils.defaultString(map.get("abc")));
        System.out.println(StringUtils.defaultString(map.get("abc"), "abc"));
        System.out.println(StringUtils.defaultIfBlank(map.get("abc"), "abc"));
        System.out.println(StringUtils.defaultIfEmpty(map.get("abc"), "abc"));

        String ss = "aabb";

        /** replaceEach和replaceEachRepeatedly区别可能是：aabb 替换 aa 为 bb， bb 为 cc 的时候，replaceEach 结果是 bbcc, replaceEachRepeatedly 的结果是 cccc **/
        String a = StringUtils.replaceEach(ss, new String[] {"aa",  "bb"}, new String[] {"bb",  "cc"});
        String b = StringUtils.replaceEachRepeatedly(ss, new String[] {"aa",  "bb"}, new String[] {"bb",  "cc"});

        System.out.println(a + "," + b);
        System.out.println(StringUtils.replaceEachRepeatedly("123987",
                new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"},
                new String[]{"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"}));
        //统计某字符在一个字符串中的出现次数
        System.out.println(StringUtils.countMatches("11112222", "1"));
        String baidu = "1\n2\n3\n4";
        System.out.println(baidu);
        System.out.println(StringUtils.normalizeSpace(baidu));
    }

    @Test
    public void ObjectUtils() throws ParseException {
        Person person = new Person("admin", "password", new Date(), BigDecimal.TEN, new Pet("diu diu"));

        String str = ObjectUtils.toString(person, "");

        System.out.println(str);
    }

    @Test
    public void Validate() {
        File sourceFile = new File(CommonsLang.class.getClassLoader().getResource("").getPath());
        Validate.notNull(sourceFile, "source file is required.");
        Validate.isTrue(sourceFile.isFile(), "source file [%s] is not a valid file.", sourceFile);
        String string = "";
        Validate.notEmpty(string, "string is not empty");
    }

    @Test
    public void NumberUtils() {
        /**判断字符串中是否全为数字*/
        System.out.println(NumberUtils.isDigits("0000000000.596"));//false
        System.out.println(NumberUtils.isDigits("0000000000596"));//true
        /**找出最大的一个*/
        System.out.println(NumberUtils.max(new int[]{3, 5, 6}));
        System.out.println(NumberUtils.max(3, 1, 7));
    }

    @Test
    public void RandomStringUtils() {
        //产生5位长度的随机字符串，中文环境下是乱码
        System.out.println(RandomStringUtils.random(5));
        //使用指定的字符生成5位长度的随机字符串
        System.out.println(RandomStringUtils.random(5, new char[]{'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3'}));
        //生成指定长度的字母和数字的随机组合字符串
        System.out.println(RandomStringUtils.randomAlphanumeric(32));
        //生成随机数字字符串
        System.out.println(RandomStringUtils.randomNumeric(5));
        //生成随机[a-z]字符串，包含大小写
        System.out.println(RandomStringUtils.randomAlphabetic(5));
        //生成从ASCII 32到126组成的随机字符串
        System.out.println(RandomStringUtils.randomAscii(4));
    }

    @Test
    public void RandomUtils() {
        System.out.println(RandomUtils.nextInt(5, 10));
    }

    @Test
    public void stopWatch() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start();

        TimeUnit.SECONDS.sleep(new Random().nextInt(10));

        sw.stop();

        System.out.println(sw.getTime());
    }

    @Test
    public void querystring() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", "1");
        params.add("storeId", "a");
        params.add("orderId", "b");
        params.add("orderId", "c");
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://spsenthil.com/order").queryParams(params).build();
        System.out.println(uriComponents.toString());
    }
    @Test
    public void querystring2() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "1");
        params.put("storeId", "a");
        params.put("orderId", "b");
        List<BasicNameValuePair> nameValuePairs = params.entrySet().stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        System.out.println(URLEncodedUtils.format(nameValuePairs, StandardCharsets.UTF_8));
    }

    @Test
    public void querystring3() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "1");
        params.put("storeId", "a");
        params.put("orderId", "b");
        String str = params.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .map(s -> "?" + s)
                .orElse("");
        System.out.println(str);
    }

    @Test
    public void random(){
        int number;
        boolean ok;
        do {
            ok = true;
            number = new Random().nextInt(9000) + 1000;
            int[] digits = { number / 1000 % 10, number / 100 % 10, number / 10 % 10, number % 10 };
            for (int i = 0; i < 4 && ok; i++) {
                for (int j = i + 1; j < 4; j++) {
                    if (digits[i] == digits[j]) {
                        ok = false;
                        break;
                    }
                }
            }
        } while (!ok);
        System.out.println(number);
    }

}
