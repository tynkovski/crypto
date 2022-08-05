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
    val data = "Hello, RSA!"
    val key = Crypto.RSA.generateKeyPair()

    val public = Crypto.Converter.toBase64String(key.public.encoded)
    println(public)
    val encrypted = Crypto.RSA.encryptToString(data, public)
    println(encrypted)

    val private = Crypto.Converter.toBase64String(key.private.encoded)
    println(private)

    val decrypted = Crypto.RSA.decryptFromString(encrypted, private)
    println(decrypted)

    // --
    println()

    val aesData = "Hello, AES!"
    val aesKey = Crypto.AES.generateKey()

    val aesRaw = Crypto.Converter.toBase64String(aesKey.encoded)
    println(aesRaw)

    val (aesEncrypted, aesIv) = Crypto.AES.encryptToString(aesData, aesRaw)
    println("$aesEncrypted $aesIv")

    val aesDecrypted = Crypto.AES.decryptFromString(aesEncrypted, aesRaw, aesIv)
    println(aesDecrypted)
}