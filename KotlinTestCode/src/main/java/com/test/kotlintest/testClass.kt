package com.test.kotlintest

import java.io.Closeable

class TestGetSet{
    var name:String = "123"
        private set(value) {
            field = value
            println(value)
        }
    var name2:String = "123"
        private set

    sealed class TestSealed(var name: String){
        constructor():this("321"){}

        class A:TestSealed(){

        }
    }
}
var aaaaaa = 1

inline class TestInLineClass(val a:Int){
    var b:Int
        get()= aaaaaa
        set(value){
            aaaaaa = value
        }

    fun testFun(){

    }
}


/**
 * Created by Scott on 2021/1/7 0007
 */
fun main() {
    testOverLoad("123")
    object:Any(){
        @JvmField var age = 123
    }

    data class XXXXXXXXX(val name: String){

    }
    testInLine {
        println("123")
    }


    testInLine1 {
        println("123")
    }


    testInLine2 {
        println("123")
    }
    println(testReifd<String?>(1))
}

fun <T,E> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T>,
              E: Closeable,
              E: Comparable<E>{
    return list.filter { it > threshold }.map { it.toString() }
}

fun <T> test(a:T, b:T):T
        where T:CharSequence,
              T:Comparable<T>{
    TODO()
}

class TestAAAA<T,E>(override val length: Int) :CharSequence where T : CharSequence,
                                                                  T : Comparable<T>,
                                                                  E: Closeable,
                                                                  E: Comparable<E>{
    override fun get(index: Int): Char {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class JsonTest(val name: String, val age: Int)

inline fun <T> testReifd(aa:Any?): T? {
    if (aa !is String){
        return "321" as T?
    }
    return aa as T?
}

inline fun <reified T> testReifd1(aa:Any?): T? {
    if (aa !is String){
        return "321" as T?
    }
    return aa as T?
}

inline fun testInLine( p:()->Unit){
    println("start")
    p()
    println("end")
}

inline fun testInLine1(noinline p:()->Unit){
    println("start")
    p()
    println("end")
}

inline fun testInLine2(crossinline p:()->Unit){
    println("start")
    p()
    println("end")
}

class TestComObj{
    companion object {
        @JvmField var age = 123
        @JvmStatic fun aaa(){
        }
    }
}

object AAAA:Any(){
    @JvmField var aaaa = 1

    @JvmStatic fun eeee(){}
}

object TestObj{
    var aaa:Int = 1
    @JvmField var bbb:Int = 1
    @JvmStatic var ccc:Int = 1
}

interface Base {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived(b: Base) : Base by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message = "Message of Derived"
}

val delLay: String by lazy {
    "123"
}

fun testOverLoad(name:String, age:Int = 123){

    val delLay: String by lazy {
        "123"
    }
}

class Class1(){

    val delLay: String by lazy {
        "123"
    }

    constructor(name: String):this(){
    }
}