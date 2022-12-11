package com.tynkovski.utils.cryptography.factory

import com.tynkovski.utils.cryptography.Crypto

object AESFactory : CryptoFactory() {

    class InitialisationVector : FactoryItem {
        constructor(string: String) : super(string)
        constructor(array: ByteArray) : super(array)
    }

    class Key : FactoryItem {
        constructor(string: String) : super(string)
        constructor(array: ByteArray) : super(array)
    }

    fun generateKey(): Key {
        val key = converter.convertToBase64(Crypto.AES.generateKey().encoded)
        return Key(key)
    }

    fun encrypt(
        data: String,
        key: Key
    ): Pair<String, InitialisationVector> {
        val (encrypted, iv) = encrypt(data.toByteArray(), key.toByteArray())
        return Pair(String(encrypted), InitialisationVector(iv))
    }

    fun decrypt(
        data: String,
        key: Key,
        iv: InitialisationVector
    ): String {
        val decrypted = decrypt(data.toByteArray(), key.toByteArray(), iv.toByteArray())
        return String(decrypted)
    }

    private fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): Pair<ByteArray, ByteArray> {
        val (encrypted, iv) = Crypto.AES.encrypt(data, converter.convertFromBase64(key))
        return Pair(
            converter.convertToBase64(encrypted),
            converter.convertToBase64(iv)
        )
    }

    private fun decrypt(
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