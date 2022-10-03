package com.daphnis.sbtdemo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * 使用 Async注解可以实现任务的异步执行
 */
@Component
public class AsyncTask {
    public static Random random=new Random();

    @Async
    public Future<String> taskOne() throws InterruptedException {
        System.out.println("start task one ..");
        long start=System.currentTimeMillis();

        Thread.sleep(3000+random.nextInt(500));

        System.out.println(String.format("task one complete,cost %s ms",System.currentTimeMillis()-start));
        return new AsyncResult<>("task one complete");
    }

    @Async
    public Future<String> taskTwo() throws InterruptedException {
        System.out.println("start task two ..");
        long start=System.currentTimeMillis();

        Thread.sleep(2000+random.nextInt(500));

        System.out.println(String.format("task two complete,cost %s ms",System.currentTimeMillis()-start));
        return new AsyncResult<>("task two complete");
    }

    @Async
    public Future<String> taskThree() throws InterruptedException {
        System.out.println("start task three ..");
        long start=System.currentTimeMillis();

        Thread.sleep(1000+random.nextInt(500));

        System.out.println(String.format("task three complete,cost %s ms",System.currentTimeMillis()-start));
        return new AsyncResult<>("task three complete");
    }

}
