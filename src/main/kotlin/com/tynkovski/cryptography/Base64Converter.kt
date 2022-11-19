package com.tynkovski.cryptography

class Base64Converter {
    fun convertToBase64(data: ByteArray): ByteArray {
        return Base64String.Builder()
            .encoded(false)
            .data(data)
            .encode()
            .build()
            .toByteArray()
    }

    fun convertFromBase64(data: ByteArray): ByteArray {
        return Base64String.Builder()
            .encoded(true)
            .data(data)
            .decode()
            .build()
            .toByteArray()
    }
}