package com.daphnis.network.server;

import java.util.UUID;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServerTest {

  private static Logger LOG = LoggerFactory.getLogger(NetworkServerTest.class);

  @Test
  public void parseAccount() throws InterruptedException {
    String uuid = UUID.randomUUID().toString();
    System.out.println(uuid.replace("-", "").toUpperCase());

//    LOG.info("hello world");
//    Thread.sleep(155);
//    LOG.error("hello java");
//
//    String host = ConfigUtil.getServerHost();
//    String port = ConfigUtil.getServerPort();
//
//    String str = "ITE-CONN";
  }
}