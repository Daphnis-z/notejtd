package com.daphnis.mybatis;

import com.daphnis.mybatis.entity.Goods;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisDemo {

  private static SqlSessionFactory sqlSessionFactory;

  public static void show(Object msg) {
    System.out.println(msg.toString());
  }

  public static void main(String... args) throws Exception {
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    SqlSession sqlSession = null;
    try {
      sqlSession = sqlSessionFactory.openSession();

      List<Goods> goodsList = sqlSession.selectList("selectAllGoods");
      for (int i = 0; i < goodsList.size(); i++) {
        show(goodsList.get(i));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (sqlSession != null) {
        sqlSession.close();
      }
    }
  }
  
}
