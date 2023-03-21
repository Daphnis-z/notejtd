package com.daphnis.flink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class KafkaSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> kfkSource = KafkaSource.<String>builder()
                .setBootstrapServers("192.168.211.101:9092")
                .setGroupId("flink-demo")
                .setTopics("topic001")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStreamSource<String> lines = env.fromSource(kfkSource,
                WatermarkStrategy.noWatermarks(), "kafka source");

        // TODO 按 hostIp统计不同攻击的次数和起始时间

        // sink到文件
        StreamingFileSink<String> streamingFileSink = StreamingFileSink.<String>forRowFormat(
                new Path("output/"), new SimpleStringEncoder<>("utf-8"))
                // 设置滚动策略
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                .withRolloverInterval(TimeUnit.MINUTES.toMillis(5))   // 5分钟滚动一次
                                .withInactivityInterval(TimeUnit.MINUTES.toMillis(1)) // 空闲时间超过1分钟滚动一次
                                .withMaxPartSize(1024 * 1024 * 1024)    // 设置文件大小1G，超过1G滚动一次（默认128M）
                                .build()
                )
                .build();
        lines.map((MapFunction<String, String>) str -> {
            DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String stdDateTime = stdFormatter.format(LocalDateTime.now());
            return stdDateTime + ",message: " + str;
        }).addSink(streamingFileSink);

        // sink到命令行
//        lines.map((MapFunction<String, Object>) str -> {
//            DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String stdDateTime = stdFormatter.format(LocalDateTime.now());
//            return stdDateTime + ",message: " + str;
//        }).print();

        env.execute("KafkaSource");
    }

}
