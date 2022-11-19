import com.tynkovski.cryptography.AESFactory
import com.tynkovski.cryptography.Base64Converter
import com.tynkovski.cryptography.Crypto
import com.tynkovski.cryptography.RSAFactory
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
    run {
        val key = "I2Y3mfrQUYkGdfPpxyRUCxV5+bvAoT2hcooGqnFdAZo="
        val data = "Hello, AES!"

        val (encrypted, iv) = AESFactory.encrypt(data, key)
        println(encrypted)

        val decrypted = AESFactory.decrypt(encrypted, key, iv)
        println(decrypted)
    }

    run {
        val public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNCAwmqY6lhz06+ItYzhyMGEmBgPuNAq35fyz/GPMI8C/sNEHHvyPD2VNQ4+KQ8UmiOfvHDJf9w9vzbtiW9djFuBjyI2bZaRlQau0One81pkeBdZHM72nfQTmU/wMiz8q6Fp7zm3c7ajQqgCyFZP6rh5eiLLAgujLum/rIRf5XewIDAQAB"
        val private = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM0IDCapjqWHPTr4i1jOHIwYSYGA+40Crfl/LP8Y8wjwL+w0Qce/I8PZU1Dj4pDxSaI5+8cMl/3D2/Nu2Jb12MW4GPIjZtlpGVBq7Q6d7zWmR4F1kczvad9BOZT/AyLPyroWnvObdztqNCqALIVk/quHl6IssCC6Mu6b+shF/ld7AgMBAAECf12QeT/BVuocEOChQK82YeNjGPyIpqa6NbK7quT9W2HhaehZZzbWFMo5nBnYURjPrlT/Rmav0lUsu/vrxnCQAQ2aVj+S9rHwR3t/eYEkOpB/mwDAM3BgJmPN2KRjC8D+wSd73TkQjhJWq77hmepmd1G5F9S8zd9/sd/3WlrKFtECQQDgzlr/PQETZtlmVzYmaPfvrgfKg2JFZVPuZJXAdFXwKIqGXUGgZWrE01RmwCBQmsSvOj1bkhKSQSiYi/l8rkkRAkEA6Xs/+lunxSUaSVlv0ogWgtsquxkL5WXgacTLvtYJ9AOPCkLxjRCEx8YutND41pbPQv7oGvgkYo9n43iv4pv3ywJBAICtfabFu3zojR4oY2V/BhRacDk6FSEVzAIT7yf3L03FVu1sKeluw7ugH5qk9JpOzLBkTMTCiwDObTdMP+VfOZECQQDD7Z2eplN/aMdKTjc8YN8jKHAPWdFVy8wqde7UUiFAF8xtPAgf7IDAdFq1ebeXvC5pdieomhtOajEv/hL7Aw9JAkEAzEkfcje37FCG966m7DWzmnBBHIHaMEyajt2D/pyOjtgzbbQMNo9QQfOcg5TydmEDyVQ9h6DbamGC0s/3V2kQ8g=="
        val data = "Hello, RSA!"

        val encrypted = RSAFactory.encrypt(data, public)
        println(encrypted)

        val decrypted = RSAFactory.decrypt(encrypted, private)
        println(decrypted)
    }
}