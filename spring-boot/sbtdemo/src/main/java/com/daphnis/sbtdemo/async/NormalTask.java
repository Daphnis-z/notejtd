package com.daphnis.sbtdemo.async;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NormalTask {
    public static Random random=new Random();

    public void taskOne() throws InterruptedException {
        System.out.println("start task one ..");
        long start=System.currentTimeMillis();

        Thread.sleep(3000+random.nextInt(500));

        System.out.println(String.format("task one complete,cost %s ms",System.currentTimeMillis()-start));
    }

    public void taskTwo() throws InterruptedException {
        System.out.println("start task two ..");
        long start=System.currentTimeMillis();

        Thread.sleep(2000+random.nextInt(500));

        System.out.println(String.format("task two complete,cost %s ms",System.currentTimeMillis()-start));
    }

    public void taskThree() throws InterruptedException {
        System.out.println("start task three ..");
        long start=System.currentTimeMillis();

        Thread.sleep(1000+random.nextInt(500));

        System.out.println(String.format("task three complete,cost %s ms",System.currentTimeMillis()-start));
    }

}
