package com.tynkovski.utils.cryptography.factory

import com.tynkovski.utils.base64.Base64Converter
import com.tynkovski.utils.cryptography.Crypto

abstract class CryptoFactory {

    abstract class FactoryItem {

        constructor(string: String) {
            value = string
        }

        constructor(array: ByteArray) {
            value = String(array)
        }

        private val value: String

        override fun toString() = value

        fun toByteArray(): ByteArray {
            return value.toByteArray()
        }
    }

    protected val converter by lazy { Base64Converter() }
}