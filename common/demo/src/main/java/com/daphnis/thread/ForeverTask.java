package com.daphnis.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author daphnis
 * @title 多线程永动任务
 * @date 2022-11-13 10:36
 */
public class ForeverTask {

    private List<SubTask> subTasks;

    public void init() {
        subTasks = Lists.newArrayList(new SubTask("subTask1"),
                new SubTask("subTask2"));
        for (final SubTask subTask : subTasks) {
            new Thread(() -> subTask.doExecute()).start();
        }
    }

    public void shutdown() {
        if (Objects.nonNull(subTasks) && !subTasks.isEmpty()) {
            for (SubTask subTask : subTasks) {
                subTask.terminal();
            }
        }
    }

    public static void main(String... args) throws Exception {
        ForeverTask task = new ForeverTask();
        task.init();

        Thread.sleep(new Random().nextInt(5000) + 2000);
        task.shutdown();
    }
}
