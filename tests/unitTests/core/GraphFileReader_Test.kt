package unitTests.core

import de.umr.FilePaths
import de.umr.core.GraphFileReader.simpleGraphByEdges
import de.umr.core.GraphFileReader.simpleGraphFromNetworkRepo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphFileReader_Test {
    @Test
    fun integration() {
        val g = simpleGraphFromNetworkRepo(FilePaths.sample)
        assertTrue(g.containsEdge(1, 2))
        assertFalse(g.containsEdge(1, 3))
        assertTrue(g.containsEdge(2, 3))
        assertFalse(g.containsEdge(2, 4))
        assertTrue(g.containsEdge(8, 9))
        assertFalse(g.containsEdge(8, 10))
        assertTrue(g.containsEdge(8, 12))
        assertTrue(g.containsEdge(15, 16))
        assertTrue(g.containsEdge(15, 17))
        assertFalse(g.containsEdge(15, 18))
    }

    @Test
    fun exceptionOnEmptyGraph() {
        assertThrows(IllegalArgumentException::class.java) { simpleGraphByEdges(ArrayList()) }
    }
}