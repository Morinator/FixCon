package unitTests.core.graphAlgorithms

import de.umr.core.GraphAlgorithms.hasTriangle
import de.umr.core.GraphAlgorithms.inducedSubgraph
import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.core.StandardGraphFactory.createClique
import de.umr.core.StandardGraphFactory.createPath
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class graphAlgorithms_Test {

    @Nested
    internal inner class Triangle_Tests {

        @Test
        fun graphsFromNetworkRepo() {
            var g = graphFromNetworkRepo(".//graph_files//sample")
            assertTrue(hasTriangle(g))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertTrue(hasTriangle(g))
        }

        @Test
        fun emptyGraph() {
            assertFalse(hasTriangle<Int>(SimpleGraph(DefaultEdge::class.java)))
        }

        @Test
        fun oneTriangle() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            addEdgeWithVertices(g, 1, 2)
            addEdgeWithVertices(g, 1, 3)
            addEdgeWithVertices(g, 2, 3)
            assertTrue(hasTriangle(g))
        }

        @Test
        fun fourClique() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            for (i in 1..4)
                for (j in 1..4)
                    if (i != j)
                        addEdgeWithVertices(g, i, j)
            assertTrue(hasTriangle(g))
        }

        @Test
        fun longPath() {
            val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
            for (i in 1..10)
                addEdgeWithVertices(g, i, i + 1)
            assertFalse(hasTriangle(g))
        }
    }

    @Nested
    internal inner class inducedSubgraph_Tests {

        private val sampleGraph =graphFromNetworkRepo(".//graph_files//sample")

        @Test
        fun emptySubgraph() =
                assertEquals(0, inducedSubgraph(sampleGraph, emptySet()).vertexSet().size)

        @Test
        fun singleVertex() {
            assertEquals(1, inducedSubgraph(sampleGraph, setOf(1)).vertexSet().size)
            assertEquals(0, inducedSubgraph(sampleGraph, setOf(1)).edgeSet().size)
        }

        @Test
        fun triangleInFourClique() {
            val subgraph = inducedSubgraph(createClique(4), setOf(0,1,2))
            assertEquals(3, subgraph.vertexSet().size)
            assertTrue(subgraph.containsEdge(0, 1))
        }

        @Test
        fun onlyIsolatedVertices() {
            val subgraph = inducedSubgraph(createPath(10), setOf(0, 2, 4, 6, 8))
            assertEquals(5, subgraph.vertexSet().size)
            assertEquals(0, subgraph.edgeSet().size)
        }
    }
}