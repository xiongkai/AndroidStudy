package com.test.kotlintest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Scott on 2021/1/19 0019
 */
fun main() {
    /*var aaa = ::testx51
    aaa.startCoroutine(Continuation(Dispatchers.IO){

    })*/
/*
    GlobalScope.launch {
        var r = suspendCoroutine<String> {
            println("000000")
            it.resume("xxxx")
        }
        println("r=${r}")
        launch {
            println("11111")
        }
        println("222222")
    }*/
    GlobalScope.launch {
        testx51()
        println("333333")
    }
    Thread.sleep(1000)
}

suspend fun testx51():Unit{
    withContext(Dispatchers.Default){
        testx52()
        println("51")
        return@withContext "51-r"
    }
}

suspend fun testx52(){
    withContext(Dispatchers.IO){
        println("52")
        return@withContext "52-r"
    }
}