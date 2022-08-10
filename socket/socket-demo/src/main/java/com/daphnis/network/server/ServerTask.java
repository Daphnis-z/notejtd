package com.daphnis.network.server;

import com.daphnis.mybatis.entity.Account;
import com.daphnis.mybatis.entity.Say;
import com.daphnis.network.entity.Heartbeat;
import com.daphnis.network.protocol.BaseProtocol;
import com.daphnis.network.protocol.HeartbeatProtocol;
import com.daphnis.network.protocol.LoginProtocol;
import com.daphnis.network.util.CommonUtil;
import com.daphnis.network.util.ConfigUtil;
import com.daphnis.network.util.DBUtil;
import com.daphnis.network.util.ProtocolUtil;
import com.daphnis.network.util.SocketUtil;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerTask implements Runnable {

  private static Logger LOG = LoggerFactory.getLogger(ServerTask.class);

  Heartbeat heartbeat = new Heartbeat();
  boolean stopTask = false;

  Socket socket;
  BufferedReader bufferReader;
  BufferedWriter bufferWriter;

  String clientAddress;
  Account account;

  // 存储从客户端收到的消息
  ConcurrentLinkedQueue<BaseProtocol> protocolQueue = new ConcurrentLinkedQueue<>();
  ConcurrentLinkedQueue<WriteTask> writeTaskQueue = new ConcurrentLinkedQueue<>();

  long heartIntervalMillis;
  long lastHeartTime;

  // 客户端验证结果
  private boolean clientVerifyResult = false;
  private String sessionId;

  public ServerTask(Socket socket) {
    this.socket = socket;
  }

  private boolean init() {
    try {
      bufferReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream(), Charsets.UTF_8));
      bufferWriter = new BufferedWriter(
          new OutputStreamWriter(socket.getOutputStream(), Charsets.UTF_8));
      clientAddress = socket.getRemoteSocketAddress().toString();

      heartIntervalMillis = ConfigUtil.getHeartbeatInterval() * 1000;
      lastHeartTime = 0;

      return true;
    } catch (Exception e) {
      LOG.error("init server task error !!", e);
    }

    return false;
  }

  @Override
  public void run() {
    if (!init()) {
      close();
      return;
    }
    LOG.info("start a server task,client address: " + clientAddress);

    ServerReadTask readTask = new ServerReadTask();
    ServerWriteTask writeTask = new ServerWriteTask();
    readTask.start();
    writeTask.start();

    long loginTimeout = ConfigUtil.getLoginTimeout() * 1000;
    long connectStartTime = System.currentTimeMillis();

    // 读写线程可能由于读写 Socket 出错而将 stopTask 置为 true
    while (!stopTask) {
      try {
        // 这里对客户端登录状态进行检查，登录超时则断开连接
        if (!clientVerifyResult && System.currentTimeMillis() - connectStartTime > loginTimeout) {
          SocketUtil
              .writeMessage2Stream(
                  String.format("client login timeout(%s seconds) !", ConfigUtil.getLoginTimeout()),
                  bufferWriter);
          LOG.info(String.format("verify client timeout: %s s,will close socket..",
              ConfigUtil.getLoginTimeout()));
          break;
        }

        if (clientVerifyResult) {
          executeTimingTasks();
        }

        if (protocolQueue.isEmpty()) {
          Thread.sleep(2000);
          continue;
        }

        BaseProtocol protocol = protocolQueue.poll();
        if (protocol instanceof LoginProtocol) {
          LoginProtocol loginProto = (LoginProtocol) protocol;
          String errMsg = loginProto.verifyClient();
          if (!Strings.isNullOrEmpty(errMsg)) {
            // 客户端验证失败
            SocketUtil
                .writeMessage2Stream(ProtocolUtil.createLoginResp(false, errMsg), bufferWriter);
            LOG.info(String.format("verify client: %s fail,message: %s", clientAddress, errMsg));
            break;
          }

          // 客户端验证通过
          clientVerifyResult = true;
          account = loginProto.getAccount();
          sessionId = CommonUtil.createUuid();
          List<String> writeMsgs = Lists.newArrayList(ProtocolUtil.createLoginResp(true, errMsg));
          writeTaskQueue.add(new WriteTask("LoginResult", writeMsgs));
          LOG.info(String.format("verify client: %s success..", clientAddress));
          continue;
        }

        // 客户端验证通过后再执行其他业务
        if (!clientVerifyResult) {
          continue;
        }

        if (protocol instanceof HeartbeatProtocol) {
          heartbeat.setReceiveHeart(true);
        }
      } catch (Exception e) {
        LOG.error("server task error !!", e);
      }
    }

    close();
  }

  /**
   * 执行定时任务：心跳检测和名人名言推送
   */
  private void executeTimingTasks() throws IOException {
    if (System.currentTimeMillis() - lastHeartTime >= heartIntervalMillis) {
      // 做心跳检测
      if (heartbeat.isSendHeart()) {
        if (heartbeat.isReceiveHeart()) {
          heartbeat = new Heartbeat();
        }

        heartbeat.setFailTimes(heartbeat.getFailTimes() + 1);
        if (heartbeat.getFailTimes() >= ConfigUtil.getHeartLimitTimes()) {
          SocketUtil.writeMessage2Stream(
              String.format("连续 %s个心跳包没有收到客户端的回复，服务端即将关闭连接！", heartbeat.getFailTimes()),
              bufferWriter);
          LOG.warn(String.format("do not get heartbeat reply from client %s times !",
              heartbeat.getFailTimes()));
          stopTask = true;
        }
      }

      // 发送心跳包
      String heartMsgs = ProtocolUtil.createHeartMsg();
      writeTaskQueue.add(new WriteTask("Heartbeat", Lists.newArrayList(heartMsgs)));
      heartbeat.setSendHeart(true);
      lastHeartTime = System.currentTimeMillis();
    }

    // 随机发送名人名言
    Random rd=new Random();
    if(rd.nextBoolean()){
      List<Say> says=DBUtil.querySays();
      writeTaskQueue.add(new WriteTask("PeopleSay",
              Lists.newArrayList(says.get(rd.nextInt(says.size())).toString())));
    }
  }

  public Socket getSocket() {
    return this.socket;
  }

  private void close() {
    try {
      // 延迟 5秒，让客户端读取 Socket 里剩余的消息
      Thread.sleep(5000);

      socket.close();
    } catch (Exception e) {
      LOG.error("server task close error !!", e);
    }
    LOG.info("socket is closed,client address: " + clientAddress);
  }

  public class ServerReadTask extends Thread {

    @Override
    public void run() {
      LOG.info("start server read task,client address: " + clientAddress);

      try {
        while (!stopTask) {
          BaseProtocol baseProtocol = SocketUtil.readMessageFromStream(bufferReader);

          if (baseProtocol.getMessage() == null) {
            LOG.info("read socket message is null,maybe client closed socket..");
            break;
          }
          LOG.info("receive client message: " + baseProtocol.getMessage());
          protocolQueue.offer(baseProtocol);
        }
      } catch (EOFException ee) {
        LOG.debug("read socket stream error !", ee);
        LOG.info("can not read socket,maybe client closed socket..");
      } catch (SocketException se) {
        LOG.debug("read socket stream error !", se);
        LOG.info("can not read socket,maybe client closed socket..");
      } catch (Exception e) {
        LOG.error("server read task error !!", e);
      }

      stopTask = true;
    }

  }

  public class ServerWriteTask extends Thread {

    @Override
    public void run() {
      LOG.info("start server write task,client address: " + clientAddress);

      try {
        while (!stopTask) {
          if (writeTaskQueue.isEmpty()) {
            Thread.sleep(2000);
            continue;
          }

          WriteTask writeTask = writeTaskQueue.poll();
          LOG.info("start execute " + writeTask);
          SocketUtil.writeMessageList2Stream(writeTask.getMessages(), bufferWriter);

          LOG.info(String.format("execute %s success..", writeTask));
        }
      } catch (SocketException se) {
        LOG.debug("write socket stream error !", se);
        LOG.info("can not write socket,maybe client closed socket..");
      } catch (Exception e) {
        LOG.error("server write task error !!", e);
      }

      stopTask = true;
    }

  }


}
