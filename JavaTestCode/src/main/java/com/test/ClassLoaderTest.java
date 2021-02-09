package com.test;

public class ClassLoaderTest {
	
	public static void main(String[] args) throws InterruptedException {
		ClassLoader loader1 = Thread.currentThread().getContextClassLoader();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ClassLoader loader2 = Thread.currentThread().getContextClassLoader();
				System.out.println("loader1 = " + loader1);
				System.out.println("loader2 = " + loader2);
				StringTest1 test = new StringTest1();
				System.out.println("test = " + test.getClass().getClassLoader());
			}
		});
		ClassLoader newLoader = new ClassLoader(loader1) {
			
		};
		thread.setContextClassLoader(newLoader);
		thread.start();
		thread.join();
	}

}
