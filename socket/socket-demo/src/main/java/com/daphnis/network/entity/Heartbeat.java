package com.daphnis.network.entity;

public class Heartbeat {

  private boolean sendHeart = false;
  private boolean receiveHeart = false;

  private int failTimes = 0;

  public boolean isSendHeart() {
    return sendHeart;
  }

  public void setSendHeart(boolean sendHeart) {
    this.sendHeart = sendHeart;
  }

  public boolean isReceiveHeart() {
    return receiveHeart;
  }

  public void setReceiveHeart(boolean receiveHeart) {
    this.receiveHeart = receiveHeart;
  }

  public int getFailTimes() {
    return failTimes;
  }

  public void setFailTimes(int failTimes) {
    this.failTimes = failTimes;
  }
}
