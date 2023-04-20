package com.daphnis.flink.kafka;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class KafkaSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> kfkSource = KafkaSource.<String>builder()
                .setBootstrapServers("192.168.211.101:9092")
                .setGroupId("flink-demo")
                .setTopics("topic001")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
        DataStreamSource<String> lines = env.fromSource(kfkSource,
                WatermarkStrategy.noWatermarks(), "kafka source");

        // 按 hostIp统计不同攻击的次数和起始时间
        lines.flatMap(new FlatMapFunction<String, Tuple2<String, AttackEvent>>() {
            @Override
            public void flatMap(String line, Collector<Tuple2<String, AttackEvent>> ip2events) throws Exception {
                AttackEvent event = new Gson().fromJson(line, AttackEvent.class);
                if (!Strings.isNullOrEmpty(event.getHostIp())) {
                    event.setStartTime(event.getEventTime());
                    event.setEndTime(event.getEventTime());
                    event.setCount(1);
                    ip2events.collect(new Tuple2<>(event.getHostIp() + "-" + event.getEventName(), event));
                }
            }
        }).keyBy(0).reduce(new ReduceFunction<Tuple2<String, AttackEvent>>() {
            @Override
            public Tuple2<String, AttackEvent> reduce(Tuple2<String, AttackEvent> tue1,
                                                      Tuple2<String, AttackEvent> tue2) throws Exception {
                tue1.f1.setStartTime(tue1.f1.getStartTime().compareTo(tue2.f1.getStartTime()) < 0 ?
                        tue1.f1.getStartTime() : tue2.f1.getStartTime());
                tue1.f1.setEndTime(tue1.f1.getEndTime().compareTo(tue2.f1.getEndTime()) > 0 ?
                        tue1.f1.getEndTime() : tue2.f1.getEndTime());
                tue1.f1.setCount(tue1.f1.getCount() + tue2.f1.getCount());
                return tue1;
            }
        }).map(new MapFunction<Tuple2<String, AttackEvent>, String>() {
            @Override
            public String map(Tuple2<String, AttackEvent> tue) throws Exception {
                return String.format("[Host: %s,Attack: %s, startTime: %s, endTime: %s, times: %d]",
                        tue.f1.getHostIp(), tue.f1.getEventName(), tue.f1.getStartTime(),
                        tue.f1.getEndTime(), tue.f1.getCount());
            }
        }).print();

        // sink到文件
//        StreamingFileSink<String> streamingFileSink = StreamingFileSink.<String>forRowFormat(
//                new Path("output/"), new SimpleStringEncoder<>("utf-8"))
//                // 设置滚动策略
//                .withRollingPolicy(
//                        DefaultRollingPolicy.builder()
//                                .withRolloverInterval(TimeUnit.MINUTES.toMillis(5))   // 5分钟滚动一次
//                                .withInactivityInterval(TimeUnit.MINUTES.toMillis(1)) // 空闲时间超过1分钟滚动一次
//                                .withMaxPartSize(1024 * 1024 * 1024)    // 设置文件大小1G，超过1G滚动一次（默认128M）
//                                .build()
//                )
//                .build();
//        lines.map((MapFunction<String, String>) str -> {
//            DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String stdDateTime = stdFormatter.format(LocalDateTime.now());
//            return stdDateTime + ",message: " + str;
//        }).addSink(streamingFileSink);

        // sink到命令行
//        lines.map((MapFunction<String, Object>) str -> {
//            DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String stdDateTime = stdFormatter.format(LocalDateTime.now());
//            return stdDateTime + ",message: " + str;
//        }).print();

        env.execute("KafkaSource");
    }

}
