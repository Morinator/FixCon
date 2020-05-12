package unitTests.fixcon.wrappers

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class CFCO_Problem_Test {
    @Test
    fun constructor_test() {
        val g = VertexOrderedGraph<Int>()
        addEdgeWithVertices(g, 1, 2)
        var p = CFCO_Problem(g, 5, EdgeCountFunction, ArrayList())

        assertEquals(2, p.originalGraph.vertexSet().size)
        assertEquals(5, p.targetSize)
        assertEquals(0, p.parameters.size)


        p = CFCO_Problem(g, 3, EdgeCountFunction, listOf(1, 2, 3, 4, 5))
        assertEquals(3, p.targetSize)
        assertEquals(5, p.parameters.size)
    }
}