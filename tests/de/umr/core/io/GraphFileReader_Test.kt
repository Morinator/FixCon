package de.umr.core.io

import de.umr.core.dataStructures.GraphFile.*
import de.umr.core.extensions.vertexCount
import de.umr.core.graphFromWeightedEdges
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GraphFileReader_Test {

    @Nested
    internal inner class defaultWeightedGraphs_Tests {

        @Test
        fun sampleGraph_unweighted_Test() {
            val g = graphFromFile(Sample)
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
        fun badGraph_test() {
            val g = graphFromFile(BadGraph)
            assertEquals(20, g.vertexCount)
            assertTrue(g.containsEdge(1, 2))
            assertThrows(IllegalArgumentException::class.java) { graphFromWeightedEdges(edgesFromFile(BadGraph, allowLoops = true)) }
        }
    }
}