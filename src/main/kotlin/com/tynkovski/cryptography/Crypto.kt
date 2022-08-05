package com.tynkovski.cryptography

import java.util.Base64
import java.security.*
import java.security.spec.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Crypto {
    object AES {
        const val KEY_ALGORITHM = "AES"

        private val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        private val keyGen: KeyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)

        fun generateKey(): Key {
            return keyGen.generateKey()
        }

        fun encrypt(data: ByteArray, key: Key): Pair<ByteArray, ByteArray> {
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return Pair(cipher.doFinal(data), cipher.iv)
        }

        fun decrypt(data: ByteArray, key: Key, iv: ByteArray): ByteArray {
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            return cipher.doFinal(data)
        }
    }

    object RSA {
        const val KEY_ALGORITHM = "RSA"
        private const val SIGNATURE_ALGORITHM = "MD5withRSA"

        private val cipher: Cipher = Cipher.getInstance(KEY_ALGORITHM)
        private val signature: Signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        private val keyGen: KeyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM)

        fun generateKeyPair(): KeyPair {
            keyGen.initialize(1024)
            return keyGen.genKeyPair()
        }

        fun encrypt(data: ByteArray, key: PublicKey): ByteArray {
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return cipher.doFinal(data)
        }

        fun decrypt(data: ByteArray, key: PrivateKey): ByteArray {
            cipher.init(Cipher.DECRYPT_MODE, key)
            return cipher.doFinal(data)
        }

        fun sign(data: ByteArray, privateKey: ByteArray): ByteArray {
            signature.initSign(Builder.buildPrivateKey(privateKey))
            signature.update(data)
            return Converter.toBase64(signature.sign())
        }

        fun verify(data: ByteArray, publicKey: ByteArray, sign: ByteArray): Boolean {
            signature.initVerify(Builder.buildPublicKey(publicKey))
            signature.update(data)
            return signature.verify(Converter.fromBase64(sign))
        }

        fun encryptToString(string: String, key: String): String {
            val publicKey = Builder.buildPublicKey(key)
            return encryptToString(string, publicKey)
        }

        fun decryptFromString(string: String, key: String): String {
            val privateKey = Builder.buildPrivateKey(key)
            return decryptFromString(string, privateKey)
        }

        fun encryptToString(string: String, key: PublicKey): String {
            val encrypted = encrypt(string.toByteArray(), key)
            return String(Converter.toBase64(encrypted))
        }

        fun decryptFromString(string: String, key: PrivateKey): String {
            val decrypted = decrypt(Converter.fromBase64(string.toByteArray()), key)
            return String(decrypted)
        }
    }

    object Converter {
        /** Превращение любых данных в base64 массив */
        fun toBase64(data: ByteArray): ByteArray {
            return Base64.getEncoder().encode(data)
        }

        /** Превращение base64 массива обратно в данные */
        fun fromBase64(base64: ByteArray): ByteArray {
            return Base64.getDecoder().decode(base64)
        }

        /** Превращение base64 массива в base64 строку */
        fun toBase64String(base64: ByteArray): String {
            return String(toBase64(base64))
        }

        /** Превращение base64 строки в base64 массив */
        fun fromBase64String(string: String): ByteArray {
            return fromBase64(string.toByteArray())
        }
    }

    object Builder {
        fun buildKey(string: String): SecretKeySpec {
            return buildKey(Converter.fromBase64String(string))
        }

        fun buildKey(byteArray: ByteArray): SecretKeySpec {
            return SecretKeySpec(byteArray, AES.KEY_ALGORITHM)
        }

        fun buildPrivateKey(string: String): PrivateKey {
            return buildPrivateKey(Converter.fromBase64String(string))
        }

        fun buildPublicKey(string: String): PublicKey {
            return buildPublicKey(Converter.fromBase64String(string))
        }

        fun buildPrivateKey(byteArray: ByteArray): PrivateKey {
            val keySpec = PKCS8EncodedKeySpec(byteArray)
            val keyFactory = KeyFactory.getInstance(RSA.KEY_ALGORITHM)
            return keyFactory.generatePrivate(keySpec)
        }

        fun buildPublicKey(byteArray: ByteArray): PublicKey {
            val keySpec = X509EncodedKeySpec(byteArray)
            val keyFactory = KeyFactory.getInstance(RSA.KEY_ALGORITHM)
            return keyFactory.generatePublic(keySpec)
        }
    }
}