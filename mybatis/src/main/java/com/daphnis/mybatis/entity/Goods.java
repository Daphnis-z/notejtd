package com.daphnis.mybatis.entity;

public class Goods {

  private int goodsId;
  private String goodsName;
  private double price;
  private int count;

  public Goods(String goodsName, double price, int count) {
    this.goodsName = goodsName;
    this.price = price;
    this.count = count;
  }

  public Goods(int goodsId, String goodsName, double price, int count) {
    this.goodsId = goodsId;
    this.goodsName = goodsName;
    this.price = price;
    this.count = count;
  }

  public int getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(int goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "Goods{" +
        "goodsId='" + goodsId + '\'' +
        ", goodsName='" + goodsName + '\'' +
        ", price=" + price +
        ", count=" + count +
        '}';
  }
}
