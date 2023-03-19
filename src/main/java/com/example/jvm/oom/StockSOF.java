package com.example.jvm.oom;

/**
 * @description:
 */
public class StockSOF {
	private int stackLength = 1;
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) {
		StockSOF sof = new StockSOF();
		try {
			sof.stackLeak();
		} catch (Throwable throwable) {
			System.out.println(sof.stackLength);
			throw throwable;
		}
	}
}
