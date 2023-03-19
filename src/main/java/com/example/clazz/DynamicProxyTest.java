package com.example.clazz;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 */
public class DynamicProxyTest {
	interface IHello {
		void sayHello();
	}

	static class Hello implements IHello {

		@Override
		public void sayHello() {
			System.out.println("Hello");
		}
	}

	static class DynamicProxy implements InvocationHandler {

		Object originalObject;

		Object bind(Object originalObj) {
			this.originalObject = originalObj;
			return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);

		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("welcome");
			return method.invoke(originalObject, args);
		}
	}

	public static void main(String[] args) {
		IHello hello = (IHello) new DynamicProxy().bind(new Hello());
		hello.sayHello();
	}

}
