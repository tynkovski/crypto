package com.tynkovski.cryptography

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
        ): Pair<ByteArray, ByteArray> {
            val cipher: Cipher = Cipher.getInstance(KEY_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, Key.getInstance(key))

            return Pair(cipher.doFinal(data), cipher.iv ?: ByteArray(16))
        }

        fun decrypt(
            data: ByteArray,
            key: ByteArray,
            iv: ByteArray
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