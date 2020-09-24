package com.me.guanpj.kotlin.ppjoke

import com.me.guanpj.kotlinp.ppjoke.ExampleUnitTest
import java.lang.reflect.Field

fun main() {

    /*addParam("a", "aaa")
    addParam("b", 333)
    addParam("c", true)
    addParam("d", ExampleUnitTest())

    println(params)*/

    // Lambdas 表达式是花括号括起来的代码块。
    /*items.fold(0, {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    })


    var a1 = a("aaa", b("bbb"))

    var a2 = a("aaa", b("bbab"))

    println(a1 == a2)
    println(a1.equals(a2))*/

    abc {
        println("hhh")
        return@abc
    }
}

inline fun abc(say: () -> Unit) {
    println("aaa")
    say()
    println("bbb")
}

fun ddd(say: () -> Unit) {
    say()
}

data class a(var aa: String, var b: b)

data class b(var ba: String)

fun <T, R> Collection<T>.fold(initial: R, combine: (acc: R, nextElement: T) -> R): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

val items = listOf(1, 2, 3, 4, 5)

// lambda 表达式的参数类型是可选的，如果能够推断出来的话：
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// 函数引用也可以用于高阶函数调用：
val product = items.fold(1, Int::times)

val params = mutableMapOf<String, Any?>()

fun addParam(key: String, value: Any) {
    try {
        if (value is String) {
            params.put(key, value)
        } else {
            val field: Field = value.javaClass.getField("TYPE")
            val claz = field.get(null) as Class<*>
            if (claz.isPrimitive) {
                params.put(key, value)
            }
        }
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}
