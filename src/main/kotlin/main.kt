import com.tynkovski.cryptography.Base64String
import com.tynkovski.cryptography.Crypto
import java.security.KeyPair

fun randomString(maxSize: Int): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val size = (1..maxSize).random()
    return (1..size)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

data class Profile(
    val keys: KeyPair = Crypto.RSA.generateKeyPair()
) {
    val privateKey: ByteArray get() = keys.private.encoded
    val publicKey: ByteArray get() = keys.public.encoded
}

fun main() {
    val user = Profile()

    val message = "Hello"

    val encoded = Crypto.RSA.encrypt(message.toByteArray(), user.publicKey)

    val encodedBase = Base64String.Builder()
        .data(encoded)
        .build()
    println(encodedBase)

    val messageBase = Base64String.Builder()
        .encoded(true)
        .data(encodedBase)
        .decode()
        .build()
        .toByteArray()

    val decoded = Crypto.RSA.decrypt(messageBase, user.privateKey)
    println(String(decoded))
}