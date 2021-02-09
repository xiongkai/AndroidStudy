package com.test.kotlintest

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty2
import kotlin.reflect.full.createType
import kotlin.reflect.typeOf

/**
 * Created by Scott on 2021/1/11 0011
 */
@ExperimentalStdlibApi
fun main() {
    var a:Comparable<Int> = IntComp(1);
    println(
            typeOf<Comparable<Int>>().classifier)
    var aa:Array<Int> = Array<Int>(1){it}
    var bb:Array<*> = aa
    var propRef = ::prop
    var xxx = typeOf<()->Unit>()
    println(xxx.toString())
    //Comparable::class.createType()
    ::demo.returnType.arguments
    println(::demo)
    println(IntComp::aaaa.parameters[2].type)

    var aaaaList:List<Number> = ArrayList<Number>()

    val axx:Int
    axx = 1
}
class AXXX{
    private val axx:Int
    init {
        axx=1
    }
}

fun testCreate(){
    var a = 1
    onCreate {
        a = 2
    }
    a=3
}

fun onCreate(a:()->Unit) {
    a.invoke()
}

inline fun <reified A> test(){
    var a = A::class.constructors.elementAt(0).call()
}

fun <T> xxxx(a:@UnsafeVariance T){
}

var prop = 1

class TestTT<T>{


    fun xxxx(a:T){

    }
}

fun <T> T.aa(){

}

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}
class IntComp(var v:Int):Comparable<Int>{
    override fun compareTo(other: Int): Int {
        return 1
    }

    fun aaaa(a:Int, b:Int){

    }
}
fun demo(x: Comparable<Number>) {
    x.compareTo(1)     // 1.0 拥有类型 Double，它是 Number 的子类型
    // 因此，我们可以将 x 赋给类型为 Comparable<Double> 的变量
    val y: Comparable<Double> = x // OK！
}

class Test<out T>(){
    fun test(a:Comparable<T>):Array<*> {
        return Array<Int>(1){it}
    }
}

annotation class TestAnn

@Suppress val z:Int = 1