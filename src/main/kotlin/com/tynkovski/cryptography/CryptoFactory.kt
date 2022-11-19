package com.tynkovski.cryptography

abstract class CryptoFactory {
    protected val converter = Base64Converter()
}

object AESFactory : CryptoFactory() {
    fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): Pair<ByteArray, ByteArray> {
        val (encrypted, iv) = Crypto.AES.encrypt(data, key)
        return Pair(converter.convertToBase64(encrypted), iv)
    }

    fun decrypt(
        data: ByteArray,
        key: ByteArray,
        iv: ByteArray
    ): ByteArray {
        return Crypto.AES.decrypt(converter.convertFromBase64(data), key, iv)
    }
}

object RSAFactory : CryptoFactory() {
    fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val encrypted = Crypto.RSA.encrypt(data, key)
        return converter.convertToBase64(encrypted)
    }

    fun decrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val raw = converter.convertFromBase64(data)
        return Crypto.RSA.decrypt(raw, key)
    }
}