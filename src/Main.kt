import de.umr.FilePaths
import de.umr.core.graphFromFile
import de.umr.core.shareOfDegree

fun main() {
    FilePaths.values().forEach {
        val g = try {
            graphFromFile(it)
        } catch (e: Exception) {
            graphFromFile(it, weighted = true)
        }
        println("${shareOfDegree(g, 2)} \t $it")
    }
}

