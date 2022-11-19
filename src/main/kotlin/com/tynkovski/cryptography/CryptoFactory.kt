package com.tynkovski.cryptography

abstract class CryptoFactory {
    protected val converter = Base64Converter()
}

object AESFactory : CryptoFactory() {
    fun encrypt(
        data: String,
        key: String
    ): Pair<String, String> {
        val (encrypted, iv) = encrypt(data.toByteArray(), key.toByteArray())
        return Pair(String(encrypted), String(iv))
    }

    fun decrypt(
        data: String,
        key: String,
        iv: String
    ): String {
        return String(decrypt(data.toByteArray(), key.toByteArray(), iv.toByteArray()))
    }

    fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): Pair<ByteArray, ByteArray> {
        val (encrypted, iv) = Crypto.AES.encrypt(data, converter.convertFromBase64(key))
        return Pair(
            converter.convertToBase64(encrypted),
            converter.convertToBase64(iv)
        )
    }

    fun decrypt(
        data: ByteArray,
        key: ByteArray,
        iv: ByteArray
    ): ByteArray {
        return Crypto.AES.decrypt(
            converter.convertFromBase64(data),
            converter.convertFromBase64(key),
            converter.convertFromBase64(iv)
        )
    }
}

object RSAFactory : CryptoFactory() {
    fun encrypt(
        data: String,
        key: String
    ): String {
        return String(encrypt(data.toByteArray(), key.toByteArray()))
    }

    fun decrypt(
        data: String,
        key: String,
    ): String {
        val decrypted = decrypt(data.toByteArray(), key.toByteArray())
        return String(decrypted)
    }

    fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val encrypted = Crypto.RSA.encrypt(data, converter.convertFromBase64(key))
        return converter.convertToBase64(encrypted)
    }

    fun decrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val raw = converter.convertFromBase64(data)
        return Crypto.RSA.decrypt(raw, converter.convertFromBase64(key))
    }
}