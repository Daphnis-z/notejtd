package com.daphnis.network.util;

public class ProtocolUtil {

  /**
   * 创建登录结果应答消息
   *
   * @param validateResult
   * @param faultReason
   * @return
   */
  public static String createLoginResp(boolean validateResult, String faultReason) {
    // LOGIN-RESULT||VerifyResult=0,FaultReason=
    // 0:成功，1:失败
    return String.format("LOGIN-RESULT||VerifyResult=%s,FaultReason=%s",
        validateResult ? 0 : 1, faultReason);
  }

  /**
   * 创建服务端发送给客户端的心跳消息
   *
   * @return
   */
  public static String createHeartMsg() {
    return String.format("HEARTBEAT||server-time=%s", CommonUtil.getCurrentShortDateTime());
  }

}
