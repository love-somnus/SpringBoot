package com.somnus.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2015年8月6日 下午4:25:45
 */
public class JodaDate {

    @Test
    public void now() {
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        System.out.println(DateTime.now().toString("yyyyMMddHHmmssS"));
        System.out.println(DateTime.now().toString("yyyyMMddHHmmssSS"));
        System.out.println(DateTime.now().toString("yyyyMMddHHmmssSSS"));
    }

    @Test
    public void tostring() {
        DateTime dateTime = new DateTime(2015, 11, 11, 0, 0, 0, 0);
        System.out.println(dateTime.toString("yyyy-MM-dd"));
    }

    @Test
    public void plusDays() {
        System.out.println(DateTime.now().plusHours(9).getHourOfDay());
        //加上 9 天
        System.out.println(DateTime.now().plusDays(9).toString("yyyy-MM-dd"));
    }

    @Test
    public void dayOfWeek() {
        System.out.println(DateTime.now().plusDays(4).getDayOfWeek());
        // 当前周的周一，周日  
        System.out.println(String.format(
                "min:%s, max:%s",
                DateTime.now().dayOfWeek().withMinimumValue()
                        .toString("yyyy-MM-dd"),
                DateTime.now().dayOfWeek().withMaximumValue()
                        .toString("yyyy-MM-dd")));
    }

    @Test
    public void dayOfMonth() {
        // 当前月的第一天和最后一天  
        System.out.println(String.format(
                "min:%s, max:%s",
                DateTime.now().dayOfMonth().withMinimumValue()
                        .toString("yyyy-MM-dd"),
                DateTime.now().dayOfMonth().withMaximumValue()
                        .toString("yyyy-MM-dd")));
    }

    @Test
    public void dayOfYear() {
        // 当前年的第一天和最后一天  
        System.out.println(String.format(
                "min:%s, max:%s",
                DateTime.now().dayOfYear().withMinimumValue()
                        .toString("yyyy-MM-dd"),
                DateTime.now().dayOfYear().withMaximumValue()
                        .toString("yyyy-MM-dd")));
        // 某月的第一天和最后一天  
        System.out.println(String.format(
                "min:%s, max:%s",
                new DateTime("2017-03").dayOfMonth().withMinimumValue()
                        .toString("yyyy-MM-dd"),
                new DateTime("2017-03").dayOfMonth().withMaximumValue()
                        .toString("yyyy-MM-dd")));

        System.out.println(DateTime.now().withDayOfMonth(10).toString("yyyy-MM-dd"));
        System.out.println(DateTime.now().withDayOfWeek(1).toString("yyyy-MM-dd"));
    }

    @Test
    public void weekOfWeekyear(){
        System.out.println(DateTime.now().weekOfWeekyear().get());
    }

    @Test
    public void calendar() {
        //Joda 和 JDK 互操作性
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.now().toDate());
        System.out.println(calendar.get(Calendar.YEAR));

        DateTime dateTime = new DateTime(new Date());
        System.out.println(dateTime.toString("yyyy-MM-dd"));
        System.out.println(dateTime.getDayOfMonth());

        DateTime dateTime2 = new DateTime("2017-03-31").minusMonths(1);
        System.out.println(dateTime2.toString("yyyy-MM-dd"));
        System.out.println(dateTime2.getDayOfMonth());
    }

    @Test
    public void getDayOfMonth() {
        //在当月第几天
        System.out.println(DateTime.now().getDayOfMonth());
    }

    @Test
    public void getHourOfDay() {
        //在当天中第几个小时
        System.out.println(DateTime.now().getHourOfDay());
    }

    @Test
    public void compare() {
        //Date vs Date
        System.out.println(new Date().before(new Date()));
        System.out.println(new Date().after(new Date()));

        System.out.println(new Date().compareTo(new Date()));

        //String vs String
        System.out.println(new DateTime("2017-03-31").isBefore(new DateTime("2017-08-31")));
        System.out.println(new DateTime("2017-03-31").isBeforeNow());
        System.out.println(new DateTime("2017-03-31").isAfterNow());
    }

    @Test
    public void minus(){
        DateTime dateTime = new DateTime().millisOfDay().withMaximumValue();

        long millSeconds2 = new Duration(new DateTime(), dateTime).getMillis();

        long count = new Duration(new DateTime(), dateTime).getStandardSeconds();

        System.out.println("当天剩余毫秒：" + millSeconds2);

        System.out.println("当天剩余秒：" + count);
    }

}
