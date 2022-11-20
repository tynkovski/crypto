package com.tynkovski.cryptography

import java.util.Base64

class Base64String private constructor(
    private val data: ByteArray
) {
    companion object {
        private val encoder by lazy { Base64.getEncoder() }
        private val decoder by lazy { Base64.getDecoder() }
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
            return data(data.toByteArray())
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

    override fun toString() = String(data)
    fun toByteArray() = data
}