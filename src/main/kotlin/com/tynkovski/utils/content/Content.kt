package com.tynkovski.utils.content

import java.io.File

fun saveFile(path: String, file: String, array: ByteArray) {
    saveFile("$path//$file", array)
}

fun loadFile(path: String, fileName: String): ByteArray {
    return loadFile("$path//$fileName")
}

fun saveFile(path: String, array: ByteArray) {
    File(path).writeBytes(array)
}

fun loadFile(path: String): ByteArray {
    return File(path).readBytes()
}

fun getResource(path: String): ByteArray? =
    object {}.javaClass.getResource(path)?.readBytes()