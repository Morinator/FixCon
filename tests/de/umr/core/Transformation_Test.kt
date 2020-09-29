package de.umr.core

import de.umr.core.dataStructures.addEdgeWithVertices
import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
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
            val size = g.vertexCount
            removeComponentsSmallerThreshold(g, 3)
            assertEquals(size, g.vertexCount)
        }

        @Test
        fun graph_3_times_5_test() {
            val g = fromVertices<Int>()
            for (i in 0..20 step 10)
                (i + 1 until i + 5).forEach { g.addEdgeWithVertices(i, it) }

            assertEquals(15, g.vertexCount)
            removeComponentsSmallerThreshold(g, 5)
            assertEquals(15, g.vertexCount)
            removeComponentsSmallerThreshold(g, 6)
            assertEquals(0, g.vertexCount)
        }

        @Test
        fun oneSmallComponent() {
            val g = graphFromFile(GraphFile.CustomTree)
            (101..105).forEach { g.addEdgeWithVertices(100, it) }
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
            connectVertexSets(g, setOf(0, 1, 2), setOf(3, 4, 5))
            assertEquals(13, g.edgeCount)
        }

        @Test
        fun twoSingleVertices() {
            val g = fromVertices(0, 1)
            connectVertexSets(g, setOf(0), setOf(1))
            assertEquals(1, g.edgeCount)
        }

        @Test
        fun errorOnNonExistentVertex() {
            val g = fromVertices(0)
            Assertions.assertThrows(Exception::class.java) { connectVertexSets(g, setOf(0), setOf(1)) }
        }
    }


    @Nested
    internal inner class addAsClique_test {

        @Test
        fun emptyToSize1() {
            val g = fromVertices<Int>()
            addAsClique(g, setOf(1))
            assertEquals(0, g.edgeCount)
            assertEquals(1, g.vertexCount)
        }

        @Test
        fun emptyToSize10() {
            val g = fromVertices<Int>()
            addAsClique(g, (1..10).toSet())
            assertEquals(45, g.edgeCount)
        }

        @Test
        fun nonEmptyTo10() {
            val g = fromUnweightedEdges(listOf(0 to 1, 1 to 2))
            addAsClique(g, (3..9).toSet())
            assertEquals(23, g.edgeCount)
        }
    }
}