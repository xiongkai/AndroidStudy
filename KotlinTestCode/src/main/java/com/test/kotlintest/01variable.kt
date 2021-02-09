package com.test.kotlintest

import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors

var nameBack:String = "123"
inline var name:String
    get() = nameBack
    set(value) {nameBack = value}

fun main(args: Array<String>){
    functions()
    jisuanqi(1, "+", 2)
    testVarRef()
    extendProperties()
    testRetrofit()
    testExtendFunctions()
    testInlineFunction()
    testSequence()
}

interface AAAAA{
    fun func1()
}

fun testSAM(a:Int, r:AAAAA){

}

fun testNoNameInnerClass(){
    var xx:Executor = Executors.newCachedThreadPool()
    xx.execute {
        println("execute")
    }
    testSAM(1, object : AAAAA {
        override fun func1() {
            println("234")
        }
    })
}

fun testSequence(){
    println("--------------------------")
    var list = mutableListOf(1, 2, 3)
    list.filter {
        println("it1 = $it")
        it%2==1
    }.forEach {
        println("it2 = $it")
    }
    var seq = sequenceOf(1, 2, 3)
    seq.filter {
        println("it1 = $it")
        it%2==1
    }.forEach {
        println("it2 = $it")
    }
    println("--------------------------")
    var g:Sequence<Int> = generateSequence(1){
        if (it == 3) null else it+1
    }
    for (i in g){
        println(i)
    }
    println("reduce = ${mutableListOf(1,2,3).reduce { acc, i ->  acc + i}}")
}

fun testInlineFunction(){
    println("--------------------------")
    testInline(){
        println("testInline")
    }
    //-------------------------
    testInline(xxx@ fun (){
        println("testInline")
        return@xxx
    })
    //-------------------------
    testInline(::inlinePrint)

    val xxxx = ClassInline()
    xxxx.testInline {
        println("xxxx")
    }
}

var defaultVal = 123
fun testDefualtVal(a: Int = defaultVal){
}

class ClassInline{
    private var aaa = 1

    internal inline fun testInline(func1:()->Unit){
        val now = System.currentTimeMillis()
        func1()
        aaa = 2
        println("time = ${System.currentTimeMillis()-now}")
    }
}

fun inlinePrint(){
    println("testInline")
}

inline fun testInline(func1:()->Unit){
    val now = System.currentTimeMillis()
    func1()
    println("time = ${System.currentTimeMillis()-now}")
}

fun testExtendFunctions(){
    println("----------------------------")
    var str = "Hello"
    str = str + "world"
    println(str)
    str = str - "world"
    println(str)
    str = str * 3
    println(str)
    val count = str / "Hello"
    println(count)
}

operator fun String.plus(second: Any?): String{
    println("operator String.plus")
    return "$this$second"
}
operator fun String.minus(second: Any?): String{
    println("operator String.minus")
    return replace(second.toString(), "")
}
operator fun String.times(second: Int): String{
    println("operator String.times")
    return (1..second).joinToString(""){ this }
}
operator fun String.div(second: Any?): Int{
    val right = second.toString()
    return windowed(right.length){ it == right}.count { it }
}

fun testRetrofit(){
    var callBody = Retrofit.Builder().baseUrl("http://www.baidu.com").build().create(RetrofitApi::class.java).baidu()
    println(callBody.execute().body()?.string())
}

internal fun testFunction(){}

interface TestInterface{
    val name:String
}

open class TestPropsParent{
    @JvmField var name:String = "321"
    protected var age:String = "321"
        set
}

var aaa = 2 in 1..2

var bbbb = {p:Int-> print(p)}

class TestProps:TestPropsParent(){
    //internal var name:String = "123"
}

var TestProps.name:String
    get() = "111"
    set(value){
        this.name=value
    }

fun extendProperties(){
    println("-----------------------------")
    val test:TestProps = TestProps()
    println(test.name)
}

const val _const = 1
var testRef = 1

fun testVarRef(){
    println("-----------------------------")
    val globalRef = ::testRef
    println("str = ${globalRef.get()}")
    class Person(var name:String)
    val nameRef = Person::name
    val person = Person("123")
    println("name=${nameRef.get(person)}")
    nameRef.set(person, "321")
    println("name=${nameRef.get(person)}")
    val nameRef2 = person::name
    println("name=${nameRef2.get()}")
    nameRef2.set("123")
    println("name=${nameRef2.get()}")
}

fun jisuanqi(first: Int, op: String, second: Int){
    println("-----------------------------")
    fun plus(first: Int, second: Int): Int{
        return first + second
    }
    fun minus(first: Int, second: Int): Int{
        return first - second
    }
    fun times(first: Int, second: Int): Int{
        return first * second
    }
    fun div(first: Int, second: Int): Int{
        return first / second
    }
    val ops = mapOf(
            "+" to ::plus,
            "-" to ::minus,
            "*" to ::times,
            "/" to ::div
    )
    val opFun = ops[op]?:return
    val result = opFun(first, second)
    println("result = $result")
}

fun functions(){
    println("-----------------------------")
    fun toStr(a: String){
        println("toStr")
    }
    class Test{
        private val a = 1
        fun testPrint(){
            println("testPrint_$a")
        }
    }
    val test: Test = Test()
    val myPrint = Test::testPrint
    //test.myPrint()   // 编译错误
    myPrint(test)   // 输出：toStr
    val testToStr1:String.()->Unit = ::toStr
    "123".testToStr1()  // 输出：toStr
    testToStr1("123")   // 输出：toStr
    val testToStr2:(String)->Unit = testToStr1
    testToStr2("123")  // 输出：toStr
    //"123".testToStr2() // 编译错误
}
