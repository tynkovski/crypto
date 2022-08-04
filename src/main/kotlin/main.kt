import kotlinx.coroutines.coroutineScope
import kotlin.system.measureTimeMillis

import com.tynkovski.cryptography.Crypto

data class Message(
    var text: String,
    val time: Long
) {
    companion object {
        fun random(text: String): Message {
            return Message(text, (0 until Long.MAX_VALUE).random())
        }
    }
}

fun randomString(): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..32)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun main() {
    val kp = Crypto.generateKeyPair()
    val messages = arrayListOf<Message>()

    repeat(10_000) {
        messages.add(Message.random(randomString()))
    }

    val time1 = measureTimeMillis {
        messages.forEach { message ->
            message.text = Crypto.Converter.encryptToBase64String(message.text, kp.public)
        }
    }

    println("$time1 ms")

    val time2 = measureTimeMillis {
        messages.forEach { message ->
            message.text = Crypto.Converter.decryptFromBase64String(message.text, kp.private)
        }
    }

    println("$time2 ms")
}