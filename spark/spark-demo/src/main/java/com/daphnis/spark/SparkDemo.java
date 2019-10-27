package com.daphnis.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import java.util.Arrays;
import java.util.List;

import com.daphnis.spark.functions.FlatMapFunc;

public class SparkDemo {

    public static void println(String content) {
        System.out.println("");
        System.err.println(content);
        System.out.println("");
    }

    /**
     * filter 算子：遍历集合，把集合中符合要求的数据取出来作为一个新的集合返回
     * @param sparkContext
     */
    public static void filterDemo(JavaSparkContext sparkContext) {
        println("************************ filter demo ************************");

        JavaRDD<String> originRdd=sparkContext.parallelize(Arrays.asList("it","is","my","life"));
        JavaRDD<String> filterRdd=originRdd.filter(word->word.length()==2);

        List<String> words=filterRdd.collect();
        println("filter demo,left words: "+words);
    }

    /**
     * flatMap 算子：遍历集合，将集合中的每个对象做处理，返回 0 个或多个新的对象
     * @param sparkContext
     */
    public static void flatMapDemo(JavaSparkContext sparkContext) {
        println("************************ flatMap demo ************************");

        JavaRDD<String> originRdd=sparkContext.parallelize(Arrays.asList("flat","map","function","demo"));
        JavaRDD<String> flatMapRdd=originRdd.flatMap(new FlatMapFunc(5));

        List<String> words=flatMapRdd.collect();
        println("flatMap demo,result words: "+words);
    }


    public static void main(String... args) {
        SparkConf conf = new SparkConf().setAppName("SparkDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        filterDemo(sparkContext);
        flatMapDemo(sparkContext);

        sparkContext.close();
    }

}

