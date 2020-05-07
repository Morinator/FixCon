package unitTests.fixcon.iterators

import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.fixcon.itarators.SubIteratorFromStart
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class SubIterator_fromStart_Test {
    @Test
    fun mainTest() {
        val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
        addEdgeWithVertices(g, 1, 2)
        addEdgeWithVertices(g, 1, 4)
        addEdgeWithVertices(g, 2, 3)
        addEdgeWithVertices(g, 2, 5)
        addEdgeWithVertices(g, 3, 6)
        addEdgeWithVertices(g, 4, 5)
        addEdgeWithVertices(g, 5, 6)
        val sub_it_4 = SubIteratorFromStart(g, 1, 4)
        val result_4: MutableSet<Set<Int>> = HashSet()
        while (sub_it_4.isValid()) {
            result_4.add(HashSet(sub_it_4.current().vertexSet()))
            sub_it_4.mutate()
        }
        assertEquals(6, result_4.size)
        assertTrue(result_4.contains(setOf(1, 2, 4, 3)))
        assertTrue(result_4.contains(setOf(1, 2, 4, 5)))
        assertTrue(result_4.contains(setOf(1, 2, 3, 5)))
        assertTrue(result_4.contains(setOf(1, 2, 3, 6)))
        assertTrue(result_4.contains(setOf(1, 2, 5, 6)))
        assertTrue(result_4.contains(setOf(1, 4, 5, 6)))
        val sub_it_2 = SubIteratorFromStart(g, 1, 2)
        val result_2: MutableSet<Set<Int>> = HashSet()
        while (sub_it_2.isValid()) {
            result_2.add(HashSet(sub_it_2.current().vertexSet()))
            sub_it_2.mutate()
        }
        assertEquals(2, result_2.size)
        assertTrue(result_2.contains(mutableSetOf(1, 2)))
        assertTrue(result_2.contains(mutableSetOf(1, 4)))
    }

    @Test
    fun illegal_sizes() {
        val g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
        addEdgeWithVertices(g, 1, 2)
        assertThrows(IllegalArgumentException::class.java) { SubIteratorFromStart(g, 1, 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIteratorFromStart(g, 1, 0) }
        assertThrows(IllegalArgumentException::class.java) { SubIteratorFromStart(g, 1, -1) }
        assertThrows(IllegalArgumentException::class.java) { SubIteratorFromStart(g, 1, -100) }
    }

    @Test
    fun targetSize_greaterThan_graphSize() {
        val g = graphFromNetworkRepo(".//graph_files//sample")
        val subIt25 = SubIteratorFromStart(g, 1, 25)
        var subgraphCounter = 0
        while (subIt25.isValid()) {
            subgraphCounter++
            subIt25.mutate()
        }
        assertEquals(0, subgraphCounter)
    }
}