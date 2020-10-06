package de.umr.core

import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vCount
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Factory_Test {

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

    @Nested
    internal inner class completeBipartiteTest {

        @Test
        fun illegalSizes() {
            assertThrows(IllegalArgumentException::class.java) {createBipartite(0, 0)}
        }

        @Test
        fun size_1_1() {
            val g = createBipartite(1, 1)
            assertEquals(2, g.vCount)
            assertEquals(1, g.edgeCount)
            assertTrue(g.containsEdge(0, 1))
        }

        @Test
        fun size_2_4() {
            val g = createBipartite(2, 4)
            assertEquals(8, g.edgeCount)
            assertTrue(g.containsEdge(0, 2))
            assertTrue(g.containsEdge(0, 3))
            assertTrue(g.containsEdge(0, 4))
            assertTrue(g.containsEdge(0, 5))
            assertFalse(g.containsEdge(0, 1))
            assertFalse(g.containsEdge(0, 6))
            (0 until 2).forEach { assertEquals(4, g.degreeOf(it)) }
            (2 until 6).forEach { assertEquals(2, g.degreeOf(it)) }
        }

        @Test
        fun size_99_100() {
            val g = createBipartite(99, 100)
            assertEquals(9900, g.edgeCount)
            assertEquals(199, g.vCount)
            assertTrue(g.containsEdge(98, 101))
            assertTrue(g.containsEdge(54, 179))
            assertTrue(g.containsEdge(3, 198))
            assertFalse(g.containsEdge(45, 199))
            assertFalse(g.containsEdge(136, 199))

        }

    }
}