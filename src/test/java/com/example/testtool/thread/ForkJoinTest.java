/**
 * Author:   Herewe
 * Date:     2022/6/8 23:24
 * Description: 使用fork/join框架计算1+2+3+4的结果
 */
package com.example.testtool.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Integer> {
    // 拆分阈值
    public static final int THRESHOLD = 2;

    public ForkJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    private int start;
    private int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean forkFlag = end - start > THRESHOLD;
        if (forkFlag) {
            int middle = (end + start) / 2;
            ForkJoinTest task1 = new ForkJoinTest(start, middle);
            ForkJoinTest task2 = new ForkJoinTest(middle + 1, end);
            // fork
            task1.fork();
            task2.fork();
            // 等待子任务执行完成
            Integer sum1 = task1.join();
            Integer sum2 = task2.join();
            // 合并
            sum = sum1 + sum2;
        } else {
            for (int i = start; i <= end; i++) {
                sum += start;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // 线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 任务
        ForkJoinTest task = new ForkJoinTest(1, 4);
        // 线程池提交任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
