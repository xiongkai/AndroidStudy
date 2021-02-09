package com.test;

public class xxxxxxxx {

	public static void main(String[] args) {
		int COUNT_BITS = Integer.SIZE - 3;
		System.out.println("count_bits = " + COUNT_BITS);
		System.out.println("-1<<COUNT_BITS = " + (-1<<COUNT_BITS));
		System.out.println("0<<COUNT_BITS = " + (0<<COUNT_BITS));
		System.out.println("1<<COUNT_BITS = " + (1<<COUNT_BITS));
		
		System.out.println(StringTest1.print() == StringTest2.print());

		ByteCodeTest test = new ByteCodeTest();
		test.interfaceTestB();
	}
}
