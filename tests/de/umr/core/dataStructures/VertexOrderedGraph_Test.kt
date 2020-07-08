package de.umr.core.dataStructures

import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import de.umr.core.extensions.weightOfEdge
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

    @Nested
    internal inner class Constructors {

        @Test
        fun fromVertices() {
            val g = VOG.fromVertices(1, -2, 0, 23, 5, 2, 2, 2, 2, 2, 2)

            assertTrue(1 in g.vertexSet())
            assertTrue(23 in g.vertexSet())
            assertFalse(3 in g.vertexSet())
            assertEquals(6, g.vertexCount)

            assertEquals(0, g.edgeCount)
        }

        @Test
        fun fromWeightedEdges() {
            val g = VOG.fromWeightedEdges(listOf(Triple(1, 2, 3.0), Triple(1, 3, 4.0), Triple(5, 12, -1.0)))
            assertEquals(5, g.vertexCount)
            assertEquals(3, g.edgeCount)

            assertEquals(3.0, g.weightOfEdge(1, 2))
            assertEquals(4.0, g.weightOfEdge(1, 3))
            assertEquals(-1.0, g.weightOfEdge(5, 12))
        }

        @Test
        fun emptyEdges() {
            assertThrows(IllegalArgumentException::class.java) { VOG.fromWeightedEdges(emptyList<Triple<Int, Int, Double>>()) }
            assertThrows(IllegalArgumentException::class.java) { VOG.fromUnweightedEdges(emptyList<Pair<Int, Int>>()) }
        }
    }
}