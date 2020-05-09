package unitTests.core.GraphAlgorithms

import de.umr.core.GraphAlgorithms.hasTriangle
import de.umr.core.GraphFileReader.graphFromNetworkRepo
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class hasTriangle_Test {

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
                    addEdgeWithVertices(g, i, i+1)
        assertFalse(hasTriangle(g))
    }
}