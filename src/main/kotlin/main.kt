import com.tynkovski.cryptography.AESFactory
import com.tynkovski.cryptography.Base64Converter
import com.tynkovski.cryptography.Crypto
import com.tynkovski.cryptography.RSAFactory
import java.security.Key
import java.security.KeyPair

class Profile(
    rsaKeys: KeyPair = Crypto.RSA.generateKeyPair(),
    aeskey: Key = Crypto.AES.generateKey()
) {
    companion object {
        private val converter = Base64Converter()
    }

    val privateKey: ByteArray
    val publicKey: ByteArray
    val key: ByteArray

    init {
        privateKey = converter.convertToBase64(rsaKeys.private.encoded)
        publicKey = converter.convertToBase64(rsaKeys.public.encoded)
        key = converter.convertToBase64(aeskey.encoded)
    }
}

fun main() {
    val user = Profile()
    println("    key: ${String(user.key)}")
    println("private: ${String(user.privateKey)}")
    println(" public: ${String(user.publicKey)}")

    val message = "Hello".toByteArray()
    val a = RSAFactory.encrypt(message, user.publicKey)
    val b = RSAFactory.decrypt(a, user.privateKey)

    println(String(a))
    println(String(b))

    val message2 = "Should be encrypted".toByteArray()
    val a2 = AESFactory.encrypt(message2, user.key)
    val b2 = AESFactory.decrypt(a2.first, user.key, a2.second)

    println(String(a2.first))
    println(String(b2))
}