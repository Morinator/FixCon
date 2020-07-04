package de.umr.core.dataStructures

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.Exception
import kotlin.random.Random.Default.nextInt

internal class PartitioningTest {

    private val x = Partitioning<Int>()
    private val y = Partitioning<String>()

    @Test
    fun emptyAtStart() {
        assertEquals(0, x.elements.size)
        repeat(20) { assertFalse(x.contains(nextInt())) }
    }

    @Test
    fun contains() {
        y.addInNewSubset("bli")
        y.addInNewSubset("bla")
        y.addToSubset("bli", "blub")
        assertTrue("bli" in y.elements)
        assertTrue("blub" in y.elements)
        assertFalse("Knoblauch" in y.elements)
    }

    @Test
    fun subset() {
        x.addInNewSubset(1)
        x.addToSubset(1, 2)
        x.addInNewSubset(3)

        assertSame(x.subset(1), x.subset(2))
        assertEquals(x.subset(1), setOf(1, 2))
        assertEquals(x.subset(3), setOf(3))
    }

    @Test
    fun subset_onlySize1Sets() {
        x.addInNewSubset(1)
        x.addInNewSubset(2)
        x.addInNewSubset(3)
        assertEquals(1, x.subset(1).size)
        assertEquals(1, x.subset(2).size)
        assertEquals(1, x.subset(3).size)
    }

    @Test
    fun addToSubset() {
        x.addInNewSubset(1)
        x.addToSubset(1, 2)
        x.addInNewSubset(3)

        assertFalse(x.addToSubset(1, 1))
        assertFalse(x.addToSubset(1, 2))
        assertFalse(x.addToSubset(1, 3))

        assertTrue(x.addToSubset(2, 4))
    }

    @Test
    fun addToSubset_ExceptionIfOldValueNotPresent() {
        assertThrows(Exception::class.java) {x.addToSubset(1,2)}
    }

    @Test
    fun addInNewSubset() {
        assertTrue(x.addInNewSubset(1))
        assertFalse(x.addInNewSubset(1))

        assertTrue(x.addInNewSubset(2))
        assertFalse(x.addInNewSubset(1))
        assertFalse(x.addInNewSubset(2))

        assertTrue(x.addToSubset(2, 3))
        assertFalse(x.addInNewSubset(3))
    }
}