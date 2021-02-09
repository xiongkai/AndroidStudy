package com.test.kotlintest

import java.io.File

/**
 * Created by Scott on 2021/1/6 0006
 */

fun main() {
    val htmlContent = html {
        head {
            "meta" { "charset"("UTF-8") }
        }
        body {
            "div" {
                "style"(
                        """
                    width: 200px; 
                    height: 200px; 
                    line-height: 200px; 
                    background-color: #C9394A;
                    text-align: center
                    """.trimIndent()
                )
                "span" {
                    "style"(
                            """
                        color: white;
                        font-family: Microsoft YaHei
                        """.trimIndent()
                    )
                    +"Hello HTML DSL!!"
                }
            }
        }
    }.render()

    File("Kotlin.html").writeText(htmlContent)
}

interface Node{
    fun render():String
}

class StringNode(private val value:String):Node{
    override fun render(): String {
        return value
    }
}

class BlockNode(private val name:String):Node{

    override fun render(): String {
        return "<$name ${
            properties.map {
                "${it.key}='${it.value}'"
            }.joinToString(" ")
        }>${
            children.joinToString("") {
                it.render()
            }
        }</$name>"
    }
    val children = ArrayList<Node>()
    private val properties = HashMap<String, Any>()

    operator fun String.invoke(block:BlockNode.()->Unit){
        val node = BlockNode(this)
        node.block()
        this@BlockNode.children += node
    }

    operator fun String.invoke(value:Any){
        this@BlockNode.properties[this] = value
    }

    operator fun String.unaryPlus(){
        val node = StringNode(this)
        this@BlockNode.children += node
    }
}

fun html(block:BlockNode.()->Unit):BlockNode{
    val node = BlockNode("html")
    node.block()
    return node
}

fun BlockNode.head(block:BlockNode.()->Unit){
    val node = BlockNode("head")
    node.block()
    this.children += node
}

fun BlockNode.body(block:BlockNode.()->Unit){
    val node = BlockNode("body")
    node.block()
    this.children += node
}