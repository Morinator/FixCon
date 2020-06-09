package unitTests.core.graphAlgorithms

import de.umr.FilePaths
import de.umr.core.addEdgeWithVertices
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.getCopy
import de.umr.core.graphFromFile
import de.umr.core.hasTriangle
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