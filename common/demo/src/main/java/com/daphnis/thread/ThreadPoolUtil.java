package com.daphnis.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author daphnis
 * @title 线程池工具类：线程池的创建、初始化、获取和回收等
 * @date 2022-11-12 11:30
 */
public class ThreadPoolUtil {
    // 每个任务，都有自己单独的线程池
    private static Map<String, ExecutorService> executors =
            new ConcurrentHashMap<>();

    // 初始化一个线程池
    private static ExecutorService init(String poolName, int poolSize) {
        return new ThreadPoolExecutor(poolSize, poolSize, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("Pool-" + poolName)
                        .setDaemon(false)
                        .build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    // 获取线程池
    public static ExecutorService getOrInitExecutors(
            String poolName, int poolSize) {
        ExecutorService executorService = executors.get(poolName);
        if (null == executorService) {
            synchronized (ThreadPoolUtil.class) {
                executorService = executors.get(poolName);
                if (null == executorService) {
                    executorService = init(poolName, poolSize);
                    executors.put(poolName, executorService);
                }
            }
        }
        return executorService;
    }

    // 回收线程资源
    public static void releaseExecutors(String poolName) {
        ExecutorService executorService = executors.remove(poolName);
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}

