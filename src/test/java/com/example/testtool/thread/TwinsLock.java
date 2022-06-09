/**
 * Author:   Herewe
 * Date:     2022/6/6 23:06
 * Description: 设计一个锁，使得同一时刻最多有两个线程可以同时访问，超过则被阻塞
 */
package com.example.testtool.thread;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {

    private static final class Sync extends AbstractQueuedSynchronizer {

        /**
         * 初始化
         * @param count
         */
        Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("数量必须大于0");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int curCount = getState();
                int newCount = curCount - arg;
                if (newCount < 0 || compareAndSetState(curCount, newCount)) {
                    return newCount;
                }
            }
            // 返回大于0时，才证明获取需要arg个同步状态成功
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int curCount = getState();
                int newCount = curCount + arg;
                if (compareAndSetState(curCount, newCount)) {
                    return true;
                }
            }
        }
        Condition newCondition() {
            return newCondition();
        }
    }

    private Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) > 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
