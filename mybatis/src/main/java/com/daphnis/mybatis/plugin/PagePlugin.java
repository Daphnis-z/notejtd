package com.daphnis.mybatis.plugin;

import com.daphnis.mybatis.entity.PageInfo;
import com.daphnis.mybatis.util.CommonUtil;
import com.daphnis.mybatis.util.ReflectUtil;
import com.google.common.base.Strings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.type.TypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,
    Integer.class})})
public class PagePlugin implements Interceptor {

  private static final Logger LOG = LoggerFactory.getLogger(PagePlugin.class);

  private String dialect;
  private String pageSqlId;

  /**
   * 进行拦截时执行的方法
   *
   * @param invocation
   * @return
   * @throws Throwable
   */
  public Object intercept(Invocation invocation) throws Throwable {
    if (invocation.getTarget() instanceof RoutingStatementHandler) {
      RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
      BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil
          .getValueByFieldName(statementHandler, "delegate");
      MappedStatement mappedStatement = (MappedStatement) ReflectUtil
          .getValueByFieldName(delegate, "mappedStatement");

      // 只拦截指定 id 的 sql
      if (!mappedStatement.getId().matches(pageSqlId)) {
        return invocation.proceed();
      }

      BoundSql boundSql = delegate.getBoundSql();
      if (boundSql.getParameterObject() instanceof PageInfo) {
        PageInfo pageInfo = (PageInfo) boundSql.getParameterObject();

        Connection connection = (Connection) invocation.getArgs()[0];
        int dataCount = queryDataCount(connection, boundSql);
        pageInfo.setTotalResult(dataCount);

        String pageSql = generatePageSql(boundSql.getSql(), pageInfo);
        CommonUtil.showMsg("page sql: " + pageSql);
        ReflectUtil.setValueByFieldName(boundSql, "sql", pageSql);
      } else {
        throw new TypeException("boundSql.getParameterObject() is not PageInfo !!");
      }
    }

    return invocation.proceed();
  }

  /**
   * 查询总数据量
   *
   * @param connection
   * @param boundSql
   * @return 总数据量
   */
  private int queryDataCount(Connection connection, BoundSql boundSql) {
    String countSql = String.format("select count(1) from (%s) allData", boundSql.getSql());
    CommonUtil.showMsg("统计总数据量 sql: " + countSql);

    int dataCount = 0;
    try (PreparedStatement countStmt = connection.prepareStatement(countSql)) {
      ResultSet rs = countStmt.executeQuery();
      if (rs.next()) {
        dataCount = rs.getInt(1);
      }

      rs.close();
    } catch (Exception e) {
      LOG.error("query data count error !!", e);
    }

    return dataCount;
  }

  private String generatePageSql(String sql, PageInfo page) {
    if (page != null && (dialect != null || !dialect.equals(""))) {
      StringBuffer pageSql = new StringBuffer();
      if ("mysql".equals(dialect)) {
        pageSql.append(sql);
        pageSql.append(" limit " + page.getCurrentResult() + "," + page.getShowCount());
      } else if ("oracle".equals(dialect)) {
        pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
        pageSql.append(sql);
        pageSql.append(")  tmp_tb where ROWNUM<=");
        pageSql.append(page.getCurrentResult() + page.getShowCount());
        pageSql.append(") where row_id>");
        pageSql.append(page.getCurrentResult());
      }
      return pageSql.toString();
    } else {
      return sql;
    }
  }

  /**
   * 用于封装目标对象
   *
   * @param target
   * @return 目标对象或者目标对象的的代理
   */
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  /**
   * 读取 plugin 里面配置的属性
   *
   * @param properties
   */
  public void setProperties(Properties properties) {
    dialect = properties.getProperty("dialect");
    assert !Strings.isNullOrEmpty(dialect);

    pageSqlId = properties.getProperty("pageSqlId");
    assert !Strings.isNullOrEmpty(pageSqlId);
  }

}