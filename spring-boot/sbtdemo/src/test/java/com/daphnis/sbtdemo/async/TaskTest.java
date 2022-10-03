package com.daphnis.sbtdemo.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import java.util.concurrent.Future;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskTest {

    @Autowired
    private NormalTask normalTask;

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testNormal() throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();

        normalTask.taskOne();
        normalTask.taskTwo();
        normalTask.taskThree();

        stopWatch.stop();
        System.out.println(String.format("all task complete,cost %s ms",stopWatch.getTotalTimeMillis()));
    }

    @Test
    public void testAsync() throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();

        Future<String> task1=asyncTask.taskOne();
        Future<String> task2=asyncTask.taskTwo();
        Future<String> task3=asyncTask.taskThree();

        while (!task1.isDone()||!task2.isDone()||!task3.isDone()){
            Thread.sleep(100);
        }

        stopWatch.stop();
        System.out.println(String.format("all task complete,cost %s ms",stopWatch.getTotalTimeMillis()));
    }
}
