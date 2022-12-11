package com.tynkovski.utils.cryptography.factory

import com.tynkovski.utils.cryptography.Crypto

object RSAFactory : CryptoFactory() {

    class PublicKey : FactoryItem {
        constructor(string: String) : super(string)
        constructor(array: ByteArray) : super(array)
    }

    class PrivateKey : FactoryItem {
        constructor(string: String) : super(string)
        constructor(array: ByteArray) : super(array)
    }

    fun generateKeyPair(): Pair<PrivateKey, PublicKey> {
        val keyPair = Crypto.RSA.generateKeyPair()
        val privateKey = converter.convertToBase64(keyPair.private.encoded)
        val publicKey = converter.convertToBase64(keyPair.public.encoded)
        return Pair(PrivateKey(privateKey), PublicKey(publicKey))
    }

    fun encrypt(
        data: String,
        key: PublicKey
    ): String {
        val encrypted = encrypt(data.toByteArray(), key.toByteArray())
        return String(encrypted)
    }

    fun decrypt(
        data: String,
        key: PrivateKey,
    ): String {
        val decrypted = decrypt(data.toByteArray(), key.toByteArray())
        return String(decrypted)
    }

    private fun encrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val encrypted = Crypto.RSA.encrypt(data, converter.convertFromBase64(key))
        return converter.convertToBase64(encrypted)
    }

    private fun decrypt(
        data: ByteArray,
        key: ByteArray,
    ): ByteArray {
        val raw = converter.convertFromBase64(data)
        return Crypto.RSA.decrypt(raw, converter.convertFromBase64(key))
    }
}