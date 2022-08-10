package com.daphnis.mybatis.entity;

public class Account {

  private String userId;
  private String userName;
  private String userPwd;

  public Account() {
    this.userId = "";
    this.userName = "";
    this.userPwd = "";
  }

  public Account(String userName, String password) {
    this.userName = userName;
    this.userPwd = password;
  }

  public Account(String userId, String userName, String password) {
    this.userId = userId;
    this.userName = userName;
    this.userPwd = password;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }
}
