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
        private object Key {
            fun getInstance(byteArray: ByteArray): java.security.Key {
                return SecretKeySpec(byteArray, AES.KEY_ALGORITHM)
            }
        }

        const val KEY_ALGORITHM = "AES"
        private const val KEY_TRANSFORMATION = "AES/CBC/PKCS5PADDING"

        fun generateKey(): java.security.Key {
            val keyGen: KeyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
            keyGen.init(256)

            return keyGen.generateKey()
        }

        fun encrypt(
            data: ByteArray,
            key: ByteArray,
            iv: Boolean = true
        ): Pair<ByteArray, ByteArray> {
            val cipher: Cipher = Cipher.getInstance(if (iv) KEY_TRANSFORMATION else KEY_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, Key.getInstance(key))

            return Pair(cipher.doFinal(data), cipher.iv)
        }

        fun decrypt(
            data: ByteArray,
            key: ByteArray,
            iv: ByteArray = ByteArray(16)
        ): ByteArray {
            val cipher: Cipher = Cipher.getInstance(KEY_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, Key.getInstance(key), IvParameterSpec(iv))

            return cipher.doFinal(data)
        }
    }

    object RSA {
        private object Key {
            fun getPrivateInstance(privateKey: ByteArray): PrivateKey {
                val keySpec = PKCS8EncodedKeySpec(privateKey)
                val keyFactory = KeyFactory.getInstance(RSA.KEY_ALGORITHM)

                return keyFactory.generatePrivate(keySpec)
            }

            fun getPublicInstance(publicKey: ByteArray): PublicKey {
                val keySpec = X509EncodedKeySpec(publicKey)
                val keyFactory = KeyFactory.getInstance(RSA.KEY_ALGORITHM)

                return keyFactory.generatePublic(keySpec)
            }
        }

        const val KEY_ALGORITHM = "RSA"
        private const val SIGNATURE_ALGORITHM = "MD5withRSA"

        fun generateKeyPair(): KeyPair {
            val keyGen: KeyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM)
            keyGen.initialize(1024)

            return keyGen.genKeyPair()
        }

        fun encrypt(
            data: ByteArray,
            publicKey: ByteArray
        ): ByteArray {
            val cipher: Cipher = Cipher.getInstance(KEY_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, Key.getPublicInstance(publicKey))

            return cipher.doFinal(data)
        }

        fun decrypt(
            data: ByteArray,
            privateKey: ByteArray
        ): ByteArray {
            val cipher: Cipher = Cipher.getInstance(KEY_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, Key.getPrivateInstance(privateKey))

            return cipher.doFinal(data)
        }

        fun sign(
            data: ByteArray,
            privateKey: ByteArray
        ): ByteArray {
            val signature: Signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initSign(Key.getPrivateInstance(privateKey))
            signature.update(data)

            return signature.sign()
        }

        fun verify(
            data: ByteArray,
            publicKey: ByteArray,
            sign: ByteArray
        ): Boolean {
            val signature: Signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initVerify(Key.getPublicInstance(publicKey))
            signature.update(data)

            return signature.verify(sign)
        }
    }
}

class Base64String {
    companion object {
        private val encoder = Base64.getEncoder()
        private val decoder = Base64.getDecoder()
    }

    class Builder {
        private var encode: Boolean = true
        private var encoded: Boolean = false
        private lateinit var data: ByteArray

        fun encoded(encoded: Boolean): Builder {
            this.encoded = encoded
            return this
        }

        fun data(data: Base64String): Builder {
            return data(data.toString())
        }

        fun data(data: String): Builder {
            return data(data.toByteArray())
        }

        fun data(data: ByteArray): Builder {
            this.data = data
            return this
        }

        fun encode(): Builder {
            this.encode = true
            return this
        }

        fun decode(): Builder {
            this.encode = false
            return this
        }

        fun build(): Base64String {
            return if (encoded) {
                if (encode) Base64String(data) else Base64String(decoder.decode(data))
            } else {
                if (encode) Base64String(encoder.encode(data)) else Base64String(data)
            }
        }
    }

    private val data: ByteArray

    private constructor(array: ByteArray) {
        this.data = array
    }

    override fun toString() = String(data)
    fun toByteArray() = data
}