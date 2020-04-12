package com.daphnis.mybatis.entity;

public class PageInfo {

  private int currentResult;
  private int showCount;
  private int totalResult;


  public PageInfo() {
  }

  public PageInfo(int currentResult, int showCount) {
    this.currentResult = currentResult;
    this.showCount = showCount;
  }

  public int getCurrentResult() {
    return currentResult;
  }

  public void setCurrentResult(int currentResult) {
    this.currentResult = currentResult;
  }

  public int getShowCount() {
    return showCount;
  }

  public void setShowCount(int showCount) {
    this.showCount = showCount;
  }

  public int getTotalResult() {
    return totalResult;
  }

  public void setTotalResult(int totalResult) {
    this.totalResult = totalResult;
  }

  @Override
  public String toString() {
    return "PageInfo{" +
        "currentResult=" + currentResult +
        ", showCount=" + showCount +
        ", totalResult=" + totalResult +
        '}';
  }
}
