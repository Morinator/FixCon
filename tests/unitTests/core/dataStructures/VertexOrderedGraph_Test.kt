package unitTests.core.dataStructures

import de.umr.FilePaths
import de.umr.core.GraphFileReader.graphFromFile
import de.umr.core.addWeightedEdge
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.vertexCount
import de.umr.core.weightOfEdge
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VertexOrderedGraph_Test {

    @Test
    fun removeLastVertex() {
        val x = VertexOrderedGraph<Int>()
        addEdgeWithVertices(x, 1, 2)
        addEdgeWithVertices(x, 2, 3)
        addEdgeWithVertices(x, 2, 4)

        x.removeLastVertex()
        assertTrue(x.containsVertex(1))
        assertFalse(x.containsVertex(4))

        x.removeLastVertex()
        assertTrue(x.containsVertex(1))
        assertTrue(x.containsVertex(2))
        assertFalse(x.containsVertex(3))
        assertFalse(x.containsVertex(4))
    }

    @Test
    fun duplicateInsertion() {
        val x = VertexOrderedGraph<Int>()
        x.addVertex(1)
        x.addVertex(1)
        x.addVertex(1)
        assertEquals(1, x.vertexCount)
        x.removeLastVertex()
        assertEquals(0, x.vertexCount)
    }

    @Test
    fun size_test() = assertEquals(17, graphFromFile(FilePaths.sample).vertexCount)     //17 is manually counted

    @Test
    fun constructor_test() {
        val g = VertexOrderedGraph(1, -2, 0, 23, 5, 2, 2, 2, 2, 2, 2)
        assertTrue(1 in g.vertexSet())
        assertTrue(23 in g.vertexSet())
        assertFalse(3 in g.vertexSet())
        assertEquals(6, g.vertexCount)
    }

    @Test
    fun getEdgeWeight_test() {
        val g = VertexOrderedGraph(listOf(Triple(1, 2, 3.4), Triple(2, 4, 2.3)))
        assertEquals(3.4, g.weightOfEdge(1, 2))
        assertEquals(2.3, g.weightOfEdge(2, 4))
        assertThrows(IllegalStateException::class.java) { g.weightOfEdge(1, 4) }
        assertThrows(IllegalStateException::class.java) { g.weightOfEdge(0, 0) }
    }

    @Test
    fun addWeightedEdge_test() {
        val g = VertexOrderedGraph<Int>()
        g.addWeightedEdge(1, 2, PI)
        g.addWeightedEdge(2, 4, -6.4)
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.vertexCount == 3)
        assertEquals(-6.4, g.weightOfEdge(2, 4))
    }
}