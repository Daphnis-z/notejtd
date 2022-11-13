package com.daphnis.thread;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author daphnis
 * @title 线程子任务
 * @date 2022-11-12 11:51
 */
public class SubTask {
    private final int POOL_SIZE = 3; // 线程池大小
    private final int SPLIT_SIZE = 4; // 数据拆分大小
    private String taskName;

    // 接收jvm关闭信号，实现优雅停机
    protected volatile boolean terminal = false;

    public SubTask(String taskName) {
        this.taskName = taskName;
    }

    // 程序执行入口
    public void doExecute() {
        int i = 0;
        while (true) {
            System.out.println(taskName + ":Cycle-" + i + "-Begin");
            // 获取数据
            List<Motorbike> datas = queryData();
            // 处理数据
            taskExecute(datas);
            System.out.println(taskName + ":Cycle-" + i + "-End");
            if (terminal) {
                // 只有应用关闭，才会走到这里，用于实现优雅的下线
                break;
            }
            i++;
        }

        // 回收线程池资源
        ThreadPoolUtil.releaseExecutors(taskName);
    }

    // 优雅停机
    public void terminal() {
        terminal = true;
        System.out.println(taskName + " shutdown");
    }

    // 处理数据
    private void doProcessData(List<Motorbike> datas, CountDownLatch latch) {
        try {
            for (Motorbike mbike : datas) {
                System.out.println(taskName + ":" + mbike.toString()
                        + ",ThreadName:" + Thread.currentThread().getName());
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    // 处理单个任务数据
    private void taskExecute(List<Motorbike> sourceDatas) {
        if (null == sourceDatas || sourceDatas.isEmpty()) {
            return;
        }
        // 将数据拆成4份
        List<List<Motorbike>> splitDatas = Lists.partition(sourceDatas, SPLIT_SIZE);
        final CountDownLatch latch = new CountDownLatch(splitDatas.size());
        // 并发处理拆分的数据，共用一个线程池
        for (final List<Motorbike> datas : splitDatas) {
            ExecutorService executorService =
                    ThreadPoolUtil.getOrInitExecutors(taskName, POOL_SIZE);
            executorService.submit(() -> doProcessData(datas, latch));
        }
        try {
            latch.await();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // 获取永动任务数据
    private List<Motorbike> queryData() {
        List<Motorbike> datas = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            datas.add(new Motorbike("DUCATI", "V" + i));
        }
        return datas;
    }
}
