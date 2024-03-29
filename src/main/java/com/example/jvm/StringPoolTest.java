package com.example.jvm;

/**
 * @description:
 */
public class StringPoolTest {
	public static void main(String[] args) {
		String b1 = new StringBuilder("a").append("bc").toString();// 不会放入字符串常量池
		String q1 = "abc"; // 会放到常量池并返回
		System.out.println(b1 == q1); // false
		System.out.println(b1.intern() == q1); // true

		String str1 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str1.intern() == str1); // 1.7之前是false，之后是true
		String str2 = new String("哈哈哈"); // 并生成两个对象，一个是字符串常量池中，一个是堆中
		System.out.println(str2.intern() == str2); // false
		String str3 = "这个是一个测试";
		System.out.println(str3.intern() == str3); // true。

		// 字符串的几种形式
		// 第一种等价：
		String s1 = "test";
		String s2 = "te" + "st";
		final String finalS = "te";
		String s3 = finalS + "st"; // 编译期标量替换，等价于"te" + "st"
		// 第二种等价,都是在堆中的新对象
		String s4 = new String("te") + "st";
		String s5 = new StringBuilder("te").append("st").toString();
		String noFinalS = "te";
		String s6 = noFinalS + "st"; // 会调用StringBuilder.append()在堆上创建新对象
		System.out.println(s1 == s2); // true
		System.out.println(s1 == s3); // true
		System.out.println(s1 == s4); // true
		System.out.println(s1 == s5); // false
		System.out.println(s1 == s6); // false
		System.out.println(s4 == s5); // false
		System.out.println(s4 == s6); // false
		System.out.println(s6 == s5); // false

	}
}
