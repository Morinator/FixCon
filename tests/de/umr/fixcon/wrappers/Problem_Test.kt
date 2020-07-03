package de.umr.fixcon.wrappers

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Problem_Test {
    @Test
    fun constructor_test() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        var p = Problem(g, EdgeCountFunction(5))

        assertEquals(2, p.g.vertexSet().size)
        assertEquals(5, p.function.k)

        p = Problem(g, EdgeCountFunction(3))
        assertEquals(3, p.function.k)
    }
}