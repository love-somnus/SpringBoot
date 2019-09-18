package com.somnus.date;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
        System.out.println("DayOfYear:" + LocalDate.now().getDayOfWeek());

        System.out.println(LocalDate.now().plus(1, ChronoUnit.YEARS));
        System.out.println(LocalDate.now().minus(1, ChronoUnit.YEARS));

        System.out.println(LocalDate.now().plusDays(1));
        System.out.println(LocalDate.now().plusMonths(1));

        System.out.println("2014年第100天的日期:" + LocalDate.ofYearDay(2014, 100));
        System.out.println("取本月第1天:" + LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("取本月第2天:" + LocalDate.now().withDayOfMonth(2));
        System.out.println("取本月最后一天:" + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("是否闰年:" + LocalDate.now().isLeapYear());

        System.out.println(LocalDate.parse("20180808", DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.parse("2018-08-08", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.BASIC_ISO_DATE));
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
        System.out.println(LocalDateTime.parse("2008-08-08T17:04:40.172"));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println("Year:" + LocalDateTime.now().getYear());
        System.out.println("MonthValue:" + LocalDateTime.now().getMonthValue());
        System.out.println("DayOfMonth:" + LocalDateTime.now().getDayOfMonth());
        System.out.println("DayOfYear:" + LocalDateTime.now().getDayOfYear());
        System.out.println("DayOfYear:" + LocalDateTime.now().getDayOfWeek());
        System.out.println(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(LocalDateTime.parse("20080808 17:04:40",
                DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void YearMonth() {
        System.out.println(YearMonth.now());
        System.out.println(YearMonth.now().toString());
        System.out.println(YearMonth.of(2018, Month.FEBRUARY));
    }

    @Test
    public void Period() {
        System.out.println(Period.between(LocalDate.now(), LocalDate.of(2019, 8, 8)).getMonths());
        System.out.println(Period.between(LocalDate.now(), LocalDate.of(2019, 8, 8)).getDays());
    }

    @Test
    public void ChronoUnit() {
        System.out.println(ChronoUnit.MONTHS.between(LocalDate.now(), LocalDate.of(2019, 8, 8)));
        System.out.println(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(2019, 8, 8)));
    }

    @Test
    public void convert() {
        Date date = new Date();
        System.out.println("current date: " + date);

        // Date -> LocalDateTime
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime by toInstant: " + localDateTime);

        //2. Date -> LocalDateTime
        localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        System.out.println("localDateTime by ofInstant: " + localDateTime);

        // Date -> LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("localDate by toInstant: " + localDate);

        // Date -> LocalTime
        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        System.out.println("localTime by toInstant: " + localTime);
    }

    @Test
    public void convert2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime: " + localDateTime);

        // LocalDateTime -> Date
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("LocalDateTime -> current date: " + date);

        // LocalDate -> Date，时间默认都是00
        date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println("LocalDate -> current date: " + date);
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
