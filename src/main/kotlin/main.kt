import com.tynkovski.cryptography.Crypto

fun randomString(maxSize: Int): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val size = (1..maxSize).random()
    return (1..size)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun main() {
    val data = "Hello, World!"
    val key = Crypto.RSA.generateKeyPair()

    val public = Crypto.Converter.toString(key.public.encoded)
    println(public)
    val encrypted = Crypto.RSA.encryptToString(data, public)
    println(encrypted)

    val private = Crypto.Converter.toString(key.private.encoded)
    println(private)
    val decrypted = Crypto.RSA.decryptFromString(encrypted, private)
    println(decrypted)
}