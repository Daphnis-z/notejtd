package com.daphnis.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * java8 提供的线程安全的时间解析格式化类
 */
public class DateTimeFormatDemo{

    public static void main(String... args) {
        DateTimeFormatter hourFormatter=DateTimeFormatter.ofPattern("yyyyMMddHH");
        DateTimeFormatter stdFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化时间
        String toHour=hourFormatter.format(LocalDateTime.now());
        System.out.println("格式化时间到小时： "+toHour);
        String stdDateTime=stdFormatter.format(LocalDateTime.now());
        System.out.println("格式化时间为标准时间： "+stdDateTime);

        // 解析时间
        String parseTime=stdFormatter.format(hourFormatter.parse("2019100511"));
        System.out.println("解析出来的时间： "+parseTime);
    }
}