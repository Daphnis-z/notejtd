# 1.总体介绍

MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的**持久层框架**。

MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集，可以对配置和原生 Map 使用简单的 XML 或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java 对象)映射成数据库中的记录。

## 1.1 功能架构

- **API 接口层**

  提供给外部使用的接口 API，开发人员通过这些本地 API 来操纵数据库。接口层一接收到调用请求就会调用数据处理层来完成具体的数据处理。 

- **数据处理层**

  负责具体的 SQL 查找、解析、执行和执行结果映射处理等。主要的目的是根据调用的请求完成一次数据库操作。 

- **基础支撑层**

  负责最基础的功能支撑，包括连接管理、事务管理、配置加载和缓存处理，这些都是共用的东西，将他们抽取出来作为最基础的组件。为上层的数据处理层提供最基础的支撑。 

## 1.2 优缺点

**优点：**

- **简单易学**，本身就很小且简单。没有任何第三方依赖，最简安装只要两个 jar 文件+配置几个 sql 映射文件。易于学习使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。 
- **灵活**，mybatis 不会对应用程序或者数据库的现有设计强加任何影响。 sql 写在 xml 里，便于统一管理和优化。通过 sql 基本上可以实现我们不使用数据访问框架可以实现的所有功能，或许更多。 
- **解除 sql 与程序代码的耦合**，通过提供 DAL 层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql 和代码的分离，提高了可维护性。 
- **提供映射标签**，支持对象与数据库的 orm 字段关系映射 
- **提供对象关系映射标签**，支持对象关系组建维护 
- **提供 xml 标签**，支持编写动态sql。 

**缺点：**

- 编写 SQL 语句时工作量很大，尤其是字段多、关联表多时，更是如此。 
- SQL 语句依赖于数据库，导致数据库移植性差，不能更换数据库。 
- 框架还是比较简陋，功能尚有缺失，虽然简化了数据绑定代码，但是整个底层数据库查询实际还是要自己写的，工作量也比较大，且不太容易适应快速数据库修改。 
- 二级缓存机制不佳 

# 2.配置文件

mybatis 对配置文件里面标签的顺序是有要求的，比如 environments 要放在 properties 后面，否则会报错，如下：

```xml
<configuration>
  <properties resource="db.properties">
    <property name="username" value="daphnis"/>
  </properties>
  <settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
  </settings>
  <typeAliases>
    <package name="com.daphnis.mybatis.entity"/>
  </typeAliases>

  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"></transactionManager>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>

  <mappers>
    <package name="com.daphnis.mybatis.mapper"/>
  </mappers>
</configuration>
```

## 2.1 配置 properties

```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
```

这个 properties 里面配置的值可以在别的地方使用 ${propName} 进行引用，有点类似 pom 文件里的 properties。resource 指定文件里面的值可以覆盖 property ，一般在这个文件里面配置数据库连接驱动、用户名、密码等，比较方便管理。

mybatis 使用 property 的顺序如下：

- 在 properties 元素体内指定的属性首先被读取。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件或根据 url 属性指定的路径读取属性文件，并覆盖已读取的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖已读取的同名属性。

## 2.2 配置 environments

```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC"></transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中。例如，开发、测试和生产环境需要有不同的配置，或者共享相同 Schema 的多个生产数据库。

尽管可以配置多个环境，但是每个 SqlSessionFactory 实例只能选择其一，也就是说 **SqlSessionFactory  和 environment 是一对一**的关系。

**transactionManager，事务管理器：**

- **JDBC**

  这个配置就是直接使用了 JDBC 的提交和回滚设置，依赖于从数据源得到的连接来管理事务范围。

- **MANAGED**

  这个配置从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接，然而一些容器并不希望这样，因此需要将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。

**dataSource 比较好理解，就是配置数据库连接需要的驱动名称、url、用户名和密码。**

**mybatis 有三种内建的数据源类型（type="[UNPOOLED|POOLED|JNDI]"）：**

- **POOLED**

  利用"池"的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这是一种使得并发 Web 应用快速响应请求的流行处理方式。

  有一些额外的参数可以配置：

  ```
  # 任意时刻最大活动连接数量，默认是 10个
  poolMaximumActiveConnections
  
  # 任意时刻最大空闲连接数
  poolMaximumIdleConnections
  
  # 获取连接的超时时间，默认 20秒，超时后会打印日志并尝试重新获取一个连接
  poolTimeToWait
  ```

- **UNPOOLED**

  每次被请求时打开和关闭连接。有一点慢，适用于对及时可用连接方面没有性能要求的场景。 

- **JNDI**

  能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。

## 2.3 配置 settings

MyBatis 中极为重要的调整设置，可以改变 MyBatis 的运行时行为。

**列举几个常用的配置项：**

- **useGeneratedKeys**

  允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作。

  默认值是 false .

- **mapUnderscoreToCamelCase**

  是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。

  默认是 false .

- **cacheEnabled**

  该配置影响的所有映射器中配置的缓存的全局开关。

  默认是 true .

- **lazyLoadingEnabled**

  延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。

  默认是 false .

- **logImpl**

  指定 MyBatis 所用日志的具体实现，未指定时将自动查找。

  可选项：SLF4J,LOG4J,LOG4J2,JDK_LOGGING,COMMONS_LOGGING,STDOUT_LOGGING,NO_LOGGING

## 2.4 配置 typeAliases

类型别名是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

一般指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean ，如下：

```xml
<typeAliases>
	<package name="com.daphnis.mybatis.entity"/>
</typeAliases>
```

在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；若有注解，则别名为其注解值。如下：

```java
@Alias("BlogAuthor")
public class Author {
	...
}
```

## 2.5 配置 mappers

mappers 是用来配置 sql 映射语句的，需要配置 sql 映射文件的位置，一般是配置包的名称：

```xml
<mappers>
  <package name="com.daphnis.mybatis.mapper"/>
</mappers>
```

这样 mybatis 就会自动去扫描该路径下的 sql 映射文件。

# 3. 插件

MyBatis 允许在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定了想要拦截的方法签名即可。

# 4. SQL 映射文件

# 5. 动态 SQL









# 参考资料：

1. [W3Cschool-MyBatis 教程](https://www.w3cschool.cn/mybatis/7zy61ilv.html)