package com.tynkovski.utils.archiver

import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object Archiver {
    fun zip(content: ByteArray): ByteArray =
        ByteArrayOutputStream().also { bos ->
            GZIPOutputStream(bos).buffered().use { it.write(content) }
        }.toByteArray()

    fun unzip(content: ByteArray): ByteArray =
        GZIPInputStream(content.inputStream()).buffered().use { it.readBytes() }
}