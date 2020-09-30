package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CliqueTrackerTest {

    @Test
    fun main_test() {
        val ct = CliqueTracker(5)

        assertEquals(setOf(-1, -2, -3, -4, -5), ct.vertexSet())
        assertEquals(10, ct.edgeCount)

        ct.addVertex(7)
        assertEquals(setOf(-1, -2, -3, -4, 7), ct.vertexSet())
        assertEquals(6, ct.edgeCount)

        ct.addEdgeWithVertices(7, 13)
        assertEquals(setOf(-1, -2, -3, 7, 13), ct.vertexSet())
        assertEquals(4, ct.edgeCount)

        ct.removeVertex(13)
        assertEquals(setOf(-1, -2, -3, -4, 7), ct.vertexSet())
        assertEquals(6, ct.edgeCount)

        ct.removeVertex(7)
        assertEquals(setOf(-1, -2, -3, -4, -5), ct.vertexSet())
        assertEquals(10, ct.edgeCount)
    }
}