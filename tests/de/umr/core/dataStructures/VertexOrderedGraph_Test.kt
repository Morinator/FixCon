package de.umr.core.dataStructures

import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.edgeCount
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class VertexOrderedGraph_Test {

    @Test
    fun removeLastVertex() {
        val g = VertexOrderedGraph<Int>()
        g.addEdgeWithVertices(1, 2)
        g.addEdgeWithVertices(2, 3)
        g.addEdgeWithVertices(2, 4)

        assertTrue(g.removeLastVertex())
        assertEquals(setOf(1, 2, 3), g.vertexSet())
        assertEquals(2, g.edgeCount)

        assertTrue(g.removeLastVertex())
        assertEquals(setOf(1, 2), g.vertexSet())
        assertEquals(1, g.edgeCount)

        assertTrue(g.removeLastVertex())
        assertEquals(setOf(1), g.vertexSet())
        assertEquals(0, g.edgeCount)

        assertTrue(g.removeLastVertex())
        assertEquals(emptySet<Int>(), g.vertexSet())
        assertEquals(0, g.edgeCount)

        assertFalse(g.removeLastVertex())
    }

    @Test
    fun duplicateInsertion() {
        val g = VertexOrderedGraph<Int>()
        assertTrue(g.addVertex(1))
        assertEquals(setOf(1), g.vertexSet())

        repeat(5) {
            assertFalse(g.addVertex(1))
            assertEquals(setOf(1), g.vertexSet())
        }

        g.removeLastVertex()
        assertEquals(emptySet<Int>(), g.vertexSet())
    }
}