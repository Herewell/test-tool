package com.example.jvm;

import org.omg.CORBA.ObjectHolder;

/**
 * @description:
 */
public class JHSDBTestCase {

	static class Test {
		static ObjectHolder staticObj = new ObjectHolder();
		ObjectHolder instanceObj = new ObjectHolder();

		void foo() {
			ObjectHolder localObj = new ObjectHolder();
			System.out.println("done");

		}
	}


	public static void main(String[] args) {
		Test test = new JHSDBTestCase.Test();
		test.foo();
	}
}
