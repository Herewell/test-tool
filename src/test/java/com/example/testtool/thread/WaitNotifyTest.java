/**
 * Author:   Herewe
 * Date:     2022/6/4 11:58
 * Description: 等待通知机制test
 * 消费者：1、获取对象锁，2、当条件不满足时循环调用并wait(目的是释放锁)，3、满足时完成任务
 * 生产者：1、获取对象锁，2、改变条件 3、通知一个等待此对象锁的线程或者通知全部线程
 * 注：这里的条件其实就是一个标识位
 * 经典范式：
 *     synchronized(对象) {
 *         while (条件不满足) {
 *             对象.wait();
 *         }
 *         对应的处理逻辑
 *     }
 *
 *     synchronized(对象) {
 *         改变条件
 *         对象.notifyAll();
 *     }
 */
package com.example.testtool.thread;

import lombok.SneakyThrows;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class WaitNotifyTest {
    // 标识位
    private static boolean flag = true;
    // 锁
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new Wait(), "wait线程");
        wait.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notify = new Thread(new Notify(), "notify线程");
        notify.start();
    }



    public static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(LocalTime.now() + " 仍在wait，等待通知中。。。");
                    try {
                        lock.wait();
                        System.out.println(LocalTime.now() + " wait已经释放锁。。。");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("当前线程被打断");
                    }
                }
                System.out.println(LocalTime.now() + " 收到notify，正在执行业务。。。");
            }
        }
    }

    public static class Notify implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(LocalTime.now() + " 即将向wait线程发送notify");
                flag = false;
                lock.notifyAll();
                Thread.sleep(5000);
                System.out.println(LocalTime.now() + " 结束向wait线程发送notify");
            }
        }
    }

    public synchronized Object get(long mills) throws InterruptedException {
        // 这个result就是线程的返回值
        Object result = null;
        // 超时时间
        long future = System.currentTimeMillis() + mills;
        // 等待持续时间
        long remaining = mills;
        // 当超时大于0 并且result 返回值不满足要求
        while ((result == null) && remaining > 0) {
            wait(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }


}
