package com.daphnis.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author daphnis
 * @title DataSet demo
 * @date 2023-02-08 16:56
 */
public class WordCount {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // test data
        String[] words = new String[]{
                "The Apache Flink community maintains a self-paced training course",
                " that contains a set of lessons and hands-on exercises. ",
                "This step-by-step introduction to Flink focuses on learning how to",
                " use the DataStream API to meet the needs of common, real-world use cases."
        };

        DataSet<String> text = env.fromElements(words);
        // groupBy tuple field 0, sum tuple field 1
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
                String[] tokens = value.toLowerCase().split("\\W+");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        out.collect(new Tuple2<>(token, 1));
                    }
                }
            }
        }).groupBy(0).sum(1);

        System.out.println("----- word count result -----");
        counts.print();
    }
}
