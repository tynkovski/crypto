package com.tynkovski.cryptography

import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object Crypto {
    private const val KEY_ALGORITHM = "RSA"
    private const val SIGNATURE_ALGORITHM = "MD5withRSA"

    private val signature: Signature = Signature.getInstance(SIGNATURE_ALGORITHM)
    private val cipher: Cipher = Cipher.getInstance(KEY_ALGORITHM)
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

    object Converter {
        fun encryptToBase64String(string: String, key: PublicKey): String {
            return String(toBase64(encrypt(string.toByteArray(), key)))
        }

        fun decryptFromBase64String(string: String, key: PrivateKey): String {
            return String(decrypt(fromBase64(string.toByteArray()), key))
        }

        fun toBase64(data: ByteArray): ByteArray {
            return Base64.getEncoder().encode(data)
        }

        fun fromBase64(base64: ByteArray): ByteArray {
            return Base64.getDecoder().decode(base64)
        }
    }

    object Builder {
        fun buildPrivateKey(stringInBase64: String): PrivateKey {
            return buildPrivateKey(Converter.fromBase64(stringInBase64.toByteArray()))
        }

        fun buildPublicKey(stringInBase64: String): PublicKey {
            return buildPublicKey(Converter.fromBase64(stringInBase64.toByteArray()))
        }

        fun buildPrivateKey(byteArray: ByteArray): PrivateKey {
            val keySpec = PKCS8EncodedKeySpec(byteArray)
            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
            return keyFactory.generatePrivate(keySpec)
        }

        fun buildPublicKey(byteArray: ByteArray): PublicKey {
            val keySpec = X509EncodedKeySpec(byteArray)
            val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
            return keyFactory.generatePublic(keySpec)
        }
    }
}