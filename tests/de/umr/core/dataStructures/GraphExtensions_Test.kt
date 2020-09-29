package de.umr.core.dataStructures

import de.umr.core.*
import de.umr.core.graphFromFile
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.AsSubgraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GraphExtensions_Test {

    @Nested
    internal inner class expandSubgraph_Tests {

        @Test
        fun p3_in_clique() {
            val g = createClique(10)
            val sub = createPath(3)

            sub.expandSubgraph(g, 3)     //vertex 3 has edge to all of {0, 1, 2}
            assertEquals(5, sub.edgeCount)

            sub.expandSubgraph(g, 5)
            assertEquals(9, sub.edgeCount)

            sub.expandSubgraph(g, 9)
            assertEquals(14, sub.edgeCount)
        }

        @Test
        fun singlesInPath() {
            val g = createPath(10)
            val sub = fromVertices(0)

            (2..9).forEach { sub.expandSubgraph(g, it) }

            //vertex 0 has no edge to any vertex of {2..9} in original
            assertEquals(0, sub.edgeCount)
            assertEquals(1, sub.vertexCount)
        }

        @Test
        fun centerOfStar() {
            val g = createStar(10)
            val sub = fromVertices(1, 2, 3, 4, 5, 6, 7, 8, 9)

            assertEquals(0, sub.edgeCount)

            sub.expandSubgraph(g, 0)

            //connects the vertex in the center of the star
            assertEquals(9, sub.edgeCount)
            assertEquals(10, sub.vertexCount)
        }

        @Test
        fun illegalNewVertex() {
            assertThrows(IllegalArgumentException::class.java) { createPath(3).expandSubgraph(createClique(20), 100) }
        }

        @Test
        fun sample1() {
            val g = graphFromFile(GraphFile.Sample)
            val sub = AsSubgraph(g, setOf(8, 9, 10)).copy()
            assertEquals(2, sub.edgeCount)

            sub.expandSubgraph(g, 11)
            assertEquals(4, sub.edgeCount)

            sub.expandSubgraph(g, 12)
            assertEquals(6, sub.edgeCount)
        }
    }

    @Test
    fun vertexCount_test() {
        assertEquals(0, fromVertices<Int>().vertexCount)
        assertEquals(10, createClique(10).vertexCount)
        assertEquals(15, createCircle(15).vertexCount)
        assertEquals(17, graphFromFile(GraphFile.Sample).vertexCount) //17 is manually counted
        assertEquals(21, createPath(21).vertexCount)
        assertEquals(37, createStar(37).vertexCount)

    }

    @Test
    fun edgeCount_test() {

        assertEquals(0, fromVertices<Int>().edgeCount)

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

        val g = graphFromFile(GraphFile.CustomTree)
        assertEquals(g.vertexCount, g.edgeCount + 1)
    }

    @Nested
    internal inner class neighbour_Tests {
        @Test
        fun openNB_singleVertex() {
            assertEquals(setOf(1, 2, 3), neighborSetOf(createClique(4),0))
            assertEquals(setOf(4, 6), neighborSetOf(createPath(10), 5))
            assertEquals(setOf(4, 6), neighborSetOf(createCircle(10), 5))
            assertEquals(setOf(1, 9), neighborSetOf(createCircle(10), 0))
            assertEquals(setOf(1, 2, 3, 4), neighborSetOf(createStar(5), 0))
            assertEquals(setOf(0), neighborSetOf(createStar(5), 1))
        }

        @Test
        fun openNB_multipleVertices() {
            assertEquals(setOf(2, 3), createClique(5).neighbours(setOf(0, 1, 4)))
            assertEquals(setOf(4, 8), createPath(10).neighbours(setOf(5, 6, 7)))
            assertEquals(setOf(1, 3, 4, 6), createPath(10).neighbours(setOf(2, 5)))
            assertEquals(setOf(4, 8), createCircle(10).neighbours(setOf(5, 6, 7)))
            assertEquals(setOf(1, 9, 4, 6), createCircle(10).neighbours(setOf(0, 5)))
            assertEquals(setOf(2, 4), createStar(5).neighbours(setOf(0, 1, 3)))
            assertEquals(setOf(0), createStar(5).neighbours(setOf(1, 2, 3, 4)))
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
        fun repetition() {
            val g = createPath(3)
            assertEquals(setOf(0,1,2), g.closedNB(1))
        }

        @Test
        fun closedNB_multipleVertices() {
            assertEquals(setOf(0, 1, 2, 3), createClique(4).closedNB(setOf(0, 1, 2, 3)))
            assertEquals(setOf(4, 5, 6, 7, 8), createPath(10).closedNB(setOf(5, 7)))
            assertEquals(setOf(1, 2, 3, 6, 7, 8), createCircle(10).closedNB(setOf(2, 7)))
            assertEquals(setOf(0, 1, 2, 9), createCircle(10).closedNB(setOf(0, 1)))
            assertEquals(setOf(0, 1, 2, 3, 4), createStar(5).closedNB(setOf(0, 1, 2, 3, 4)))
            assertEquals(setOf(0, 1, 3, 4), createStar(5).closedNB(setOf(1, 3, 4)))
        }

        @Test
        fun openNBEquals() {
            assertTrue(createBipartite(6, 9).openNBEqualsFast(0, 1))
            assertFalse(createBipartite(5, 5).apply { addEdge(0, 1) }.openNBEqualsFast(0, 1))
            assertTrue(createStar(14).openNBEqualsFast(1, 2))
            assertFalse(createClique(10).openNBEqualsFast(1, 2))
            assertFalse(createPath(14).openNBEqualsFast(1, 6))
            assertTrue(createPath(3).openNBEqualsFast(0, 2))
            assertFalse(createPath(2).openNBEqualsFast(0, 1))
        }

        @Test
        fun closedNBEquals() {
            assertTrue(createClique(10).closedNBEqualsFast(1, 2))
            with(createClique(5).apply { addEdgeWithVertices(0, 10); addEdgeWithVertices(1, 10) }) {
                assertTrue(closedNBEqualsFast(0, 1))
                assertFalse(closedNBEqualsFast(0, 2))
            }
            assertFalse(createStar(14).closedNBEqualsFast(1, 2))
            assertFalse(createPath(14).closedNBEqualsFast(1, 6))
            assertFalse(createBipartite(6, 9).closedNBEqualsFast(0, 1))
        }
    }

    @Test
    fun addEdgeWithVertices_test() {
        val g = SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        assertTrue(g.addEdgeWithVertices(1, 2))
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.containsVertex(1) && g.containsVertex(2))
        assertFalse(g.addEdgeWithVertices(1, 2))
        assertTrue(g.containsEdge(1, 2))
    }

    @Nested
    internal inner class hasTriangle_Tests {

        @Test
        fun sampleGraph() = assertTrue(graphFromFile(GraphFile.Sample).hasTriangle())

        @Test
        fun customTree() = assertFalse(graphFromFile(GraphFile.CustomTree).hasTriangle())

        @Test   //reading in graph takes the longest time
        fun euroRoad() = assertTrue(graphFromFile(GraphFile.InfEuroRoad).hasTriangle()) //has 86 triangles

        @Test
        fun emptyGraph() = assertFalse(fromVertices<Int>().hasTriangle())

        @Test
        fun threeClique() = assertTrue(createClique(3).hasTriangle())

        @Test
        fun fourClique() = assertTrue(createClique(4).hasTriangle())

        @Test
        fun longPath() = assertFalse(createPath(10).hasTriangle())

        @Test
        fun star100() = assertFalse(createStar(100).hasTriangle())

        @Test
        fun circle3() = assertTrue(createCircle(3).hasTriangle())

        @Test
        fun circle86() = assertFalse(createCircle(86).hasTriangle())

        @Test
        fun oneEdge() = assertFalse(createPath(2).hasTriangle())
    }

    @Nested
    internal inner class getCopy_Test {

        @Test
        fun intGraph_test() {
            val g1 = graphFromFile(GraphFile.Sample)
            val g2 = g1.copy()
            assertTrue(g1.containsEdge(1, 2))
            g1.removeVertex(1)
            assertFalse(g1.containsEdge(1, 2))

            assertTrue(g2.containsEdge(1, 2))   //g2 is unaltered
        }


        @Test
        fun stringGraph_test() {
            val g1 = fromUnweightedEdges(listOf("Hund" to "Katze", "Hund" to "Giraffe"))
            val g2 = g1.copy()

            assertTrue(g1.containsEdge("Hund", "Giraffe"))
            g1.removeVertex("Hund")
            assertFalse(g1.containsEdge("Hund", "Giraffe"))

            assertTrue(g2.containsEdge("Hund", "Giraffe"))   //g2 is unaltered
        }
    }

    @Nested
    internal inner class degreeSequence {

        @Test
        fun clique10() = assertEquals(10, createClique(10).degreeSequence.count { it == 9 })

        @Test
        fun path36() {
            assertEquals(2, createPath(36).degreeSequence.count { it == 1 })
            assertEquals(34, createPath(36).degreeSequence.count { it == 2 })
        }

        @Test
        fun star29() {
            assertEquals(1, createStar(29).degreeSequence.count { it == 28 })
            assertEquals(28, createStar(29).degreeSequence.count { it == 1 })
        }

    }
    
    @Nested
    internal inner class ToggleEdge {

        @Test
        fun path3() {
            val g = createPath(4)
            assertEquals(3, g.edgeCount)
            g.toggleEdge(1, 2)
            assertFalse(g.containsEdge(1, 2))
            g.toggleEdge(1, 2)
            assertTrue(g.containsEdge(1,2))
        }

        @Test
        fun triangle() {
            val g = createClique(3)
            g.toggleEdge(0, 1)
            assertTrue(ConnectivityInspector(g).isConnected)
            g.toggleEdge(0, 2)
            assertFalse(ConnectivityInspector(g).isConnected)
        }
    }
}