# Spring注解

- @SpringBootApplication

  修饰 主程序入口类，用于 将类标记为入口类

- @EnableConfigurationProperties({ApplicationConfig.class})

  修饰 主程序入口类，用于 开启对@ConfigurationProperties的支持

- @ConfigurationProperties(prefix = "sbtdemo")

  修饰 配置文件实体类，用于 读取配置文件，与 @EnableConfigurationProperties搭配使用

- @EnableAsync

  修饰 主程序入口类，用于 开启异步执行

- @Async

  修饰 某个具体的方法，用于 将方法标记为异步执行，与 @EnableAsync搭配使用

- @Component

  修饰 某个具体的类，用于 将类注册为 Spring bean

- @Autowired

  修饰 某个具体的对象，用于 从IOC容器中找到匹配的类后自动生成对象，与 @Component搭配使用

- @SpringBootTest

  修饰 测试类，用于启动 Spring容器

