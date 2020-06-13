import de.umr.FilePaths
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions.assertTrue

internal class FilePathsTest {

    @Test
    fun main() {
        FilePaths.values().forEach {
            assertTrue(Files.exists(Paths.get(it.path)))
        }
    }
}