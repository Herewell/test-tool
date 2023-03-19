package com.example.jvm.oom;

/**
 * @description:
 */
public class StackOOM {
	private void notStop() {
		while (true) {
		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(this::notStop);
			thread.start();
		}
	}

	public static void main(String[] args) {
		StackOOM oom = new StackOOM();
		oom.stackLeakByThread();
	}
}
