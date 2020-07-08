package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.GraphFile
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.io.graphFromFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RRegularFunction_Test {

    @Test
    fun is_N_regular_Test_Small() {
        val g = VertexOrderedGraph.fromUnweightedEdges(listOf(1 to 2, 1 to 3, 2 to 3))
        assertEquals(0, RRegularFunction(listOf(2), dummyK).eval(g))
    }

    @Test
    fun is_N_regular_Test_Big() {
        assertEquals(0, RRegularFunction(listOf(848), dummyK).eval(graphFromFile(GraphFile.Hamming10_4)))
    }

    @Test
    fun infPower() = kotlin.test.assertTrue(RRegularFunction(listOf(3), dummyK).eval(graphFromFile(GraphFile.InfPower)) < 0)

}