//package de.umr.fixcon.cliqueJoinRule
//
//import de.umr.core.*
//import de.umr.core.dataStructures.getEdgeCount
//import de.umr.fixcon.Problem
//import de.umr.fixcon.graphFunctions.MinDegreeFunction
//import org.jgrapht.alg.connectivity.ConnectivityInspector
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertThrows
//import org.junit.jupiter.api.Nested
//import org.junit.jupiter.api.Test
//
//internal class CliqueJoinRule_Test {
//
//    @Nested
//    internal inner class GetNewVertexIDs {
//
//        @Test
//        fun size0() = assertEquals(setOf<Int>(), getNewVertexIDs(createPath(4), 0))
//
//        @Test
//        fun size3() = assertEquals(setOf(5, 6, 7), getNewVertexIDs(createCircle(5), 3))
//
//        @Test
//        fun size5() = assertEquals(setOf(13, 14, 15, 16, 17), getNewVertexIDs(createStar(13), 5))
//    }
//
//    @Nested
//    internal inner class AddAsClique {
//
//        @Test
//        fun path5_clique4() {
//            val g = createPath(5)
//            addAsClique(g, getNewVertexIDs(g, 4))
//            assertEquals(setOf(setOf(0, 1, 2, 3, 4), setOf(5, 6, 7, 8)), ConnectivityInspector(g).connectedSets().toSet())
//            assertEquals(10, g.edgeCount)
//        }
//
//        @Test
//        fun path3_clique6() {
//            val g = fromUnweightedEdges(listOf(0 to 1))
//            addAsClique(g, getNewVertexIDs(g, 6))
//            assertEquals(setOf(setOf(0, 1), setOf(2, 3, 4, 5, 6, 7)), ConnectivityInspector(g).connectedSets().toSet())
//        }
//
//    }
//
//    @Nested
//    internal inner class ConnectVertexSets {
//
//        @Test
//        fun twoPathsLength3() {
//            val g = fromUnweightedEdges(listOf(0 to 1, 1 to 2, 3 to 4, 4 to 5))
//            connectVertexSets(g, setOf(0, 1, 2), setOf(3, 4, 5))
//            assertEquals(13, g.edgeCount)
//        }
//
//        @Test
//        fun twoSingleVertices() {
//            val g = fromVertices(0, 1)
//            connectVertexSets(g, setOf(0), setOf(1))
//            assertEquals(1, g.edgeCount)
//        }
//
//        @Test
//        fun errorOnNonExistentVertex() {
//            val g = fromVertices(0)
//            assertThrows(Exception::class.java) { connectVertexSets(g, setOf(0), setOf(1)) }
//        }
//    }
//
//    @Nested
//    internal inner class ExtendableVertices {
//
//        @Test
//        fun path3InStar() = assertEquals(setOf(0), extendableVertices(fromUnweightedEdges(listOf(0 to 1, 0 to 2)), createStar(10)))
//
//        @Test
//        fun clique4InClique6() = assertEquals(setOf(0, 1, 2, 3), extendableVertices(createClique(4), createClique(6)))
//
//        @Test
//        fun path3InPath5() = assertEquals(setOf(2), extendableVertices(createPath(3), createPath(5)))
//    }
//
//    @Nested
//    internal inner class CliqueJoinValue {
//
//        @Test
//        fun path4InClique6_k6() = assertEquals(3, cliqueJoinValue(createPath(4), Problem(createClique(6), MinDegreeFunction(6))))
//    }
//
//}