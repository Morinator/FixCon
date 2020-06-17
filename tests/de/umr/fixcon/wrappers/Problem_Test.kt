package de.umr.fixcon.wrappers

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Problem_Test {
    @Test
    fun constructor_test() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        var p = Problem(g, 5, EdgeCountFunction)

        assertEquals(2, p.g.vertexSet().size)
        assertEquals(5, p.k)
        assertEquals(0, p.args.size)


        p = Problem(g, 3, EdgeCountFunction, listOf(1, 2, 3, 4, 5))
        assertEquals(3, p.k)
        assertEquals(5, p.args.size)
    }
}