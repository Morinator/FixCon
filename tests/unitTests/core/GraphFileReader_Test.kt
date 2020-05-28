package unitTests.core

import de.umr.FilePaths
import de.umr.core.GraphFileReader.defaultWeightedGraphByEdges
import de.umr.core.GraphFileReader.simpleGraphFromNetworkRepo
import de.umr.core.GraphFileReader.weightedGraphFromNetworkRepo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphFileReader_Test {

    @Nested
    internal inner class defaultWeightedGraphs_Tests {

        @Test
        fun sampleGraph_unweighted_Test() {
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
            assertThrows(IllegalArgumentException::class.java) { defaultWeightedGraphByEdges(ArrayList()) }
        }

    }


    @Nested
    internal inner class weightedGraphs_Tests {

        @Test
        fun sampleGraph_weighted_Test() {
            val g = simpleGraphFromNetworkRepo(FilePaths.sample)
            assertTrue(g.containsEdge(1, 2))
            assertFalse(g.containsEdge(1, 3))
            assertEquals(1.0, g.getEdgeWeight(1, 2))  //Default weight of JgraphT is 1.0
        }

        /** This file contains a graph with weighted edges. It is checked if the weights are read in correctly.*/
        @Test
        fun edgeWeights_fromFile_Test() {
            val g = weightedGraphFromNetworkRepo(FilePaths.caSandiAuths)
            assertTrue(g.containsEdge(35, 1))
            assertEquals(2.0, g.getEdgeWeight(35, 1))

            assertTrue(g.containsEdge(7, 3))
            assertEquals(1.0, g.getEdgeWeight(7, 3))

            assertTrue(g.containsEdge(43, 53))
            assertEquals(6.0, g.getEdgeWeight(43, 53))
        }

    }
}