package com.test.kotlintest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.startCoroutine

/**
 * Created by Scott on 2021/1/15 0015
 */
fun main() {
    test1()
}

fun test1(){
    println("--------------------")
    suspend {

    }.startCoroutine(
            Continuation(Dispatchers.IO){

            }
    )
    GlobalScope.launch {
        println("test-xxx1")
        var s1 = testS1()
        println("test-xxx2")
        var s2 = testS2()
        println("test-xxx3")
        var s3 = testS2()
        println("s1=$s1;s2=$s2")
    }
}

suspend fun testS1():Int{
    println("testS1")
    return 1
}

suspend fun testS2():Int{
    println("testS2")
    return 2
}

