package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OrderedGraphTest {

    val g = OrderedGraph<Int>()

    @Test
    fun addVertex() {
        g.addVertex(1)
        assertEquals(1, g.vertexCount)
    }

    @Test
    fun removeLastVertex() {
        g.addVertex(1)
        g.addVertex(2)
        assertEquals(setOf(1, 2), g.vertexSet())
        g.removeLastVertex()
        assertEquals(1, g.vertexCount)
        g.removeLastVertex()
        assertEquals(0, g.vertexCount)
    }
}