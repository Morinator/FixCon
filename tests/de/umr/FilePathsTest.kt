package de.umr

import de.umr.core.io.edgesFromFile
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions.assertTrue

internal class FilePathsTest {

    @Test
    fun filesExist_and_rightFormat() {
        FilePaths.values().forEach {
            assertTrue(Files.exists(Paths.get(it.path)))

            assertTrue(edgesFromFile(it, it.weighted).isNotEmpty())
        }
    }
}