package com.daphnis.network.protocol;

import com.google.common.base.Strings;

public class BaseProtocol {

  private String message;
  protected String msgContent;

  public BaseProtocol() {

  }

  public BaseProtocol(String message) {
    this.message = message;
  }

  /**
   * 获取消息的类型
   *
   * @param message
   * @return
   */
  public static String getMessageType(String message) {
    if (!Strings.isNullOrEmpty(message)) {
      return message.split("\\|\\|")[0];
    }

    return "";
  }

  public void analysisMessage(String message) {
    this.message = message;

    if (!Strings.isNullOrEmpty(message)) {
      String[] data = message.split("\\|\\|");
      if (data.length == 2) {
        msgContent = data[1];
      }
    }
  }

  public String getMessage() {
    return message;
  }

  public String getMsgContent() {
    return msgContent;
  }
}
