package unitTests.core.dataStructures

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.vertexCount
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Test
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
    fun constructor_test() {
        val g = VertexOrderedGraph(1, -2, 0, 23, 5, 2, 2, 2, 2, 2, 2)
        assertTrue(1 in g.vertexSet())
        assertTrue(23 in g.vertexSet())
        assertFalse(3 in g.vertexSet())
        assertEquals(6, g.vertexCount)
    }
}