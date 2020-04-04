package com.daphnis.mybatis.mapper;

import com.daphnis.mybatis.entity.Goods;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoodsMapperTest {

  private SqlSessionFactory sqlSessionFactory;
  private SqlSession sqlSession;

  void show(Object msg) {
    System.out.println(msg.toString());
  }

  @Before
  public void setUp() throws Exception {
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    sqlSession = sqlSessionFactory.openSession();
  }

  @After
  public void tearDown() {
    if (sqlSession != null) {
      sqlSession.close();
    }
  }

  @Test
  public void testSelectAllGoods() {
    List<Goods> allGoods = sqlSession.selectList("selectAllGoods");

    show("all goods count: " + allGoods.size());
    for (int i = 0; i < allGoods.size(); i++) {
      show(allGoods.get(i));
    }
  }

  @Test
  public void testSelectOneGoods() {
    show("take a goods:");

    Goods goods2 = sqlSession.selectOne("selectOneGoods", "G002");
    show(goods2);
  }

  @Test
  public void testInsertOneGoods() {
    Goods goods = new Goods("G011", "Spark Calculate", 60, 200);
    show("add a goods: " + goods);

    int result = sqlSession.insert("insertOneGoods", goods);
    if (result > 0) {
      show("add a goods success ..");
    }

    sqlSession.commit();
  }

  @Test
  public void testDeleteOneGoods() {
    String goodsId = "G011";
    show("delete goods: " + goodsId);

    int result = sqlSession.delete("deleteOneGoods", goodsId);
    if (result > 0) {
      show("delete goods success ..");
    }

    sqlSession.commit();
  }

  @Test
  public void testUpdateOneGoods() {
    Goods goods = new Goods("G002", "JVM-Simple", 25, 80);
    show("update goods: " + goods.getGoodsId());

    int result = sqlSession.update("updateOneGoods", goods);
    if (result > 0) {
      show("update goods success ..");
    }

    sqlSession.commit();
  }
}