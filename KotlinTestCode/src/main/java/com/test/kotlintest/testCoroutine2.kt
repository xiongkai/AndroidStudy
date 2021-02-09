package com.test.kotlintest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * Created by Scott on 2021/1/18 0018
 */

class MyContinuation() : Continuation<String> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resumeWith(result: Result<String>) {
        log("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }

}

suspend fun demo1() = suspendCoroutine<String> { c ->

    thread(name = "demo1创建的线程") {
        log("demo1 调用resume回调")
        c.resume("hello")
    }

}

suspend fun demo2() = suspendCoroutine<String> { c ->
    thread(name = "demo2创建的线程") {
        log("demo2 调用resume回调")
        c.resume("world")
    }
}

fun main() {


    // 假设下面的lambda需要在UI线程运行
    val suspendLambda = suspend {
        log("demo1 运行前")
        val resultOne = demo1()
        log("demo1 运行后")
        log("demo2 运行前")
        val resultTwo = demo2()
        log("demo2 运行后")
        //拼接结果
        resultOne + resultTwo
    }

    val myContinuation = MyContinuation()

    thread(name = "一个新的线程") {
        suspendLambda.startCoroutine(myContinuation)

    }
    Dispatchers

    GlobalScope.launch {

    }
}

fun log(msg: String) {

    println("[${Thread.currentThread().name}] ${msg}")
}

