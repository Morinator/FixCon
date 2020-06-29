package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SegmentedList_Test {

    private val fiveInts = SegmentedList(listOf(11, 12, 13, 66, 34))
    private var intList = SegmentedList<Int>()
    private var animals = SegmentedList(listOf("Hund", "Katze", "Giraffe"))

    @Nested
    internal inner class constructors {
        @Test
        fun constructor_test() {
            assertEquals(0, intList.size)
            assertEquals(0, SegmentedList<Char>().size)
            assertFalse(1 in intList)
        }

        @Test
        fun constructorFromCollection() {
            assertEquals(5, fiveInts.size)
            assertTrue(11 in fiveInts)
            assertTrue(66 in fiveInts)
            assertFalse(1 in fiveInts)
            fiveInts.removeLastSegment()    //all stored in one segment
            assertEquals(0, fiveInts.size)
        }

        @Test
        fun constructorRetainsOrder_test() {
            assertEquals("Hund", animals[0])
            assertEquals("Katze", animals[1])
            assertEquals("Giraffe", animals[2])
        }
    }

    @Nested
    internal inner class illegalAccess {

        @Test
        fun illegalAccess_emptyList() {
            assertThrows(IndexOutOfBoundsException::class.java) { intList[0] }
            assertThrows(IndexOutOfBoundsException::class.java) { intList[-10] }
            assertThrows(IndexOutOfBoundsException::class.java) { intList[100] }
        }

        @Test
        fun removeLastSegment_emptyList() {
            assertThrows(NoSuchElementException::class.java) { intList.removeLastSegment() }
        }

        @Test
        fun indexOutOfBounds() {
            assertThrows(IndexOutOfBoundsException::class.java) { animals[5] }
        }
    }

    @Test
    fun add() {
        intList.add(3)
        assertTrue(3 in intList)
        assertFalse(1 in intList)

        intList.add(3)
        assertEquals(2, intList.size)
        assertTrue(3 in intList)

        intList.removeLastSegment()
        assertEquals(1, intList.size)
        assertTrue(3 in intList)
    }

    @Test
    fun addAll() {
        intList.addAll(listOf(11, 12, 13))
        assertEquals(3, intList.size)
        assertTrue(11 in intList)
        assertFalse(-11 in intList)
        assertEquals(listOf(11, 12, 13), intList.listView)

        intList.addAll(listOf(1, 2))
        intList.removeLastSegment()
        assertEquals(3, intList.size)
    }

    @Test
    fun removeLastSegment() {
        intList.addAll(listOf(11, 12, 13, 66))
        intList.addAll(listOf(5, 4, 3))
        intList.removeLastSegment()
        assertEquals(listOf(11, 12, 13, 66), intList.listView)
        intList.removeLastSegment()
        assertEquals(emptyList<Int>(), intList.listView)
        intList.addAll(listOf(7, 6, 5))
        intList.removeLastSegment()
        assertEquals(emptyList<Int>(), intList.listView)
    }

    @Test
    fun string_test() {
        assertTrue("Hund" in animals)
        assertFalse("Komodowaran" in animals)
        assertEquals(3, animals.size)
        animals.removeLastSegment()
        assertEquals(0, animals.size)
    }

    @Test
    fun manyAdditionsStillDoNothing() {
        repeat(20) { intList.addAll(emptyList()) }
        assertTrue(intList.size == 0)
        repeat(20) { intList.removeLastSegment() }
        assertTrue(intList.size == 0)
        assertThrows(NoSuchElementException::class.java) { intList.removeLastSegment() }
    }
}