package com.daphnis.network.client;

import com.daphnis.network.util.CommonUtil;
import com.daphnis.network.util.ConfigUtil;
import com.daphnis.network.util.SocketUtil;
import com.google.common.base.Charsets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkClient {

  private static Logger LOG = LoggerFactory.getLogger(NetworkClient.class);

  public void start() {
    LOG.info("socket client start ..");

    String serverName = ConfigUtil.getServerHost();
    try {
      LOG.info(String
          .format("connect to server: %s on port: %s", serverName, ConfigUtil.getServerPort()));
      Socket socket = new Socket(serverName, ConfigUtil.getServerPort());
      LOG.info(String.format("connect %s success..", socket.getRemoteSocketAddress()));

      BufferedReader bufferReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream(), Charsets.UTF_8));
      BufferedWriter bufferWriter = new BufferedWriter(
          new OutputStreamWriter(socket.getOutputStream(), Charsets.UTF_8));

      boolean heartEnable = true;
      String msg;

//      SocketUtil.writeMessage2Stream(
//          String.format("LOGIN||UserName=%s,Password=%s", "admin", "inms123456"),
//          bufferWriter);

      // 在服务端进行验证
      Scanner scanner = new Scanner(System.in);
      System.out.println("请输入用户名（Enter 结束输入）：");
      String userName = scanner.nextLine();
      System.out.println("请输入密码（Enter 结束输入）：");
      String password = scanner.nextLine();
      SocketUtil.writeMessage2Stream(
          String.format("LOGIN||UserName=%s,Password=%s", userName, password), bufferWriter);

      // 解析服务端返回的认证结果
      msg = bufferReader.readLine();
      if (msg.startsWith("LOGIN-RESULT||VerifyResult=0")) {
        System.out.println("receive from server: " + msg);
        System.out.println("已通过服务端认证..");

        System.out.println("请输入是否回复服务端的心跳验证（0:不回复，1:回复）：");
        heartEnable = "1".equals(scanner.nextLine());
      } else {
        System.out.println("服务端认证失败：" + msg);
        return;
      }

      // 开始正式接受服务端的消息
      while (true) {
        msg = bufferReader.readLine();
        System.out.println("receive from server: " + msg);

        if (msg.startsWith("HEARTBEAT") && heartEnable) {
          SocketUtil
              .writeMessage2Stream("HEARTBEAT||client-time=" + CommonUtil.getCurrentShortDateTime(),
                  bufferWriter);
        }

        Thread.sleep(1000);
      }

//      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


}




