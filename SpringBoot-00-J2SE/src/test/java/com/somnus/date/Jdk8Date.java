package com.somnus.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * @author Somnus
 * @ClassName: Jdk8Date
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年9月27日
 */
public class Jdk8Date {

    @Test
    public void Instant() {
        System.out.println(Instant.now());
        System.out.println(Instant.now().toString());
        System.out.println("获取已经度过的毫秒" + Instant.now().toEpochMilli());
        System.out.println("获取已经度过的秒" + Instant.now().getEpochSecond());

        System.out.println(ZoneId.systemDefault());
        /* Instant转LocalDateTime */
        System.out.println(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        /* LocalDateTime转Instant */
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void LocalDate() {
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().toString());
        System.out.println(LocalDate.of(2008, 8, 8));
        System.out.println(LocalDate.parse("2008-08-08"));
        System.out.println("Year:" + LocalDate.now().getYear());
        System.out.println("MonthValue:" + LocalDate.now().getMonthValue());
        System.out.println("DayOfMonth:" + LocalDate.now().getDayOfMonth());
        System.out.println("DayOfYear:" + LocalDate.now().getDayOfYear());
        System.out.println("DayOfWeek:" + LocalDate.now().getDayOfWeek().getValue());

        System.out.println("WeekOfYear:" + LocalDate.now().get(WeekFields.ISO.weekOfYear()));
        System.out.println("WeekOfMonth:" + LocalDate.now().get(WeekFields.ISO.weekOfMonth()));
        System.out.println("DayOfWeek:" + LocalDate.now().get(WeekFields.ISO.dayOfWeek()));

        System.out.println(LocalDate.now().plus(1, ChronoUnit.YEARS));
        System.out.println(LocalDate.now().minus(1, ChronoUnit.YEARS));

        System.out.println(LocalDate.now().plusDays(1));
        System.out.println(LocalDate.now().plusMonths(1));

        System.out.println("2014年第100天的日期:" + LocalDate.ofYearDay(2014, 100));
        System.out.println("取下一周第一天:" + LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println("取下一年第一天:" + LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println("取本月第一天:" + LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("取当年第一天:" + LocalDate.now().with(TemporalAdjusters.firstDayOfYear()));

        System.out.println("取本月的第一个星期天:" + LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY)));
        System.out.println("取本月的第二个星期天:" + LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY)));

        System.out.println("取本月最后一天:" + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("取当年最后一天:" + LocalDate.now().with(TemporalAdjusters.lastDayOfYear()));
        System.out.println("取本月的最后一个星期天:" + LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)));

        System.out.println("取上一个星期天:" + LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));
        System.out.println("取下一个星期天:" + LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));

        System.out.println("取本月第二天:" + LocalDate.now().withDayOfMonth(2));
        System.out.println("取当年第100天:" + LocalDate.now().withDayOfYear(100));
        System.out.println("取当年2月最后一天:" + LocalDate.now().withMonth(2).with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("取2021年当月最后一天:" + LocalDate.now().withYear(2021).with(TemporalAdjusters.lastDayOfMonth()));

        System.out.println("是否闰年:" + LocalDate.now().isLeapYear());

        System.out.println("相隔周数:" + LocalDate.now().until(LocalDate.parse("2020-08-08"), ChronoUnit.WEEKS));
        System.out.println("相隔月数:" + LocalDate.now().until(LocalDate.parse("2020-08-08"), ChronoUnit.MONTHS));
        System.out.println("相隔天数:" + LocalDate.now().until(LocalDate.parse("2020-08-08"), ChronoUnit.DAYS));

    }

    @Test
    public void LocalTime() {
        System.out.println(LocalTime.now());
        System.out.println(LocalTime.now().toString());
        System.out.println("获取当前时间，不包含毫秒数:" + LocalTime.now().withNano(0));
        System.out.println(LocalTime.of(10, 8, 8));
        System.out.println(LocalTime.of(23, 59, 59));
        System.out.println(LocalTime.parse("12:01:02"));
        System.out.println("Hour:" + LocalTime.now().getHour());
        System.out.println("Minute:" + LocalTime.now().getMinute());
        System.out.println("Second:" + LocalTime.now().getSecond());
        System.out.println("Nano:" + LocalTime.now().getNano());

        LocalTime time = LocalTime.of(23, 0, 1);
        LocalTime start = LocalTime.of(10, 0, 0);
        LocalTime end = LocalTime.of(23, 59, 59);

        System.out.println(time.isAfter(start) && time.isBefore(end));
    }

    @Test
    public void LocalDateTime() {
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().toString());
        System.out.println(LocalDateTime.of(2008, Month.AUGUST, 8, 2, 10, 20, 55));

        System.out.println("Year:" + LocalDateTime.now().getYear());
        System.out.println("MonthValue:" + LocalDateTime.now().getMonthValue());
        System.out.println("DayOfMonth:" + LocalDateTime.now().getDayOfMonth());
        System.out.println("DayOfYear:" + LocalDateTime.now().getDayOfYear());
        System.out.println("DayOfWeek:" + LocalDateTime.now().getDayOfWeek());
        System.out.println("DayOfWeek:" + LocalDateTime.now().getDayOfWeek().getValue());

        System.out.println("WeekOfYear:" + LocalDateTime.now().get(WeekFields.ISO.weekOfYear()));
        System.out.println("WeekOfMonth:" + LocalDateTime.now().get(WeekFields.ISO.weekOfMonth()));
        System.out.println("DayOfWeek:" + LocalDateTime.now().get(WeekFields.ISO.dayOfWeek()));

        System.out.println("取本月第1天:" + LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("取本月第2天:" + LocalDateTime.now().withDayOfMonth(2));
        System.out.println("取本月的第一个星期天:" + LocalDateTime.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY)));
        System.out.println("取本月最后一天:" + LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("取当年2月最后一天:" + LocalDateTime.now().withMonth(2).with(TemporalAdjusters.lastDayOfMonth()));
        //其它的LocalDate有with的它都有

        System.out.println("plusDays:" + LocalDateTime.now().plusDays(1));
    }

    @Test
    public void YearMonth() {
        System.out.println(Boolean.valueOf(null));
        System.out.println(Boolean.valueOf("a"));
        System.out.println(Boolean.valueOf(""));
        System.out.println(YearMonth.now());
        System.out.println(YearMonth.now().toString());
        System.out.println(YearMonth.of(2018, Month.FEBRUARY));
        System.out.println(YearMonth.now().atEndOfMonth().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println(YearMonth.now().atEndOfMonth());
    }

    @Test
    public void MonthDay() {
        System.out.println(MonthDay.now());
        System.out.println(MonthDay.now().toString());
        System.out.println(MonthDay.of(Month.FEBRUARY, 28));
    }

    @Test//由于Period是以年月日衡量时间段，所以between()方法只能接收LocalDate类型的参数
    public void Period() {
        System.out.println(Period.between(LocalDate.now(), LocalDate.of(2019, 8, 8)).getYears());
        System.out.println(Period.between(LocalDate.now(), LocalDate.of(2019, 8, 8)).getMonths());
        System.out.println(Period.between(LocalDate.now(), LocalDate.of(2019, 8, 8)).getDays());
    }

    @Test
    public void Duration() {
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(2)).toDays());
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(2)).toHours());
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(2)).toMinutes());
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(2)).getSeconds());
    }

    @Test
    public void ChronoUnit() {
        System.out.println(ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(2)));

        System.out.println(ChronoUnit.HOURS.between(LocalTime.now(), LocalTime.parse("12:01:02")));

        System.out.println("相隔天数:" +ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse("2020-08-08")));
        System.out.println("相隔月数:" + ChronoUnit.MONTHS.between(LocalDate.now(), LocalDate.parse("2020-08-08")));
        System.out.println("相隔周数:" + ChronoUnit.WEEKS.between(LocalDate.now(), LocalDate.parse("2020-08-08")));
    }

    @Test
    public void convert() {
        Date date = new Date();
        System.out.println("current date: " + date);

        // Date -> LocalDateTime
        System.out.println("localDateTime by ofInstant: " + LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));

        // Date -> LocalDateTime
        System.out.println("localDateTime by toInstant: " + date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // Date -> LocalDate
        System.out.println("localDate by toInstant: " + date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        //2. Date -> LocalTime
        System.out.println("localTime by toInstant: " + date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());

    }

    @Test
    public void convert2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime: " + localDateTime);

        // LocalDateTime -> Date
        System.out.println("LocalDateTime -> current date: " + Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        // LocalDate -> Date，时间默认都是00
        System.out.println("LocalDate -> current date: " + Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        System.out.println("LocalDate -> current date: " + Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        //LocalDateTime -> LocalDate
        System.out.println("LocalDateTime -> LocalDate: " + LocalDateTime.now().toLocalDate());
        //LocalDate -> LocalDateTime
        System.out.println("LocalDateTime -> LocalDate: " + LocalDate.now().atStartOfDay());
        //LocalDate -> LocalDateTime
        System.out.println("LocalDate -> LocalDateTime: " + LocalDate.now().atTime(LocalTime.now()));
        //LocalDate -> LocalDateTime
        System.out.println("LocalDate -> LocalDateTime: " + LocalDate.now().atTime(0,0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("LocalDate -> LocalDateTime: " + LocalDate.now().atTime(0,0,0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("LocalDate -> LocalDateTime: " + LocalDate.now().atTime(0,0,0,0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void convert3(){
        // LocalDateTime -> String
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // String -> LocalDateTime
        System.out.println(LocalDateTime.parse("2017-12-03T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(LocalDateTime.parse("2017-12-03 10:15:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // LocalDate -> String
        System.out.println(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        // String -> LocalDate
        System.out.println(LocalDate.parse("20180808", DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.parse("2017-08-08", DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(LocalDate.parse("2018-08-08", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // Date -> String
        System.out.println(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //String -> Date
        System.out.println(Date.from(LocalDateTime.parse("2017-12-03 10:15:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    public void minus(){
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);

        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);

        System.out.println("当天剩余毫秒3：" + millSeconds);

        System.out.println("当天剩余秒3：" + seconds);

    }

}
