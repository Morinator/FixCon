package de.umr.core.extensions

import de.umr.core.dataStructures.GraphFile
import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.io.graphFromFile
import de.umr.fixcon.twins.vHashClosed
import de.umr.fixcon.twins.vHashOpen
import org.jgrapht.graph.AsSubgraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.PI

internal class Graphs_Test {

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
            val sub = VertexOrderedGraph.fromVertices(0)

            (2..9).forEach { sub.expandSubgraph(g, it) }

            //vertex 0 has no edge to any vertex of {2..9} in original
            assertEquals(0, sub.edgeCount)
            assertEquals(1, sub.vertexCount)
        }

        @Test
        fun centerOfStar() {
            val g = createStar(10)
            val sub = VertexOrderedGraph.fromVertices(1, 2, 3, 4, 5, 6, 7, 8, 9)
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
        assertEquals(0, VertexOrderedGraph<Int>().vertexCount)
        assertEquals(10, createClique(10).vertexCount)
        assertEquals(15, createCircle(15).vertexCount)
        assertEquals(17, graphFromFile(GraphFile.Sample).vertexCount) //17 is manually counted
        assertEquals(21, createPath(21).vertexCount)
        assertEquals(26, VertexOrderedGraph<Char>().apply { ('a'..'z').forEach { addVertex(it) } }.vertexCount)
        assertEquals(37, createStar(37).vertexCount)

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

        val g = graphFromFile(GraphFile.CustomTree)
        assertEquals(g.vertexCount, g.edgeCount + 1)
    }

    @Test
    fun weightOfEdge_test() {
        val g = VertexOrderedGraph.fromWeightedEdges(listOf(Triple(1, 2, 3.4), Triple(2, 4, 2.3)))
        assertEquals(3.4, g.weightOfEdge(1, 2))
        assertEquals(2.3, g.weightOfEdge(2, 4))
        assertThrows(NullPointerException::class.java) { g.weightOfEdge(1, 4) }
        assertThrows(NullPointerException::class.java) { g.weightOfEdge(0, 0) }
    }

    @Nested
    internal inner class addWeightedEdge_Tests {
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
            assertEquals(setOf(2, 3), createClique(5).openNB(setOf(0, 1, 4)))
            assertEquals(setOf(4, 8), createPath(10).openNB(setOf(5, 6, 7)))
            assertEquals(setOf(1, 3, 4, 6), createPath(10).openNB(setOf(2, 5)))
            assertEquals(setOf(4, 8), createCircle(10).openNB(setOf(5, 6, 7)))
            assertEquals(setOf(1, 9, 4, 6), createCircle(10).openNB(setOf(0, 5)))
            assertEquals(setOf(2, 4), createStar(5).openNB(setOf(0, 1, 3)))
            assertEquals(setOf(0), createStar(5).openNB(setOf(1, 2, 3, 4)))
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
            assertEquals(setOf(0, 1, 2, 3), createClique(4).closedNB(setOf(0, 1, 2, 3)))
            assertEquals(setOf(4, 5, 6, 7, 8), createPath(10).closedNB(setOf(5, 7)))
            assertEquals(setOf(1, 2, 3, 6, 7, 8), createCircle(10).closedNB(setOf(2, 7)))
            assertEquals(setOf(0, 1, 2, 9), createCircle(10).closedNB(setOf(0, 1)))
            assertEquals(setOf(0, 1, 2, 3, 4), createStar(5).closedNB(setOf(0, 1, 2, 3, 4)))
            assertEquals(setOf(0, 1, 3, 4), createStar(5).closedNB(setOf(1, 3, 4)))
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
        fun emptyGraph() = assertFalse(VertexOrderedGraph<Int>().hasTriangle())

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
            val g1 = VertexOrderedGraph<String>().apply { addEdgeWithVertices("Hund", "Katze"); addEdgeWithVertices("Hund", "Giraffe") }
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
    internal inner class vHashClosed {

        @Test
        fun clique10() {
            val g = createClique(10)
            val hash = vHashClosed(g, 0)
            (1..9).forEach { assertEquals(hash, vHashClosed(g, it)) }
        }

        @Test
        fun path2() = assertEquals(vHashClosed(createPath(2), 0), vHashClosed(createPath(2), 1))

        @Test
        fun hashStableOnRepetition() {
            val g = graphFromFile(GraphFile.InfEuroRoad)
            val hash = vHashClosed(g, 3)
            repeat(30) { assertEquals(hash, vHashClosed(g, 3)) }
        }

        @Test
        fun singleVertex() {
            val g = VertexOrderedGraph.fromVertices(5)
            assertEquals(vHashClosed(g, 5), vHashClosed(g, 5))
        }

        @Test
        fun clique3() = assertEquals(vHashClosed(createClique(3), 0), vHashClosed(createClique(3), 1))

        @Test
        fun path3() {
            val g = createPath(10)
            assertEquals(10, g.vertexSet().map { vHashClosed(g, it) }.distinct().count())
        }

        @Test
        fun charClique() {
            val g = VertexOrderedGraph<Char>()
            for (i in 'a'..'z')
                for (j in 'a'..'z')
                    if (i != j) g.addEdgeWithVertices(i, j)
            val hash = vHashClosed(g, 'a')
            ('b'..'z').forEach { assertEquals(hash, vHashClosed(g, it)) }
        }

        @Test
        fun triangleOfStrings() {
            val g = VertexOrderedGraph<String>().apply {
                addEdgeWithVertices("Hund", "Katze"); addEdgeWithVertices("Katze", "Pferd");addEdgeWithVertices("Pferd", "Hund")
            }
            assertEquals(vHashClosed(g, "Hund"), vHashClosed(g, "Katze"))
            assertEquals(vHashClosed(g, "Katze"), vHashClosed(g, "Pferd"))

        }
    }

    @Nested
    internal inner class vHashOpen {

        @Test
        fun path3() = assertEquals(vHashOpen(createPath(3), 0), vHashOpen(createPath(3), 2))

        @Test
        fun hashStableOnRepetition() {
            val g = graphFromFile(GraphFile.InfEuroRoad)
            val hash = vHashOpen(g, 3)
            repeat(30) { assertEquals(hash, vHashOpen(g, 3)) }
        }

        @Test
        fun singleVertex() {
            val g = VertexOrderedGraph.fromVertices(5)
            assertEquals(vHashOpen(g, 5), vHashOpen(g, 5))
        }

        @Test
        fun charGraph() {
            val g = VertexOrderedGraph.fromVertices('a', 'b')
            ('c'..'z').forEach { g.addEdgeWithVertices('a', it); g.addEdgeWithVertices('b', it) }
            assertEquals(vHashOpen(g, 'a'), vHashOpen(g, 'b'))
        }

        @Test
        fun path3_String() {
            val g = VertexOrderedGraph<String>().apply { addEdgeWithVertices("Hund", "Katze"); addEdgeWithVertices("Katze", "Pferd") }
            assertEquals(vHashOpen(g, "Hund"), vHashOpen(g, "Pferd"))
        }

        @Test
        fun twoVerticesConnectedWithClique() {
            val g = createClique(20)
            (0 until 20).forEach { g.addEdgeWithVertices(it, 100); g.addEdgeWithVertices(it, 101) }
            assertEquals(vHashOpen(g, 100), vHashOpen(g, 101))
        }

        @Test
        fun openHashDifferentInClique() {
            val g = createClique(20)
            assertEquals(20, g.vertexSet().map { vHashOpen(g, it) }.distinct().count())
        }
    }

}