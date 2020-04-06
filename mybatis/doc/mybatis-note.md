# 1.总体介绍

MyBatis 是一款优秀的**持久层框架**，支持**自定义 SQL**、**存储过程**以及**高级映射**。

MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

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

**为 property 设置默认值：**

从 MyBatis 3.4.2 开始，可以为占位符指定一个默认值。例如：

```xml
<!-- 如果属性 'username' 没有被配置，'username' 属性的值将为 'ut_user' -->
<property name="username" value="${username:ut_user}"/> 
```

但这个特性默认是关闭的。要启用这个特性，需要添加一个特定的属性来开启这个特性。例如：

```xml
<properties resource="org/mybatis/example/config.properties">
  <!-- 启用默认值特性 -->
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/>
</properties>
```

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

**类型别名**是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

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

MyBatis 的真正强大在于它的**映射语句**，也是它的魔力所在。跟具有相同功能的 JDBC 代码进行对比，几乎省掉了 90%以上的代码。MyBatis 就是针对 SQL 构建的，并且比普通的方法做的更好。

**下面是 SQL 映射文件中几个顶级元素：**

- `cache`

  给定命名空间的缓存配置。

- `cache-ref`

  其他命名空间缓存配置的引用。

- `resultMap`

  **最复杂也是最强大的元素**，用来描述如何从数据库结果集中来加载对象。

- `sql`

  可被其他语句引用的可重用语句块。

- `insert`

- `update`

- `delete`

- `select`

## 4.1 select

先看一个示例：

```xml
<select id="selectOneGoods" resultType="Goods">
  select
  goods_id,goods_name, price, count
  from goods
  where goods_id=#{goodsId}
</select>
```

如何在代码中调用：

```java
goodsMapper = sqlSession.getMapper(GoodsMapper.class);
Goods goods2 = goodsMapper.selectOneGoods(2);
```

注意：SqlSession 是**非线程安全**的，作用域一般是方法内，使用完要及时关闭。

**select 常用属性：**

- **id**

  配置接口里面对应函数的名字，如下：

  ```java
  public interface GoodsMapper {
  
    List<Goods> selectAllGoods();
  
    Goods selectOneGoods(int goodsId);
  }
  ```

- **resultType**

  从这条语句中返回的期望类型的类的完全限定名或别名。一般用**类的别名**。

- **resultMap**

  外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性，对其有一个很好的理解的话，许多复杂映射的情形都能迎刃而解。使用 resultMap 或 resultType，但不能同时使用。

- **flushCache**

  将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：false。

- **useCache**

  将其设置为 true，将会导致本条语句的结果被二级缓存，默认值：对 select 元素为 true。

- **fetchSize**

  这是尝试影响驱动程序每次批量返回的结果行数和这个设置值相等。默认值为 unset（依赖驱动）。

## 4.2 insert

先看一个示例：

```xml
<insert id="insertOneGoods" useGeneratedKeys="true" keyProperty="goodsId">
  insert into
  goods
  (goods_name,price,count)
  values (#{goodsName},#{price},#{count})
</insert>
```

如果数据库有自增字段，可以把 useGeneratedKeys 设为 true ，keyProperty 是 java 里面自增字段的名称，这样每次插入就可以利用数据库自动生成 id 了。

## 4.3 update

```xml
<update id="updateOneGoods">
  update
  goods
  set
  goods_name=#{goodsName},price=#{price},count=#{count}
  where goods_id=#{goodsId}
</update>
```

## 4.4 delete

```xml
<delete id="deleteOneGoods">
  delete from
  goods
  where goods_id=#{goodsId}
</delete>
```

## 4.5 sql

用来定义可重用的 SQL 代码段，可以包含在其他语句中。如下：

```xml
<sql id="tableName">goods</sql>

<delete id="deleteOneGoods">
  delete from
  <include refid="tableName"/>
  where goods_id=#{goodsId}
</delete>
```

## 4.6 resultMap

resultMap 元素是 MyBatis 中最重要最强大的元素。目的是让开发者远离 90% 的需要从结果集中取出数据的 JDBC 代码, 而且在一些情形下允许做一些 JDBC 不支持的事情。

下面是一种比较简单的映射场景：

```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="username"/>
  <result property="password" column="password"/>
</resultMap>   

<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

TODO 还有一些比较高级的映射场景，这个后面有时间再研究下，下面是一个比较高级的映射：

```xml
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```

## 4.7 cache

MyBatis 包含一个非常强大的查询缓存特性，可以非常方便地配置和定制。默认情况下是没有开启缓存的，除了局部的 session 缓存。

开启二级缓存,只需要在 SQL 映射文件中添加一行：

```xml
<cache/>
```

这样配置缓存，一切参数都是使用默认配置，效果如下：

- 映射语句文件中的所有 select 语句将会被缓存
- 映射语句文件中的所有 insert,update 和 delete 语句会刷新缓存
- 缓存会使用 Least Recently Used(LRU,最近最少使用的)算法来收回
- 根据时间表(比如 no Flush Interval,没有刷新间隔), 缓存不会以任何时间顺序来刷新
- 缓存会存储列表集合或对象的 1024 个引用。
- 缓存会被视为是 read/write 的缓存,意味着对象检索不是共享的,而且可以安全地被调用者修改,而不干扰其他调用者或线程所做的潜在修改

**缓存回收的策略如下：**

- `LRU` ——默认策略

  最近最少使用的，移除最长时间不被使用的对象

- `FIFO`

  先进先出，按对象进入缓存的顺序来移除它们

- `SOFT`

  软引用，移除基于垃圾回收器状态和软引用规则的对象

- `WEAK`

  弱引用，更积极地移除基于垃圾收集器状态和弱引用规则的对象

下面是一个使用 FIFO 回收策略的缓存配置：

```xml
<cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
```

# 5. 动态 SQL

MyBatis 的强大特性之一便是它的动态 SQL。通常使用动态 SQL 不可能是独立的一部分,MyBatis 使用一种强大的动态 SQL 语言来改进这种情形,这种语言可以被用在任意的 SQL 映射语句中。动态 SQL 元素和使用 JSTL 或其他类似基于 XML 的文本处理器相似。

**动态 SQL 元素有如下几个：**

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

## 5.1 **if**

```xml
<select id="findActiveBlogWithTitleLike" resultType="Blog">
  SELECT * FROM BLOG 
  WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

## 5.2 choose

```xml
<select id="findActiveBlogLike" resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

可以很容易的看出 choose 有点类似 switch 。

## 5.3 **trim (where, set)**

trim 和 where 可以实现同样的功能，对比如下：

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <where> 
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>

<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <trim prefix="WHERE" prefixOverrides="AND |OR ">
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </trim>
</select>
```

trim 和 set 也可以实现同样的功能，对比如下：

```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>

<update id="updateAuthorIfNecessary">
  update Author
  <trim prefix="SET" suffixOverrides=",">
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
  </trim>
  where id=#{id}
</update>
```

## 5.4 **foreach**

动态 SQL 的另外一个常用的必要操作是需要对一个集合进行遍历，通常是在构建 IN 条件语句的时候。比如：

```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

foreach 元素的功能是非常强大的，它允许指定一个集合，声明可以用在元素体内的集合项和索引变量。它也允许指定开闭匹配的字符串以及在迭代中间放置分隔符。这个元素是很智能的，因此它不会偶然地附加多余的分隔符。











TODO 后面有时间可以研究下 mybatis-generator-maven-plugin ，可以根据数据库自动生成 mapper 接口和配置文件。



# 参考资料：

1. [W3Cschool-MyBatis 教程](https://www.w3cschool.cn/mybatis/7zy61ilv.html)
2. [MyBatis 官方文档](https://mybatis.org/mybatis-3/zh/index.html)
3. [mybatis存储过程及mode=IN,mode=OUT的使用](https://blog.csdn.net/king101125s/article/details/104167858?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3)