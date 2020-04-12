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

  private SqlSession sqlSession;
  private GoodsMapper goodsMapper;

  void show(Object msg) {
    System.out.println(msg.toString());
  }

  @Before
  public void setUp() throws Exception {
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    // 设置自动提交
    sqlSession = sessionFactory.openSession(true);

    goodsMapper = sqlSession.getMapper(GoodsMapper.class);
  }

  @After
  public void tearDown() {
    if (sqlSession != null) {
      sqlSession.close();
    }
  }

  @Test
  public void testSelectAllGoods() {
    // 这种是官方推荐的最新方式，代码清晰、类型安全，还避免了字符串常量
    GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
    List<Goods> allGoods = mapper.selectAllGoods();

    show("all goods count: " + allGoods.size());
    for (int i = 0; i < allGoods.size(); i++) {
      show(allGoods.get(i));
    }
  }

  @Test
  public void testSelectOneGoods() {
    show("take a goods:");

    Goods goods2 = goodsMapper.selectOneGoods(2);
    show(goods2);
  }

  @Test
  public void testInsertOneGoods() {
    Goods goods = new Goods("Spark Calculate", 60, 200);
    show("add a goods: " + goods);

    int result = goodsMapper.insertOneGoods(goods);
    if (result > 0) {
      show("add a goods success ..");
    }

    show("auto generate goods id: " + goods.getGoodsId());
  }

  @Test
  public void testDeleteOneGoods() {
    int goodsId = 7;
    show("delete goods: " + goodsId);

    int result = goodsMapper.deleteOneGoods(goodsId);
    if (result > 0) {
      show("delete goods success ..");
    }
  }

  @Test
  public void testUpdateOneGoods() {
    Goods goods = new Goods(2, "JVM-Simple", 25, 80);
    show("update goods: " + goods.getGoodsId());

    int result = goodsMapper.updateOneGoods(goods);
    if (result > 0) {
      show("update goods success ..");
    }
  }

}