package com.daphnis.network.util;

import com.daphnis.mybatis.entity.Account;
import com.daphnis.mybatis.entity.Say;
import com.daphnis.mybatis.mapper.AccountMapper;

import java.io.Reader;
import java.util.List;

import com.daphnis.mybatis.mapper.SayMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {

  private static Logger LOG = LoggerFactory.getLogger(DBUtil.class);

  private static final String ENV = "mysql";

  private static SqlSessionFactory sqlSessionFactory;

  static {
    try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, ENV);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static SqlSession openSession() {
    return sqlSessionFactory.openSession();
  }

  /**
   * 从数据库中查询账户信息
   *
   * @param userName
   * @return
   */
  public static Account queryAccount(String userName) {
    try (SqlSession sqlSession = openSession()) {
      AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
      return accountMapper.selectOneAccount(userName);
    } catch (Exception e) {
      LOG.error("query account from db error !!", e);
    }

    return new Account();
  }

  /**
   * 查询名人名言列表
   *
   * @return
   */
  public static List<Say> querySays() {
    try (SqlSession sqlSession = openSession()) {
      SayMapper sayMapper = sqlSession.getMapper(SayMapper.class);
      List<Say> says = sayMapper.selectSays();

      return says;
    } catch (Exception e) {
      LOG.error("query say list from db error !!", e);
    }

    return Lists.newArrayList();
  }


}
