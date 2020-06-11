package core

import de.umr.FilePaths
import de.umr.core.*
import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.graph.SimpleWeightedGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.PI

class JGraphT_Extensions_Test {

    @Test
    fun vertexCount_test() {
        assertEquals(17, graphFromFile(FilePaths.sample).vertexCount) //17 is manually counted
        assertEquals(0, VertexOrderedGraph<Int>().vertexCount)
    }

    @Test
    fun edgeCount_test() {
        assertEquals(0, VertexOrderedGraph<Int>().edgeCount)

        assertEquals(1, createClique(2).edgeCount)
        assertEquals(10, createClique(5).edgeCount)
        assertEquals(45, createClique(10).edgeCount)

        assertEquals(1, createCircle(2).edgeCount)
        assertEquals(5, createCircle(5).edgeCount)
        assertEquals(10, createCircle(10).edgeCount)

        assertEquals(1, createPath(2).edgeCount)
        assertEquals(4, createPath(5).edgeCount)
        assertEquals(9, createPath(10).edgeCount)

        assertEquals(1, createStar(2).edgeCount)
        assertEquals(4, createStar(5).edgeCount)
        assertEquals(9, createStar(10).edgeCount)
    }

    @Test
    fun weightOfEdge_test() {
        val g = VertexOrderedGraph.fromEdges(listOf(Triple(1, 2, 3.4), Triple(2, 4, 2.3)))
        assertEquals(3.4, g.weightOfEdge(1, 2))
        assertEquals(2.3, g.weightOfEdge(2, 4))
        assertThrows(IllegalArgumentException::class.java) { g.weightOfEdge(1, 4) }
        assertThrows(IllegalArgumentException::class.java) { g.weightOfEdge(0, 0) }
    }

    @Test
    fun addWeightedEdge_test() {
        val g = VertexOrderedGraph<Int>()

        g.addWeightedEdge(1, 2, PI)
        g.addWeightedEdge(1, 2, PI)
        assertTrue(g.containsEdge(1, 2))
        assertEquals(PI, g.weightOfEdge(1, 2))

        g.addWeightedEdge(2, 4, -6.4)
        assertEquals(-6.4, g.weightOfEdge(2, 4))
        assertTrue(g.vertexCount == 3)
    }

    @Test
    fun addWeightedEdge_Chaining_test() {
        val g = VertexOrderedGraph<Int>().addWeightedEdge(1, 2, PI).addWeightedEdge(1, 2, PI).addWeightedEdge(1, 2, PI)
        assertTrue(g.containsEdge(1, 2))
        assertEquals(PI, g.weightOfEdge(1, 2))
    }

    @Nested
    internal inner class neighbour_Tests {
        @Test
        fun openNB_singleVertex() {
            assertEquals(setOf(1, 2, 3), createClique(4).openNB(0))
            assertEquals(setOf(4, 6), createPath(10).openNB(5))
            assertEquals(setOf(4, 6), createCircle(10).openNB(5))
            assertEquals(setOf(1, 9), createCircle(10).openNB(0))
            assertEquals(setOf(1, 2, 3, 4), createStar(5).openNB(0))
            assertEquals(setOf(0), createStar(5).openNB(1))
        }

        @Test
        fun openNB_multipleVertices() {
            assertEquals(setOf(2, 3), createClique(5).openNB(0, 1, 4))
            assertEquals(setOf(4, 8), createPath(10).openNB(5, 6, 7))
            assertEquals(setOf(1, 3, 4, 6), createPath(10).openNB(2, 5))
            assertEquals(setOf(4, 8), createCircle(10).openNB(5, 6, 7))
            assertEquals(setOf(1, 9, 4, 6), createCircle(10).openNB(0, 5))
            assertEquals(setOf(2, 4), createStar(5).openNB(0, 1, 3))
            assertEquals(setOf(0), createStar(5).openNB(1, 2, 3, 4))
        }

        @Test
        fun closedNB_singleVertex() {
            assertEquals(setOf(0, 1, 2, 3), createClique(4).closedNB(0))
            assertEquals(setOf(4, 5, 6), createPath(10).closedNB(5))
            assertEquals(setOf(4, 5, 6), createCircle(10).closedNB(5))
            assertEquals(setOf(1, 0, 9), createCircle(10).closedNB(0))
            assertEquals(setOf(0, 1, 2, 3, 4), createStar(5).closedNB(0))
            assertEquals(setOf(0, 1), createStar(5).closedNB(1))
        }

        @Test
        fun closedNB_multipleVertices() {
            assertEquals(setOf(0, 1, 2, 3), createClique(4).closedNB(0, 1, 2, 3))
            assertEquals(setOf(4, 5, 6, 7, 8), createPath(10).closedNB(5, 7))
            assertEquals(setOf(1, 2, 3, 6, 7, 8), createCircle(10).closedNB(2, 7))
            assertEquals(setOf(0, 1, 2, 9), createCircle(10).closedNB(0, 1))
            assertEquals(setOf(0, 1, 2, 3, 4), createStar(5).closedNB(0,1,2,3,4))
            assertEquals(setOf(0, 1,3,4), createStar(5).closedNB(1,3,4))
        }
    }

    @Test
    fun addEdgeWithVertices_test() {
        val x = SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        assertTrue(x.addEdgeWithVertices(1, 2))
        assertTrue(x.containsEdge(1, 2))
        assertFalse(x.addEdgeWithVertices(1, 2))
        assertTrue(x.containsEdge(1, 2))
    }

    @Nested
    internal inner class hasTriangle_Tests {

        @Test
        fun graphsFromNetworkRepo() {
            var g = graphFromFile(FilePaths.sample)
            assertTrue(g.hasTriangle)
            g = graphFromFile(".//graph_files//p-hat1500-3.mtx")
            assertTrue(g.hasTriangle)
        }

        @Test
        fun emptyGraph() {
            assertFalse((SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)).hasTriangle)
        }

        @Test
        fun threeClique() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            Graphs.addEdgeWithVertices(g, 1, 2)
            Graphs.addEdgeWithVertices(g, 1, 3)
            Graphs.addEdgeWithVertices(g, 2, 3)
            assertTrue(g.hasTriangle)
        }

        @Test
        fun fourClique() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            for (i in 1..4)
                for (j in 1..4)
                    if (i != j)
                        Graphs.addEdgeWithVertices(g, i, j)
            assertTrue(g.hasTriangle)
        }

        @Test
        fun longPath() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            for (i in 1..10)
                Graphs.addEdgeWithVertices(g, i, i + 1)
            assertFalse(g.hasTriangle)
        }
    }

    @Nested
    internal inner class IntGraph_getCopy_Tests {

        @Test
        fun intGraph_test() {
            val g1 = graphFromFile(FilePaths.sample)
            val g2 = g1.getCopy()
            assertTrue(g1.containsEdge(1, 2))
            g1.removeVertex(1)
            assertFalse(g1.containsEdge(1, 2))

            assertTrue(g2.containsEdge(1, 2))   //g2 is unaltered
        }


        @Test
        fun stringGraph_test() {
            val g1 = VertexOrderedGraph<String>()
            g1.addEdgeWithVertices("Hund", "Katze")
            g1.addEdgeWithVertices("Hund", "Giraffe")

            val g2 = g1.getCopy()

            assertTrue(g1.containsEdge("Hund", "Giraffe"))
            g1.removeVertex("Hund")
            assertFalse(g1.containsEdge("Hund", "Giraffe"))

            assertTrue(g2.containsEdge("Hund", "Giraffe"))   //g2 is unaltered
        }

    }
}