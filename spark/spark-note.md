**Spark Windows本地调试环境搭建：**

 1. 下载两个文件

    Hadoop，下载最新的hadoop: http://hadoop.apache.org/releases.html

    Hadoop-Common: https://github.com/steveloughran/winutils

2. 配置环境变量

   新建系统环境变量HADOOP_HOME，指向hadoop解压位置，并把%HADOOP_HOME%\bin加到path中。

3. 拷贝文件

   把解压后的winutils相关文件复制到hadoop bin路径下。

**常见问题：**

1. java.lang.StringIndexOutOfBoundsException: begin 0, end 3, length 2

   描述：

   ```
   到这个的原因应该是jdk版本，从jdk11换到jdk8后问题解决
   ```

   解决：

   ```
   把jdk版本换到8
   ```

**参考：**

1. [*sparkJavaApi*逐个详解 - jinggangshan - 博客园](https://www.baidu.com/link?url=FvTPghf0wT472LMGJJmjOZH2lfTbZHFSnFcUS3yeBVXBwh1b2AZMVg8fxygGRiSxdfEhTcu8GWObNePUi6Mada&wd=&eqid=e8ab086c0001e57d000000035c039e5d)

