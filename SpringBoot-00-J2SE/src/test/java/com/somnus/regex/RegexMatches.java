package com.somnus.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2015年11月2日 下午5:22:45
 */
public class RegexMatches {

    @Test
    public void test1() {
        // 按指定模式在字符串查找
        String line = "2016-01-24,PAY000000192303   ,677824749  ,"
                + "310000067575   ,2016-01-24 10:42:19,1  ,\"1,000,000.00\",01 ,   ,,Y ,";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile("((\\d{1,3}(,\\d{3})*)|(\\d+))\\.\\d{2}?");

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group());
        } else {
            System.out.println("NO MATCH");
        }
    }

    @Test
    public void test22() {
        // 按指定模式在字符串查找
        String line = "auser1 home1b\n" +
                "auser2 home2b\n" +
                "auser3 home3b";

        Matcher m = Pattern.compile("(?m)^.*$").matcher(line);

        while (m.find()) {
            System.out.println("line = " + m.group());
        }
    }

    @Test
    public void test2() {
        String str = "银行业存款类金融机构协助查冻扣账户数量[3041900001#01]<=银行业存款类金融机构协助"
                + "查询账户数量[3041900001#b1]+银行业存款类金融机构协助冻结账户数量[3041900001#b2]+银行业"
                + "存款类金融机构协助扣划账户数量[3041900001#b3]";
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        String repickStr = mat.replaceAll("");
        System.out.println("去中文后:" + repickStr);
    }
}
