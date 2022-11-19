import com.tynkovski.cryptography.AESFactory
import com.tynkovski.cryptography.Base64Converter
import com.tynkovski.cryptography.Crypto
import java.io.File
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

fun saveFile(path: String, fileName: String, array: ByteArray) {
    File("$path//$fileName").writeBytes(array)
}

fun openFile(path: String, fileName: String): ByteArray {
    return File("$path//$fileName").readBytes()
}

fun getResource(path: String): ByteArray? =
    object {}.javaClass.getResource(path)?.readBytes()

fun main() {
    val user = Profile()
    println("    key: ${String(user.key)}")

    val originalPhoto = openFile(File("images/").absolutePath, "original.jpg")
    val (encrypted, iv) = AESFactory.encrypt(originalPhoto, user.key)
    saveFile(File("images/").absolutePath, "encrypted.txt", encrypted)

    val encryptedPhoto = openFile(File("images/").absolutePath, "encrypted.txt")
    val decrypted = AESFactory.decrypt(encryptedPhoto, user.key, iv)
    saveFile(File("images/").absolutePath, "decrypted.jpg", decrypted)
}