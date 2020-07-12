package de.umr.core.io

import de.umr.core.dataStructures.GraphFile.*
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.vertexCount
import de.umr.core.extensions.weightOfEdge
import de.umr.core.fromWeightedEdges
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
            assertThrows(IllegalArgumentException::class.java) { fromWeightedEdges(edgesFromFile(BadGraph, allowLoops = true)) }
        }
    }


    @Nested
    internal inner class weightedGraphs_Tests {

        @Test
        fun sampleGraph_weighted_Test() {
            val g = graphFromFile(Sample)
            assertTrue(g.containsEdge(1, 2))
            assertFalse(g.containsEdge(1, 3))
            assertEquals(1.0, g.weightOfEdge(1, 2))  //Default weight of JgraphT is 1.0
        }

        /** This file contains a graph with weighted edges. It is checked if the weights are read in correctly.*/
        @Test
        fun edgeWeights_fromFile_Test() {
            val g = graphFromFile(CaSandiAuths)
            assertTrue(g.containsEdge(35, 1))
            assertEquals(2.0, g.weightOfEdge(35, 1))

            assertTrue(g.containsEdge(7, 3))
            assertEquals(1.0, g.weightOfEdge(7, 3))

            assertTrue(g.containsEdge(43, 53))
            assertEquals(6.0, g.weightOfEdge(43, 53))
        }

    }
}