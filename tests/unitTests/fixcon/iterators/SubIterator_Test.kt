package unitTests.fixcon.iterators

import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

internal class SubIterator_Test {
    @Test
    fun test_7erFrankGraph() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        addEdgeWithVertices(g, 1, 4)
        addEdgeWithVertices(g, 2, 3)
        addEdgeWithVertices(g, 2, 5)
        addEdgeWithVertices(g, 3, 6)
        addEdgeWithVertices(g, 4, 5)
        addEdgeWithVertices(g, 5, 6)
        val subgraphIterator = SubIterator(CFCO_Problem(g, 2, EdgeCountFunction, emptyList()))
        val result: MutableSet<Set<Int>> = HashSet()
        while (subgraphIterator.isValid()) {
            result.add(HashSet(subgraphIterator.current().vertexSet()))
            subgraphIterator.mutate()
        }
        assertEquals(7, result.size)
        assertTrue(result.contains(setOf(1, 2)))
        assertTrue(result.contains(setOf(1, 4)))
        assertTrue(result.contains(setOf(2, 3)))
        assertTrue(result.contains(setOf(2, 5)))
        assertTrue(result.contains(setOf(4, 5)))
        assertTrue(result.contains(setOf(3, 6)))
        assertTrue(result.contains(setOf(5, 6)))
    }

    @Test
    fun test_size2() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        addEdgeWithVertices(g, 1, 4)
        addEdgeWithVertices(g, 2, 3)
        addEdgeWithVertices(g, 2, 5)
        addEdgeWithVertices(g, 3, 6)
        addEdgeWithVertices(g, 4, 5)
        addEdgeWithVertices(g, 5, 6)
        val sub_it_2 = SubIterator(CFCO_Problem(g, 2, EdgeCountFunction, emptyList()))
        val result_2: MutableSet<Set<Int>> = HashSet()
        while (sub_it_2.isValid()) {
            result_2.add(HashSet(sub_it_2.current().vertexSet()))
            sub_it_2.mutate()
        }
        assertEquals(7, result_2.size)
        assertTrue(result_2.contains(setOf(1, 2)))
        assertTrue(result_2.contains(setOf(1, 4)))
        assertTrue(result_2.contains(mutableSetOf(2, 3)))
        assertTrue(result_2.contains(setOf(2, 5)))
        assertTrue(result_2.contains(setOf(3, 6)))
        assertTrue(result_2.contains(setOf(4, 5)))
        assertTrue(result_2.contains(setOf(5, 6)))
    }
}