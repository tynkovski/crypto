import com.tynkovski.utils.cryptography.factory.AESFactory

fun main() {
    val data = "Hello World!"
    val key = AESFactory.generateKey()
    val (encrypted, iv) = AESFactory.encrypt(data, key)
    println(encrypted)
    println(iv)

    val decrypted = AESFactory.decrypt(encrypted, key, iv)
    println(decrypted)
}