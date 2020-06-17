package de.umr.fixcon.iterators

import de.umr.FilePaths.Sample
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class SubIterator_fromStart_Test {

    @Test
    fun illegal_sizes() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        assertThrows(IllegalArgumentException::class.java) { SubIterator(Problem(g, 1, EdgeCountFunction), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(Problem(g, 0, EdgeCountFunction), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(Problem(g, -1, EdgeCountFunction), 1) }
        assertThrows(IllegalArgumentException::class.java) { SubIterator(Problem(g, -100, EdgeCountFunction), 1) }
    }

    @Test
    fun targetSize_greaterThan_graphSize() {
        val g = graphFromFile(Sample)
        val subIt25 = SubIterator(Problem(g, 25, EdgeCountFunction), 1)
        var subgraphCounter = 0
        while (subIt25.isValid) {
            subgraphCounter++
            subIt25.mutate()
        }
        assertEquals(0, subgraphCounter)
    }
}