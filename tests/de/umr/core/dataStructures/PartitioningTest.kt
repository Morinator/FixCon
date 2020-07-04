package de.umr.core.dataStructures

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

internal class PartitioningTest {

    private val x = Partitioning<Int>()
    private val y = Partitioning<String>()

    @Test
    fun emptyAtStart() {
        assertEquals(0, x.allElements.size)
        repeat(20) { assertFalse(x.contains(nextInt()))}
    }

    @Test
    fun contains() {
        y.addInNewPart("bli")
        y.addInNewPart("bla")
        y.addInNewPart("blub")
        assertTrue("bli" in y.allElements)
        assertFalse("Knoblauch" in y.allElements)
    }

    @Test
    fun getPart() {
        x.addInNewPart(1)
        x.addToPart(1, 2)
        x.addInNewPart(3)

        assertSame(x.getPart(1), x.getPart(2))
        assertEquals(x.getPart(1), setOf(1, 2))

        assertEquals(x.getPart(3), setOf(3))
    }
}