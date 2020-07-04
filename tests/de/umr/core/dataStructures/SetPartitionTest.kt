package de.umr.core.dataStructures

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import java.lang.Exception
import kotlin.random.Random.Default.nextInt

internal class SetPartitionTest {

    private val ints = SetPartition<Int>()
    private val strings = SetPartition<String>()

    @Nested
    internal inner class Constructor {
        @Test
        fun emptyAtStart_Int() {
            assertEquals(0, ints.elements.size)
            repeat(20) { assertFalse(ints.contains(nextInt())) }
        }

        @Test
        fun emptyAtStart_String() {
            assertEquals(0, strings.elements.size)
            assertFalse("BiBaButzemann" in strings)
        }
    }

    @Nested
    internal inner class Contains {

        @Test
        fun containsString() {
            strings.addInNewSubset("bli")
            strings.addInNewSubset("bla")
            strings.addToSubset("bli", "blub")

            assertTrue("bli" in strings)
            assertTrue("blub" in strings)
            assertFalse("Knoblauch" in strings)
        }

        @Test
        fun containsInt() {
            ints.addInNewSubset(1)
            ints.addToSubset(1,2)
            ints.addToSubset(2, 3)
            ints.addInNewSubset(4)

            assertTrue(1 in ints)
            assertTrue(2 in ints)
            assertTrue(3 in ints)
            assertTrue(4 in ints)

            assertFalse(-1 in ints)
            assertFalse(5 in ints)
        }

    }

    @Test
    fun subset() {
        ints.addInNewSubset(1)
        ints.addToSubset(1, 2)
        ints.addInNewSubset(3)

        assertSame(ints.subset(1), ints.subset(2))
        assertEquals(ints.subset(1), setOf(1, 2))
        assertEquals(ints.subset(3), setOf(3))
    }

    @Test
    fun subset_onlySize1Sets() {
        ints.addInNewSubset(1)
        ints.addInNewSubset(2)
        ints.addInNewSubset(3)
        assertEquals(1, ints.subset(1).size)
        assertEquals(1, ints.subset(2).size)
        assertEquals(1, ints.subset(3).size)
    }

    @Test
    fun addToSubset() {
        ints.addInNewSubset(1)
        ints.addToSubset(1, 2)
        ints.addInNewSubset(3)

        assertFalse(ints.addToSubset(1, 1))
        assertFalse(ints.addToSubset(1, 2))
        assertFalse(ints.addToSubset(1, 3))

        assertTrue(ints.addToSubset(2, 4))
    }

    @Test
    fun addToSubset_ExceptionIfOldValueNotPresent() {
        assertThrows(Exception::class.java) { ints.addToSubset(1, 2) }
    }

    @Test
    fun addInNewSubset() {
        assertTrue(ints.addInNewSubset(1))
        assertFalse(ints.addInNewSubset(1))

        assertTrue(ints.addInNewSubset(2))
        assertFalse(ints.addInNewSubset(1))
        assertFalse(ints.addInNewSubset(2))

        assertTrue(ints.addToSubset(2, 3))
        assertFalse(ints.addInNewSubset(3))
    }


}