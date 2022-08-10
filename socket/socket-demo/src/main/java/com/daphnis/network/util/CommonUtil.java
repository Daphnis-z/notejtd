package com.daphnis.network.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

  /**
   * 格式化 以毫秒表示的时间戳为 yyyy-MM-dd HH:mm:ss 格式
   *
   * @param mills
   * @return
   */
  public static String formatTimeMills(long mills) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(mills);
  }

  /**
   * 获取当前 日期时间（eg.20200618）
   *
   * @return
   */
  public static String getCurrentShortDate() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    return format.format(System.currentTimeMillis());
  }

  /**
   * 获取当前 日期时间（eg.20200618130049）
   *
   * @return
   */
  public static String getCurrentShortDateTime() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    return format.format(System.currentTimeMillis());
  }

  /**
   * 获取当前 日期时间（eg.2020-06-18 13:00:49）
   *
   * @return
   */
  public static String getCurrentStdDateTime() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(System.currentTimeMillis());
  }

  /**
   * 把分钟换算成毫秒
   *
   * @param minute
   * @return
   */
  public static long minute2Mills(int minute) {
    return minute * 60000;
  }

  /**
   * yyyyMMddHHmm 转 yyyy-MM-dd HH:mm:ss
   *
   * @param dateMinute
   * @return
   */
  public static String shortDateMinute2StdDateTime(String dateMinute) throws ParseException {
    SimpleDateFormat minFmt = new SimpleDateFormat("yyyyMMddHHmm");
    Date date = minFmt.parse(dateMinute);

    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
  }

  /**
   * 生成 UUID
   *
   * @return
   */
  public static String createUuid() {
    String uuid = UUID.randomUUID().toString();
    return uuid.replace("-", "").toUpperCase();
  }
}
