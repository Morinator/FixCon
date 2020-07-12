package de.umr.core.dataStructures

import de.umr.core.io.edgesFromFile
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class GraphFileTest {

    @Test
    fun filesExist_and_rightFormat() {
        GraphFile.values().forEach {
            println("${it.name.padEnd(20)} ${it.path}")
            assertTrue(Files.exists(Paths.get(it.path)))

            assertTrue(edgesFromFile(it).isNotEmpty())
        }
    }
}