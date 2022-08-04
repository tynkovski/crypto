import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object Crypto {
    private const val KEY_ALGORITHM = "RSA"
    private val cipher = Cipher.getInstance(KEY_ALGORITHM)
    private val keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM).apply {
        initialize(2048)
    }

    fun generateKeyPair(): KeyPair {
        return keyGen.genKeyPair()
    }

    fun encrypt(text: ByteArray, key: PublicKey): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(text)
    }

    fun decrypt(text: ByteArray, key: PrivateKey): ByteArray {
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(text)
    }

    object Converter {
        fun toBase64(byteArray: ByteArray): ByteArray {
            return Base64.getEncoder().encode(byteArray)
        }

        fun fromBase64(base64: ByteArray): ByteArray {
            return Base64.getDecoder().decode(base64)
        }
    }

    object Builder {
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