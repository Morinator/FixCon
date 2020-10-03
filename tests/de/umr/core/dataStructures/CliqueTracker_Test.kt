package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CliqueTracker_Test {

    private val ct0 = CliqueTracker(0)
    private val ct1 = CliqueTracker(1)
    private val ct10 = CliqueTracker(10)
    private val ct80 = CliqueTracker(80)

    @Nested
    internal inner class Adding {

        @Test
        fun addVertex() {
            ct1.addVertex(4)
            assertEquals(0, ct1.cliqueSize)
            assertEquals(1, ct1.totalSize)
            assertEquals(setOf(4), ct1.vertexSet())
        }

        @Test
        fun addVertexException() {
            assertThrows(IllegalArgumentException::class.java) { ct1.addVertex(-44) }
        }

        @Test
        fun exceptionOnTooManyAdditions() {
            ct1.addVertex(4)
            assertThrows(IllegalArgumentException::class.java) { ct1.addVertex(23) }
        }
    }

    @Nested
    internal inner class Removal {

        @Test
        fun remove1() {
            ct10.addVertex(4)
            ct10.removeVertex(4)
            assertEquals(10, ct10.cliqueSize)
            assertEquals((-1 downTo -10).toSet(), ct10.vertexSet())
        }
    }

    @Test
    fun removeVertex() {
    }

    @Test
    fun getSize() {
        assertEquals(0, ct0.totalSize)
        assertEquals(1, ct1.totalSize)
        assertEquals(10, ct10.totalSize)
        assertEquals(80, ct80.totalSize)
    }

    @Test
    fun initialization_test() {
        assertEquals(emptySet<Int>(), ct0.vertexSet())
        assertEquals(setOf(-1), ct1.vertexSet())
        assertEquals((-1 downTo -10).toSet(), ct10.vertexSet())
        assertEquals((-1 downTo -80).toSet(), ct80.vertexSet())
    }

    @Test
    fun onlyPositiveSizes() {
        assertThrows(IllegalArgumentException::class.java) { CliqueTracker(-123) }
    }
}