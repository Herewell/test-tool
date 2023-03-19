package com.example.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * -Xms100m -Xmx100m -XX:+UseSerialGC -XX:+UseConcMarkSweepGC
 * @description:
 */
public class JConsoleTest {
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];
	}

	/**
	 * gc
	 * @param num
	 * @throws InterruptedException
	 */
	public static void fillHeap(int num) throws InterruptedException {
		List<OOMObject> list = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}

	/**
	 * 线程死循环演示
	 */
	public static void createBusyThread() {
		Thread thread = new Thread(() -> {
			while (true) {

			}
		}, "testBusyThread");
		thread.start();
	}

	public static void createLockThread(final Object lock) {

		Thread thread = new Thread(() -> {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "testLockThread");
		thread.start();

	}

	static class SynAddRunnable implements Runnable {
		int a, b;

		public SynAddRunnable(int a, int b) {
			this.a = a;
			this.b = b;

		}
		@Override
		public void run() {
			synchronized (Integer.valueOf(a)) {
				synchronized (Integer.valueOf(b)) {
					System.out.println(a + b);
				}
			}

		}
	}


	public static void main1(String[] args) throws InterruptedException {
		fillHeap(1000);
	}

	public static void main2(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		br.readLine();
		Object o = new Object();
		createLockThread(o);

	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new SynAddRunnable(1, 2)).start();
			new Thread(new SynAddRunnable(2, 1)).start();
		}
	}

}
