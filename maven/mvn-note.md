### 1.简介

​	一个项目管理和综合工具，提供了开发人员构建一个完整的生命周期框架，简化和标准化项目建设过程。

### 2.提供的管理方式

- Builds——编译
- Documentation——文档
- Reporting——报表
- Dependences——依赖
- SCMS——软件配置管理
- Release——发布
- Distribution——分布
- Mailing List——邮件列表

### 3.目标
- 提供给开发人员；
- 项目是可重复使用，易维护，更容易理解的一个综合模型；
- 插件或交互的工具，这种声明性的模式；

### 4.项目结构和内容
- 在pom.xml文件中声明；
- POM：项目对象模型，管理项目的构建，相关性和文档，最强大的是能够自动下载项目依赖库;

###  5.仓库

- 本地资源库
  存储项目的依赖库，默认文件夹.m2，可修改。

- 中央存储库
  下载所有项目的依赖库的默认位置。

### 6.完整的Maven生命周期
```
validate
  验证项目是否正确，以及所有为了完整构建必要的信息是否可用
generate-sources
  生成所有需要包含在编译过程中的源代码
process-sources
  处理源代码，比如过滤一些值
generate-resources 
  生成所有需要包含在打包过程中的资源文件
process-resources
  复制并处理资源文件至目标目录，准备打包
compile 
  编译项目的源代码
process-classes
  后处理编译生成的文件，例如对Java类进行字节码增强（bytecode enhancement）
generate-test-sources
  生成所有包含在测试编译过程中的测试源码
process-test-sources
  处理测试源码，比如过滤一些值
generate-test-resources
  生成测试需要的资源文件
process-test-resources
  复制并处理测试资源文件至测试目标目录
test-compile
  编译测试源码至测试目标目录
test 
  使用合适的单元测试框架运行测试。这些测试应该不需要代码被打包或发布
prepare-package
  在真正的打包之前，执行一些准备打包必要的操作。通常会产生一个包的展开的处理过的版本。
package
  将编译好的代码打包成可分发的格式，如JAR，WAR，或者EAR
pre-integration-test 
  执行一些在集成测试运行之前需要的动作。如建立集成测试需要的环境
integration-test
  如果有必要的话，处理包并发布至集成测试可以运行的环境
post-integration-test
  执行一些在集成测试运行之后需要的动作。如清理集成测试环境。
verify 
  执行所有检查，验证包是有效的，符合质量规范
install
  安装包至本地仓库，以备本地的其它项目作为依赖使用
deploy
  复制最终的包至远程仓库，共享给其它开发人员和项目（通常和一次正式的发布相关）
```

### 7. 常用命令

```shell
# 清理项目产生的临时文件，一般是模块下的target目录
mvn clean
  
# 模块安装命令，将打包的jar/war文件复制到本地仓库中，供其他模块使用
mvn install

# 项目打包命令
# 生成target目录，编译、测试代码，生成测试报告，生成jar/war文件
mvn package
  
# 测试命令，或执行src/test/java/下的junit的测试用例
mvn test
  
# 发布命令，将打包的文件发布到远程参考，提供其他人员进行下载依赖，一般是发布到公司的私服
mvn deploy

# 手动安装jar包到maven本地仓库
mvn install:install-file -Dfile=c:\kaptcha-{version}.jar -DgroupId=com.google.code -DartifactId=kaptcha -Dversion={version} -Dpackaging=jar
# 这里以安装kaptcha举例
mvn install:install-file -Dfile=c:\kaptcha-2.3.jar -DgroupId=com.google.code 
-DartifactId=kaptcha -Dversion=2.3 -Dpackaging=jar

# 下载最新快照并构建项目
mvn clean package -U
```

### 8.特殊字符串

- SNAPSHOT 
  ​    如果一个版本包含字符串"SNAPSHOT"，Maven就会在安装或发布这个组件的时候将该符号展开为一个日期和时间值，转换为UTC时间。例如，"1.0-SNAPSHOT"会在2010年5月5日下午2点10分发布时候变成1.0-20100505-141000-1。 
  ​    这个词只能用于开发过程中，因为一般来说，项目组都会频繁发布一些版本，最后实际发布的时候，会在这些snapshot版本中寻找一个稳定的，用于正式发布，比如1.4版本发布之前，就会有一系列的1.4-SNAPSHOT，而实际发布的1.4，也是从中拿出来的一个稳定版。
- LATEST 
  ​    指某个特定构件的最新发布，这个发布可能是一个发布版，也可能是一个snapshot版，具体看哪个时间最后。
- RELEASE 
  ​    指最后一个发布版。