import com.tynkovski.cryptography.AESFactory
import com.tynkovski.cryptography.RSAFactory
import kotlin.system.measureTimeMillis

data class Message(
    var text: String,
    var extra: String = ""
)

fun main() {
    val listAES = mutableListOf<Message>()
    val key = "I2Y3mfrQUYkGdfPpxyRUCxV5+bvAoT2hcooGqnFdAZo="
    repeat(10_000) {
        listAES.add(Message(String.random((1..100))))
    }

    val timeEncryptionAES = measureTimeMillis {
        listAES.forEach { message ->
            val (decrypted, iv) = AESFactory.encrypt(message.text, key)
            message.text = decrypted
            message.extra = iv
        }
    }
    println("timeEncryptionAES: $timeEncryptionAES")

    val timeDecryptionAES = measureTimeMillis {
        listAES.forEach { message ->
            message.text = AESFactory.decrypt(message.text, key, message.extra)
        }
    }
    println("timeDecryptionAES: $timeDecryptionAES")

    val listRSA = mutableListOf<Message>()
    repeat(10_000) {
        listRSA.add(Message(String.random((1..100))))
    }
    val public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNCAwmqY6lhz06+ItYzhyMGEmBgPuNAq35fyz/GPMI8C/sNEHHvyPD2VNQ4+KQ8UmiOfvHDJf9w9vzbtiW9djFuBjyI2bZaRlQau0One81pkeBdZHM72nfQTmU/wMiz8q6Fp7zm3c7ajQqgCyFZP6rh5eiLLAgujLum/rIRf5XewIDAQAB"
    val private = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM0IDCapjqWHPTr4i1jOHIwYSYGA+40Crfl/LP8Y8wjwL+w0Qce/I8PZU1Dj4pDxSaI5+8cMl/3D2/Nu2Jb12MW4GPIjZtlpGVBq7Q6d7zWmR4F1kczvad9BOZT/AyLPyroWnvObdztqNCqALIVk/quHl6IssCC6Mu6b+shF/ld7AgMBAAECf12QeT/BVuocEOChQK82YeNjGPyIpqa6NbK7quT9W2HhaehZZzbWFMo5nBnYURjPrlT/Rmav0lUsu/vrxnCQAQ2aVj+S9rHwR3t/eYEkOpB/mwDAM3BgJmPN2KRjC8D+wSd73TkQjhJWq77hmepmd1G5F9S8zd9/sd/3WlrKFtECQQDgzlr/PQETZtlmVzYmaPfvrgfKg2JFZVPuZJXAdFXwKIqGXUGgZWrE01RmwCBQmsSvOj1bkhKSQSiYi/l8rkkRAkEA6Xs/+lunxSUaSVlv0ogWgtsquxkL5WXgacTLvtYJ9AOPCkLxjRCEx8YutND41pbPQv7oGvgkYo9n43iv4pv3ywJBAICtfabFu3zojR4oY2V/BhRacDk6FSEVzAIT7yf3L03FVu1sKeluw7ugH5qk9JpOzLBkTMTCiwDObTdMP+VfOZECQQDD7Z2eplN/aMdKTjc8YN8jKHAPWdFVy8wqde7UUiFAF8xtPAgf7IDAdFq1ebeXvC5pdieomhtOajEv/hL7Aw9JAkEAzEkfcje37FCG966m7DWzmnBBHIHaMEyajt2D/pyOjtgzbbQMNo9QQfOcg5TydmEDyVQ9h6DbamGC0s/3V2kQ8g=="

    val timeEncryptionRSA = measureTimeMillis {
        listRSA.forEach { message ->
            message.text = RSAFactory.encrypt(message.text, public)
        }
    }
    println("timeEncryptionRSA: $timeEncryptionRSA")

    val timeDecryptionRSA = measureTimeMillis {
        listRSA.forEach { message ->
            message.text = RSAFactory.decrypt(message.text, private)
        }
    }
    println("timeDecryptionRSA: $timeDecryptionRSA")

    // timeEncryptionAES: 282
    // timeDecryptionAES: 92
    // timeEncryptionRSA: 423
    // timeDecryptionRSA: 2062
}