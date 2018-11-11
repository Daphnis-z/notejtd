### 1.简介

ant是一个基于java开发的构建工具，具有跨平台、使用简单、语法清晰、功能强大的特点。

### 2.属性

有一些预定义好的属性，如下

```
ant.java.version  //Ant 使用的 JAVA 语言的软件开发工具包的版本
ant.project.name  //项目的名字，具体声明为 **project** 元素的 **name** 属性
```

也可以自定义属性，如下

``` xml
<?xml version="1.0"?>
<project name="Hello World Project" default="info">

   // 自定义属性sitename，使用${sitename}进行引用
   <property name="sitename" value="www.tutorialspoint.com"/>
   <target name="info">
      <echo>Apache Ant version is ${ant.version} - You are at ${sitename} </echo>
   </target>

</project>
```

### 3.服务列表

- 文件集

  文件集代表了一个文件集合。它被当作一个过滤器，用来包括或移除匹配某种模式的文件。

  例如，参考下面的代码。这里，src 属性指向项目的源文件夹。

  文件集选择源文件夹中所有的 .java 文件，除了那些包含有 'Stub' 单词的文件。能区分大小写的过滤器被应用到文件集上，这意味着名为 Samplestub.java 的文件将不会被排除在文件集之外。

  ```xml
  <fileset dir="${src}" casesensitive="yes">
     <include name="**/*.java"/>
     <exclude name="**/*Stub*"/>
  </fileset>
  ```

- 模式集合

  一个模式集合指的是一种模式，基于这种模式，能够很容易地过滤文件或者文件夹。模式可以使用下述的元字符进行创建。

  ```xml
  <patternset id="java.files.without.stubs">
     <include name="src/**/*.java"/>
     <exclude name="src/**/*Stub*"/>
  </patternset>
  ```

- 文件列表

  文件列表数据类型与文件集相类似，除了以下几处不同：

  - 文件列表包含明确命名的文件的列表，同时其不支持通配符。
  - 文件列表数据类型能够被应用于现有的或者还不存在的文件中。

  ```xml
  <fileset dir="${src}" casesensitive="yes">
     <patternset refid="java.files.without.stubs"/>
  </fileset>
  ```

- 过滤器集合

  使用一个过滤器集合数据类型与拷贝任务，你可以在所有文件中使用一个替换值来替换掉一些与模式相匹配的文本。

  一个常见的例子就是对一个已经发行的说明文件追加版本号，代码如下：

  ```xml
  <copy todir="${output.dir}">
     <fileset dir="${releasenotes.dir}" includes="**/*.txt"/>
     <filterset>
        <filter token="VERSION" value="${current.version}"/>
     </filterset>
  </copy>
  ```

- 路径

  path数据类型通常被用来表示一个类路径。各个路径之间用分号或者冒号隔开。然而，这些字符在运行时被替代为执行系统的路径分隔符。

  类路径被设置为项目中 jar 文件和类文件的列表，如下面例子所示：

  ```xml
  <path id="build.classpath.jar">
     <pathelement path="${env.J2EE_HOME}/${j2ee.jar}"/>
     <fileset dir="lib">
        <include name="**/*.jar"/>
     </fileset>
  </path>
  ```

### 4.执行Java代码

在下面的例子中，给出的 java 类文件需要一个参数（管理员的邮箱地址），执行后将发送一封邮件。

```java
public class NotifyAdministrator
{
   public static void main(String[] args)
   {
      String email = args[0];
      notifyAdministratorviaEmail(email);
      System.out.println("Administrator "+email+" has been notified");
   }
   public static void notifyAdministratorviaEmail(String email
   { 
       //......
   }
}
```

这里给出上面 java 类文件需要的 build.xml 构建文件。

```xml
<?xml version="1.0"?>
<project name="sample" basedir="." default="notify">
   <target name="notify">
      <java fork="true" failonerror="yes" classname="NotifyAdministrator">
         <arg line="admin@test.com"/>
      </java>
   </target>
</project>
```

当 build.xml 被执行后，将会产生下面的输出：

```
C:\>ant
Buildfile: C:\build.xml

notify: [java] Administrator admin@test.com has been notified

BUILD SUCCESSFUL
Total time: 1 second
```