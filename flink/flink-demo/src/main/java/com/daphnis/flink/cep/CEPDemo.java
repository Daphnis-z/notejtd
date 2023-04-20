package com.daphnis.flink.cep;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @author daphnis
 * @title 检测一个用户在 3秒内连续登录失败
 * @date 2023-01-08 15:40
 */
public class CEPDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        // 1. 读取事件数据，创建简单事件流
        // 5402,83.149.11.115,success,1558430815
        DataStream<LoginEvent> loginEventStream = env.readTextFile("data/login-log.csv"
        ).map((MapFunction<String, LoginEvent>) line -> {
            String[] arr = line.split(",");
            return new LoginEvent(Long.parseLong(arr[0].trim()), arr[1].trim(),
                    arr[2].trim(), Long.parseLong(arr[3].trim()));
        }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<LoginEvent>(Time.seconds(5)) {
            @Override
            public long extractTimestamp(LoginEvent loginEvent) {
                return loginEvent.getEventTime();
            }
        }).keyBy(LoginEvent::getUserId);

        loginEventStream.print();

        // 2. 定义匹配模式
        Pattern<LoginEvent, LoginEvent> loginFailPtn = Pattern.<LoginEvent>begin("begin").where(new IterativeCondition<LoginEvent>() {
            @Override
            public boolean filter(LoginEvent loginEvent, Context<LoginEvent> context) {
                return "fail".equals(loginEvent.getEventType());
            }
        }).next("next").where(new IterativeCondition<LoginEvent>() {
            @Override
            public boolean filter(LoginEvent loginEvent, Context<LoginEvent> context) {
                return "fail".equals(loginEvent.getEventType());
            }
        }).within(Time.seconds(3));

        // 3. 在事件流上应用模式，得到一个pattern stream
        PatternStream<LoginEvent> patternStream = CEP.pattern(loginEventStream, loginFailPtn);

        // 4. 从pattern stream上应用select function，检出匹配事件序列
        DataStream<Warning> loginFailStream = patternStream.select((PatternSelectFunction<LoginEvent, Warning>) map -> {
            LoginEvent firstFail = map.get("begin").iterator().next();
            LoginEvent lastFail = map.get("next").iterator().next();
            return new Warning(firstFail.getUserId(), firstFail.getEventTime(), lastFail.getEventTime(), "login fail!");
        });

        // 5. 打印结果
        loginFailStream.print();

        env.execute("CEPDemo");
    }
}
