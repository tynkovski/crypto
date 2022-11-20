import com.tynkovski.utils.archiver.Archiver
import com.tynkovski.utils.extentions.times

fun main() {
    val data = listOf(
        "a"
    ) * 1000
    println(data)
    val archived = Archiver.zip(data.joinToString(""))
    println(archived.size)
    val unarchived = Archiver.unzip(archived)
    println(unarchived.length)
}