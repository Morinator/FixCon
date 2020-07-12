package de.umr.core.dataStructures

import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import de.umr.core.extensions.weightOfEdge
import de.umr.core.fromUnweightedEdges
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import de.umr.core.dataStructures.VertexOrderedGraph as VOG

class VertexOrderedGraph_Test {

    @Test
    fun removeLastVertex() {
        val g = VOG<Int>()
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
        val g = VOG<Int>()
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