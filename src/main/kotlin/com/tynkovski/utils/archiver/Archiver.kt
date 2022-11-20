package com.tynkovski.utils.archiver

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object Archiver {
    fun zip(content: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter().use { it.write(content) }
        return bos.toByteArray()
    }

    fun unzip(content: ByteArray): String =
        GZIPInputStream(content.inputStream()).bufferedReader().use { it.readText() }
}