## 1. 常用大数据服务——以ambari为例

### 1.1 Hdfs



### 1.2 Hadoop



### 1.3 Hbase



### 1.4 Hive



### 1.5 Yarn

​	新一代Hadoop计算平台，将资源管理和处理组件分开，集成在Hadoop2.0中。

### 1.6 Spark2



### 1.7 Kafka



### 1.8 MapReduce2



### 1.9 Tez

​	运行在yarn之上，支持DAG作业的计算框架。核心思想是将Map和Reduce两个操作进一步拆分，Map被拆分成input,processor,sort,merge和output，Reduce被拆分成input,shuffle,sort,merge,processor和output等，这些分解后的元操作可以任意灵活组合，产生新的操作，经过一些控制程序组装后，可形成一个大的DAG作业。

### 1.10 Pig



### 1.11 ZooKeeper



### 1.12 Ambari Metricks



### 1.13 Knox



### 1.14 Slider





## 2. 常见问题

### 2.1 Hbase为什么适合做实时查询



###  2.2 Hive为什么适合做离线计算

