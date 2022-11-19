import com.tynkovski.cryptography.AESFactory
import com.tynkovski.cryptography.Crypto
import com.tynkovski.cryptography.RSAFactory
import java.security.Key
import java.security.KeyPair

data class Profile(
    private val rsaKeys: KeyPair = Crypto.RSA.generateKeyPair(),
    private val aesKey: Key = Crypto.AES.generateKey()
) {
    val privateKey: ByteArray get() = rsaKeys.private.encoded
    val publicKey: ByteArray get() = rsaKeys.public.encoded
    val key: ByteArray get() = aesKey.encoded
}

fun main() {
    val user = Profile()

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