import de.umr.FilePaths
import de.umr.core.edgesFromFile
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions.assertTrue

internal class FilePathsTest {

    @Test
    fun main() {
        FilePaths.values().forEach {

            assertTrue(Files.exists(Paths.get(it.path)))

            //Contains lines that match the weighted or unweighted format
            assertTrue(edgesFromFile(it).isNotEmpty() || edgesFromFile(it, weighted = true).isNotEmpty())
        }
    }
}