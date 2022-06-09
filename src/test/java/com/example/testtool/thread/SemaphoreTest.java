/**
 * Author:   Herewe
 * Date:     2022/6/9 22:55
 * Description: 信号量测试
 */
package com.example.testtool.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest {
    public static final int THREAD_COUNT = 30;
    public static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                semaphore.tryAcquire();
                count.getAndIncrement();
                semaphore.release();
            });
        }
        threadPool.shutdown();
        System.out.println(count.get());
    }
}
