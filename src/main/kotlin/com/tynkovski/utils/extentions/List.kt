package com.tynkovski.utils.extentions

operator fun<T> List<T>.times(by: Int): List<T> =
    mutableListOf<T>().apply {
        repeat(by) {
            addAll(this@times)
        }
    }