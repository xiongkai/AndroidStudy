package com.test;

class SingleTon {
    public static int count1;
    public static int count2 = 1999;
    static {

        System.out.println("静态代码块1 " + count2);
    }
    public static final int count3 = new Integer(1);
    public static final Integer count4 = 1;
    public static final String test = "test";
    public static final SingleTon singleTon = new SingleTon();
    static {

        System.out.println("静态代码块2"+singleTon);
    }
    public int a = 1;
    private SingleTon() {
        count1++;
        count2++;
        System.out.println("构造函数"+a);
    }
    {
    	a = 3;
    }
    public static SingleTon getInstance() {
        return singleTon;
    }
}
public class TestSingleTon {
    public static void main(String[] args) {
        /*SingleTon singleTon = SingleTon.getInstance();
        System.out.println("count1=" + singleTon.count1);
        System.out.println("count2=" + singleTon.count2);*/
    	System.out.println(SingleTon.count3);;
    }
}