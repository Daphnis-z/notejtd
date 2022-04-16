package com.daphnis.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class KafkaSource {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        //指定kafka的Broker地址
        properties.setProperty("bootstrap.servers", "192.168.211.101:9092");
        //指定组ID
        properties.setProperty("group.id", "flink-demo");
        //如果没有记录偏移量，第一次从最开始消费
        properties.setProperty("auto.offset.reset", "earliest");
        //kafka的消费者不自动提交偏移量
        //properties.setProperty("enable.auto.commit","false");

        //kafkaSource
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<String>("topic001", new SimpleStringSchema(), properties);

        DataStreamSource<String> lines = env.addSource(kafkaSource);

        //sink
        lines.map((MapFunction<String, Object>) str -> {
            DateTimeFormatter stdFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String stdDateTime=stdFormatter.format(LocalDateTime.now());
            return stdDateTime+",message: "+str;
        }).print();

        env.execute("KafkaSource");
    }

}
