/**
 * Author:   Herewe
 * Date:     2022/6/9 23:06
 * Description: ExchangerTest
 */
package com.example.testtool.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    private static final Exchanger<String> exchanger = new Exchanger<>();

    public static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            String A = "流水A";
            try {
                exchanger.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String B = "流水B";
            try {
                String A = exchanger.exchange(B);
                System.out.println(A);
                System.out.println(B);
                System.out.println(A.equals(B) + "  A录入：" + A + "  B录入：" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();
    }

}
