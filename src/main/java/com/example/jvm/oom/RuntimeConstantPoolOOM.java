package com.example.jvm.oom;

import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		// 使用set保存字符串，避免full gc回收常量池行为。
		Set<String> set = new HashSet<>();
		short i = 0;
		while (true) {
			set.add(String.valueOf(i++).intern());
		}
	}
}
