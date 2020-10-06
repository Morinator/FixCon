package de.umr.core

import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vCount
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class Transformation_Test {

    @Nested
    internal inner class removeSmallComponents_test {
        @Test
        fun tree_test() {
            val g = graphFromFile(GraphFile.CustomTree)
            val size = g.vCount
            removeComponentsSmallerThreshold(g, 3)
            assertEquals(size, g.vCount)
        }

        @Test
        fun graph_3_times_5_test() {
            val g = fromVertices<Int>()
            for (i in 0..20 step 10)
                (i + 1 until i + 5).forEach { addEdgeWithVertices(g, i, it) }

            assertEquals(15, g.vCount)
            removeComponentsSmallerThreshold(g, 5)
            assertEquals(15, g.vCount)
            removeComponentsSmallerThreshold(g, 6)
            assertEquals(0, g.vCount)
        }

        @Test
        fun oneSmallComponent() {
            val g = graphFromFile(GraphFile.CustomTree)
            (101..105).forEach { addEdgeWithVertices(g, 100, it) }
            assertEquals(2, ConnectivityInspector(g).connectedSets().size)
            removeComponentsSmallerThreshold(g, 10)
            assertTrue(ConnectivityInspector(g).isConnected)
        }
    }

    @Nested
    internal inner class ConnectVertexSets {

        @Test
        fun twoPathsLength3() {
            val g = fromUnweightedEdges(listOf(0 to 1, 1 to 2, 3 to 4, 4 to 5))
            connectVertices(g, setOf(0, 1, 2), setOf(3, 4, 5))
            assertEquals(13, g.edgeCount)
        }

        @Test
        fun twoSingleVertices() {
            val g = fromVertices(0, 1)
            connectVertices(g, setOf(0), setOf(1))
            assertEquals(1, g.edgeCount)
        }

        @Test
        fun errorOnNonExistentVertex() {
            val g = fromVertices(0)
            Assertions.assertThrows(Exception::class.java) { connectVertices(g, setOf(0), setOf(1)) }
        }
    }
}