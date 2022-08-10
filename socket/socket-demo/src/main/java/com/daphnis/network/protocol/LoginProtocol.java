package com.daphnis.network.protocol;

import com.daphnis.mybatis.entity.Account;
import com.daphnis.network.util.DBUtil;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginProtocol extends BaseProtocol {

  private static Logger LOG = LoggerFactory.getLogger(LoginProtocol.class);

  private Account account;

  public static final String MSG_TYPE = "LOGIN";

  @Override
  public void analysisMessage(String message) {
    // LOGIN||UserName=admin,Password=inms123456
    super.analysisMessage(message);

    // 取出用户名和密码
    String userName = "";
    String password = "";
    String[] data = super.msgContent.split(",");
    if (data.length == 2) {
      String[] userData = data[0].split("=");
      if (userData.length == 2) {
        userName = userData[1];
      }

      String[] pwdData = data[1].split("=");
      if (pwdData.length == 2) {
        password = pwdData[1];
      }
    }

    account = new Account(userName, password);
  }

  public Account getAccount() {
    return account;
  }

  /**
   * 验证客户端
   *
   * @return 错误信息（为空说明校验通过）
   */
  public String verifyClient() {
    try {
      if (Strings.isNullOrEmpty(account.getUserName()) || Strings
          .isNullOrEmpty(account.getUserPwd())) {
        return "缺少用户名或密码";
      }

      Account tgtAccount = DBUtil.queryAccount(account.getUserName());
      if (tgtAccount == null || !account.getUserPwd().equals(tgtAccount.getUserPwd())) {
        return "用户名或密码错误";
      }

      account = tgtAccount;
      return "";
    } catch (Exception e) {
      LOG.error("validate client error !!", e);
    }

    return "服务器出现异常";
  }
}
