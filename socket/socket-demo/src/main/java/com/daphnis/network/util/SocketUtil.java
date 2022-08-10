package com.daphnis.network.util;

import com.daphnis.network.protocol.BaseProtocol;
import com.daphnis.network.protocol.HeartbeatProtocol;
import com.daphnis.network.protocol.LoginProtocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketUtil {

  private static Map<String, String> msgType2ClassName = new HashMap<>();
  private static final String PKG_NAME = "com.daphnis.network.protocol";

  static {
    msgType2ClassName.put(LoginProtocol.MSG_TYPE, PKG_NAME + ".LoginProtocol");
    msgType2ClassName.put(HeartbeatProtocol.MSG_TYPE, PKG_NAME + ".HeartbeatProtocol");
  }


  public static String readUtf8(DataInputStream inputStream) throws IOException {
    byte[] bytes = new byte[1024];
    int len;
    StringBuilder sb = new StringBuilder();

    int n = inputStream.readUnsignedShort();

    while ((len = inputStream.read(bytes)) != -1) {
      //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
      String str = new String(bytes, 0, len, "UTF-8");
      sb.append(str);

      if (str.endsWith("\n")) {
        sb.deleteCharAt(sb.length() - 1);
        break;
      }
    }

    return sb.toString();
  }


  /**
   * 从 Socket流 读取单条消息
   *
   * @param reader
   * @return
   */
  public static BaseProtocol readMessageFromStream(BufferedReader reader)
      throws IOException {
    String msg = reader.readLine();
    String msgType = BaseProtocol.getMessageType(msg);

    if (msgType2ClassName.containsKey(msgType)) {
      String className = msgType2ClassName.get(msgType);
      try {
        BaseProtocol baseProtocol = (BaseProtocol) Class.forName(className).newInstance();
        baseProtocol.analysisMessage(msg);
        return baseProtocol;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return new BaseProtocol(msg);
  }

  /**
   * 往 Socket流 写入单条消息
   *
   * @param message
   * @param writer
   * @throws IOException
   */
  public static void writeMessage2Stream(String message, BufferedWriter writer)
      throws IOException {
    writer.write(message + '\n');
    writer.flush();
  }

  /**
   * 往 Socket流 写入多条消息
   *
   * @param messages
   * @param writer
   * @throws IOException
   */
  public static void writeMessageList2Stream(List<String> messages, BufferedWriter writer)
      throws IOException {
    for (String message : messages) {
      writeMessage2Stream(message, writer);
    }
  }


}
