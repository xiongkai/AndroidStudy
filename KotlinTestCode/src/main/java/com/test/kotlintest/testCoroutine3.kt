package com.test.kotlintest

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted

/**
 * Created by Scott on 2021/1/19 0019
 */
fun main() {
    //println(sus::class.java)
    //println(sus::class.java.genericSuperclass)
    //var sus1 = sus.createCoroutineUnintercepted<String>(con)
    //println(sus1::class.java)
    /*suspend {
        println("test11--${Thread.currentThread().name}")
        testx1()
        println("test1--${testx2()}")
        println("test12--${Thread.currentThread().name}")
        "123"
    }.startCoroutine(Continuation(Dispatchers.IO){
        println("test2--${Thread.currentThread().name}")
        println("test2:${it.getOrNull()}")
    })*/
    /*
    *//*CoroutineScope(Dispatchers.IO).launch {

        println("tests1--${Thread.currentThread().name}")
    }*//*
    var job = GlobalScope.launch(SupervisorJob()) {
        delay(100)
        println("******")
    }
    job.cancel()
    Thread.sleep(1000)
    //TestClass("first").test()

    println(when{
        1==1->{1}
        else->2
    })
    suspend {
        println(Thread.currentThread().name)
    }.startCoroutine(
            Continuation(Dispatchers.IO){

            }
    )*/

    //var aaa = ::testx1
    //println(aaa::class.java)
    //println(aaa::class.java.genericSuperclass)
    ::testx1.startCoroutine(Continuation(Dispatchers.IO){})
}

fun test11111(){
    GlobalScope.launch {

    }
}

suspend fun testx1(){
    withContext(Dispatchers.Default){
        println("testx1")
    }
    testx11()
}

suspend fun testx11(){
}

suspend fun testx2()= suspendCoroutine<String> {
    thread {
        println("testx2--${Thread.currentThread().name}")
        it.resume("testx2")
    }
}

class TestClass(val name:String){
    fun test(){
        val a = TestClass("second")
        with(a){
            println("$name")
        }
    }
}