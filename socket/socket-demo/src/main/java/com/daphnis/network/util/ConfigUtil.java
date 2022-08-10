package com.daphnis.network.util;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {

  private static Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

  private static Properties prop = new Properties();

  static {
    try {
      prop.load(ConfigUtil.class.getClassLoader().getResourceAsStream("network.properties"));
    } catch (Exception e) {
      LOG.error("read config file error !!", e);
    }
  }

  public static String getServerHost() {
    try {
      return prop.getProperty("server.host");
    } catch (Exception e) {
      LOG.error("read config error !!", e);
    }

    return "127.0.0.1";
  }

  public static int getServerPort() {
    try {
      return Integer.parseInt(prop.getProperty("server.port"));
    } catch (Exception e) {
      LOG.error("read config error !!", e);
    }

    return 8686;
  }

  public static int getHeartbeatInterval() {
    return Integer.parseInt(prop.getProperty("heartbeat.interval.seconds", "60"));
  }

  public static int getHeartLimitTimes() {
    return Integer.parseInt(prop.getProperty("heartbeat.limit.times", "3"));
  }

  public static int getLoginTimeout() {
    return Integer.parseInt(prop.getProperty("login.timeout.seconds", "30"));
  }

}
