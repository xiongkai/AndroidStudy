package com.test;

import java.lang.reflect.Field;

public class 泛型擦除 {
	static class TypeErasureSample<T> {
		public T v1;
		public T v2;
		public String v3;
	}
	
	public static void main(String[] args) throws Exception {
		TypeErasureSample<String> type = new TypeErasureSample<String>();
		type.v1 = "String value";
		// 反射设置v2的值为整型数
		Field v2 = TypeErasureSample.class.getDeclaredField("v2");
		v2.set(type, 1);
		for (Field f : TypeErasureSample.class.getDeclaredFields()) {
			System.out.println(f.getName() + ":" + f.getType());
			/*
			 v1:class java.lang.Object
			 v2:class java.lang.Object
			 v3:class java.lang.String
			 */
		}
		System.out.println(type.v2);
		//java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
	}
}
