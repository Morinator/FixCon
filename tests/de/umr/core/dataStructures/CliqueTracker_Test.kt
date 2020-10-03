package de.umr.core.dataStructures

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CliqueTracker_Test {

    private val ct1 = CliqueTracker(1)
    val ct3 = CliqueTracker(3)
    val ct10 = CliqueTracker(10)
    private val ct500 = CliqueTracker(500)

    @Test
    fun addVertex() {
    }

    @Test
    fun removeVertex() {
    }

    @Test
    fun getSize() {
        assertEquals(1, ct1.size)
        assertEquals(500, ct500.size)
    }

    @Test
    fun initialization_test() {
        assertEquals(setOf(-1), ct1.vertexSet())
        assertEquals((-1 downTo -500).toSet(), ct500.vertexSet())
    }
}