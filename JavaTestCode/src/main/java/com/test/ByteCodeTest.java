package com.test;

public class ByteCodeTest implements InterfaceTest {
	private static final Thread thread = new Thread();
	
	private static String str1 = "123";
	
	private String str2 = new String("123");
	
	private static final int _int = 1;
	
	
	public synchronized String getStr() {
		@Deprecated
		int a = 1112111;
		return str1;
	}
	
	
	public void test() {
		getStr();
		InterfaceTest.interfaceTestA();
		interfaceTestB();
		interfaceTestC();
	}
	
	@Override
	public void interfaceTestC() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {


		InterfaceTest test = new ByteCodeTest();
		InterfaceTest.interfaceTestA();
		test.interfaceTestB();
		test.interfaceTestC();
	}

}
