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

    // 设置自动提交
    sqlSession = sqlSessionFactory.openSession(true);
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

    Goods goods2 = sqlSession.selectOne("selectOneGoods", 2);
    show(goods2);
  }

  @Test
  public void testInsertOneGoods() {
    Goods goods = new Goods("Spark Calculate", 60, 200);
    show("add a goods: " + goods);

    int result = sqlSession.insert("insertOneGoods", goods);
    if (result > 0) {
      show("add a goods success ..");
    }
  }

  @Test
  public void testDeleteOneGoods() {
    int goodsId = 3;
    show("delete goods: " + goodsId);

    int result = sqlSession.delete("deleteOneGoods", goodsId);
    if (result > 0) {
      show("delete goods success ..");
    }
  }

  @Test
  public void testUpdateOneGoods() {
    Goods goods = new Goods("JVM-Simple", 25, 80);
    show("update goods: " + goods.getGoodsId());

    int result = sqlSession.update("updateOneGoods", goods);
    if (result > 0) {
      show("update goods success ..");
    }

    sqlSession.commit();
  }
}