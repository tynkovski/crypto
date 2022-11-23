import com.tynkovski.utils.archiver.Archiver
import com.tynkovski.utils.extentions.times

fun main() {
    val data = (listOf(
        "a"
    ) * 1000).joinToString("")

    println(data)

    val archived = Archiver.zip(data.toByteArray())
    println(archived.size)
    println(String(archived))

    val unarchived = Archiver.unzip(archived)
    println(unarchived.size)
    println(String(unarchived))
}