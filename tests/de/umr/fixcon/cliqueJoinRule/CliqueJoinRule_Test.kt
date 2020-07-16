package de.umr.fixcon.cliqueJoinRule

import de.umr.core.*
import de.umr.core.extensions.edgeCount
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CliqueJoinRule_Test {

    @Nested
    internal inner class UnusedVertexSet {

        @Test
        fun size0() = assertEquals(setOf<Int>(), unusedVertexSet(createPath(4), 0))

        @Test
        fun size3() = assertEquals(setOf(5, 6, 7), unusedVertexSet(createCircle(5), 3))

        @Test
        fun size5() = assertEquals(setOf(13, 14, 15, 16, 17), unusedVertexSet(createStar(13), 5))
    }


    @Nested
    internal inner class AddAsClique {

        @Test
        fun path5_clique4() {
            val g = createPath(5)
            addAsClique(g, unusedVertexSet(g, 4))
            assertEquals(setOf(setOf(0, 1, 2, 3, 4), setOf(5, 6, 7, 8)), ConnectivityInspector(g).connectedSets().toSet())
            assertEquals(10, g.edgeCount)
        }

        @Test
        fun path3_clique6() {
            val g = fromUnweightedEdges(listOf(0 to 1))
            addAsClique(g, unusedVertexSet(g, 6))
            assertEquals(setOf(setOf(0, 1), setOf(2, 3, 4, 5, 6, 7)), ConnectivityInspector(g).connectedSets().toSet())
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
            val g = fromVertices(0,1)
            connectVertexSets(g, setOf(0), setOf(1))
            assertEquals(1, g.edgeCount)
        }
    }

    @Nested
    internal inner class ExtendableVertices {

        @Test
        fun path3InStar() {
            assertEquals(setOf(0), extendableVertices(fromUnweightedEdges(listOf(0 to 1, 0 to 2)), createStar(10)))
        }

        @Test
        fun clique4InClique6() {
            assertEquals(setOf(0,1,2,3), extendableVertices(createClique(4), createClique(6)))
        }
    }

}