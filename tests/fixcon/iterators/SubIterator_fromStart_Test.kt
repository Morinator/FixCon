package fixcon.iterators

import de.umr.FilePaths
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class SubIterator_fromStart_Test {

    @Test
    fun mainTest() {
        val g=  VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        addEdgeWithVertices(g, 1, 4)
        addEdgeWithVertices(g, 2, 3)
        addEdgeWithVertices(g, 2, 5)
        addEdgeWithVertices(g, 3, 6)
        addEdgeWithVertices(g, 4, 5)
        addEdgeWithVertices(g, 5, 6)
        val sub_it_4 = SubIterator(CFCO_Problem(g, 4, EdgeCountFunction, emptyList()), 1)
        val result_4: MutableSet<Set<Int>> = HashSet()
        while (sub_it_4.isValid) {
            result_4.add(HashSet(sub_it_4.current.vertexSet()))
            sub_it_4.mutate()
        }
        assertEquals(6, result_4.size)
        assertTrue(setOf(1, 2, 4, 3) in result_4)
        assertTrue(setOf(1, 2, 4, 5) in result_4)
        assertTrue(setOf(1, 2, 3, 5) in result_4)
        assertTrue(setOf(1, 2, 3, 6) in result_4)
        assertTrue(setOf(1, 2, 5, 6) in result_4)
        assertTrue(setOf(1, 4, 5, 6) in result_4)
        val sub_it_2 = SubIterator(CFCO_Problem(g, 2, EdgeCountFunction, emptyList()), 1)
        val result_2: MutableSet<Set<Int>> = HashSet()
        while (sub_it_2.isValid) {
            result_2.add(HashSet(sub_it_2.current.vertexSet()))
            sub_it_2.mutate()
        }
        assertEquals(2, result_2.size)
        assertTrue(mutableSetOf(1, 2) in result_2)
        assertTrue(mutableSetOf(1, 4) in result_2)
    }

    @Test
    fun illegal_sizes() {
        val g=  VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        assertThrows(IllegalArgumentException::class.java) { SubIterator(CFCO_Problem(g, 1, EdgeCountFunction, emptyList()), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(CFCO_Problem(g, 0, EdgeCountFunction, emptyList()), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(CFCO_Problem(g, -1, EdgeCountFunction, emptyList()), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(CFCO_Problem(g, -100, EdgeCountFunction, emptyList()), 1) }
    }

    @Test
    fun targetSize_greaterThan_graphSize() {
        val g = graphFromFile(FilePaths.sample)
        val subIt25 = SubIterator(CFCO_Problem(g, 25, EdgeCountFunction, emptyList()), 1)
        var subgraphCounter = 0
        while (subIt25.isValid) {
            subgraphCounter++
            subIt25.mutate()
        }
        assertEquals(0, subgraphCounter)
    }
}