package com.daphnis.mybatis.plugin;

import com.daphnis.mybatis.entity.Goods;
import com.daphnis.mybatis.entity.PageInfo;
import com.daphnis.mybatis.mapper.GoodsMapper;
import com.daphnis.mybatis.util.CommonUtil;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PagePluginTest {

  private SqlSession sqlSession;
  private GoodsMapper goodsMapper;

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
  public void testPageOne() {
    PageInfo page = new PageInfo(0, 2);
    List<Goods> goodsList = goodsMapper.selectGoodsListPage(page);

    CommonUtil.showMsg("page one >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    CommonUtil.showMsg(page);
    for (Goods goods : goodsList) {
      CommonUtil.showMsg(goods);
    }
  }

  @Test
  public void testPageTwo() {
    PageInfo page = new PageInfo(2, 2);
    List<Goods> goodsList = goodsMapper.selectGoodsListPage(page);

    CommonUtil.showMsg("page two >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    CommonUtil.showMsg(page);

    for (Goods goods : goodsList) {
      CommonUtil.showMsg(goods);
    }
  }
}