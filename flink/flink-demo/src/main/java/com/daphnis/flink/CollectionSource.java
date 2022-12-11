package com.daphnis.flink;

import com.google.common.collect.Lists;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.List;

/**
 * @author daphnis
 * @title 集合数据源
 * @date 2022-12-11 18:13
 */
public class CollectionSource {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<String> animals= Lists.newArrayList("cat","dog","pig","elephant","rabbit");
        DataStreamSource<String> lines = env.fromCollection(animals);

        lines.filter((FilterFunction<String>) animal -> animal.length()>3).print();

        env.execute("CollectionSource");
    }

}
