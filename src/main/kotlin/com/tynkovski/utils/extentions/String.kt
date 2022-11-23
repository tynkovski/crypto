package com.tynkovski.utils.extentions

fun String.Companion.random(range: IntRange): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..range.random())
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

operator fun String.times(by: Int): String = buildString {
    repeat(by) {
        append(this@times)
    }
}