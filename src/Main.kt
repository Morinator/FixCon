import de.umr.FilePaths
import kotlin.reflect.full.declaredMemberProperties

val x = 1

fun main() {
    FilePaths::class.declaredMemberProperties.forEach { println(it.toString()) }
}