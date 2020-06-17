package de.umr.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GraphFactory_Test {

    @Test
    fun createClique() {
        assertThrows(IllegalArgumentException::class.java) { createClique(1) }

        var g = createClique(3)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(0, 2))
        assertTrue(g.containsEdge(1, 2))
        assertFalse(g.containsEdge(1, 3))

        g = createClique(4)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(0, 2))
        assertTrue(g.containsEdge(0, 3))
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.containsEdge(1, 3))
        assertTrue(g.containsEdge(2, 3))
        assertFalse(g.containsEdge(1, 4))
        assertFalse(g.containsEdge(1, 123))
    }

    @Test
    fun createCircle() {
        assertThrows(IllegalArgumentException::class.java) { createCircle(1) }

        var g = createCircle(3)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.containsEdge(2, 0))
        assertFalse(g.containsEdge(1, 3))

        g = createCircle(4)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.containsEdge(2, 3))
        assertTrue(g.containsEdge(3, 0))
        assertFalse(g.containsEdge(1, 4))
        assertFalse(g.containsEdge(1, 123))
    }

    @Test
    fun createPath() {
        assertThrows(IllegalArgumentException::class.java) { createPath(1) }

        var g = createPath(3)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(1, 2))
        assertFalse(g.containsEdge(2, 0))
        assertFalse(g.containsEdge(1, 3))

        g = createPath(4)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(1, 2))
        assertTrue(g.containsEdge(2, 3))
        assertFalse(g.containsEdge(3, 0))
        assertFalse(g.containsEdge(1, 4))
        assertFalse(g.containsEdge(1, 123))
    }

    @Test
    fun createStar() {
        assertThrows(IllegalArgumentException::class.java) { createStar(1) }

        var g = createStar(3)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(0, 2))
        assertFalse(g.containsEdge(1, 2))
        assertFalse(g.containsEdge(0, 3))

        g = createStar(4)
        assertTrue(g.containsEdge(0, 1))
        assertTrue(g.containsEdge(0, 2))
        assertTrue(g.containsEdge(0, 3))
        assertFalse(g.containsEdge(1, 3))
        assertFalse(g.containsEdge(0, 4))
        assertFalse(g.containsEdge(1, 123))
    }
}