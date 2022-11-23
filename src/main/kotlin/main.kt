import com.tynkovski.utils.archiver.Archiver
import com.tynkovski.utils.extentions.times

fun main() {
    val data = "a" * 100
    println(data)

    val archived = Archiver.zip(data.toByteArray())
    println(archived.size)
    println(String(archived))

    val unarchived = Archiver.unzip(archived)
    println(unarchived.size)
    println(String(unarchived))
}