package unitTests.core.graphAlgorithms

import de.umr.FilePaths
import de.umr.core.GraphAlgorithms.copyIntGraph
import de.umr.core.GraphAlgorithms.hasTriangle
import de.umr.core.GraphFileReader.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class graphAlgorithms_Test {

    @Nested
    internal inner class hasTriangle_Tests {

        @Test
        fun graphsFromNetworkRepo() {
            var g = graphFromFile(FilePaths.sample)
            assertTrue(hasTriangle(g))
            g = graphFromFile(".//graph_files//p-hat1500-3.mtx")
            assertTrue(hasTriangle(g))
        }

        @Test
        fun emptyGraph() {
            assertFalse(hasTriangle<Int>(SimpleGraph(DefaultEdge::class.java)))
        }

        @Test
        fun threeClique() {
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
    internal inner class copyIntGraph_Tests {
        @Test
        fun changeOnlyOnCopy() {
            val g1 = graphFromFile(FilePaths.sample)
            val g2 = copyIntGraph(g1)
            assertTrue(g1.containsEdge(1, 2))
            g1.removeVertex(1)
            assertFalse(g1.containsEdge(1, 2))

            assertTrue(g2.containsEdge(1, 2))   //g2 is unaltered
        }
    }
}