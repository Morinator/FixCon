package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.random.Random

class SegmentedList_Test {

    private val fiveInts = SegmentedList<Int>().apply { this += listOf(3, 12, -5, 66, 0) }
    private var emptyInts = SegmentedList<Int>()
    private var animals = SegmentedList<String>().apply { this += listOf("Hund", "Katze", "Giraffe") }
    private var emptyStrings = SegmentedList<String>()

    @Nested
    internal inner class constructors {

        @Test
        fun emptyInts() {
            assertEquals(0, emptyInts.size)
            assertFalse(1 in emptyInts)
            assertEquals(emptyList<Int>(), emptyInts.listView)
            assertTrue(emptyInts.segmentSizes.isEmpty())
        }

        @Test
        fun emptyStrings() {
            assertEquals(0, emptyStrings.size)
            assertFalse("schubidu" in emptyStrings)
            assertEquals(emptyList<String>(), emptyStrings.listView)
        }
    }

    @Nested
    internal inner class illegalAccess {

        @Test
        fun accessOnEmptyList() {
            repeat(20) { assertThrows(IndexOutOfBoundsException::class.java) { emptyInts[Random.nextInt()] } }
            (-10..10).forEach { assertThrows(IndexOutOfBoundsException::class.java) { emptyInts[it] } }
        }

        @Test
        fun indexOutOfBounds_stringList() {
            assertThrows(IndexOutOfBoundsException::class.java) { animals[5] }
            assertThrows(IndexOutOfBoundsException::class.java) { animals[-1] }
        }

        @Test
        fun indexOutOfBounds_intList() {
            assertThrows(IndexOutOfBoundsException::class.java) { fiveInts[5] }
            assertThrows(IndexOutOfBoundsException::class.java) { fiveInts[-1] }
        }

        @Test
        fun removeLastSegment_CalledTooOften() {
            val runs = 10
            (0 until runs).forEach { emptyInts += it }
            repeat(runs) { emptyInts.removeLastSegment() }
            assertThrows(Exception::class.java) { emptyInts.removeLastSegment() }
        }

        @Test
        fun removeLastSegment_emptyList() {
            assertThrows(Exception::class.java) { emptyInts.removeLastSegment() }
        }
    }

    @Nested
    internal inner class Adding {

        @Test
        fun singleAdd() {
            emptyInts += 3

            assertTrue(3 in emptyInts)
            assertFalse(1 in emptyInts)
            assertEquals(1, emptyInts.size)
            assertEquals(listOf(3), emptyInts.listView)
            assertEquals(1, emptyInts.segmentSizes[0])
        }

        @Test
        fun repeatedSingleAdd() {
            val numElements = 5
            repeat(numElements) { emptyInts += 3 }

            assertTrue(3 in emptyInts)
            assertFalse(1 in emptyInts)

            assertEquals(numElements, emptyInts.size)
            assertEquals(List(numElements) { 3 }, emptyInts.listView)

            assertEquals(5, emptyInts.segmentSizes.last())
        }

        @Test
        fun rightInsertionOrder() {
            val l = SegmentedList<Char>()
            ('e' downTo 'a').forEach { l += it }

            assertEquals(listOf('e', 'd', 'c', 'b', 'a'), l.listView)
        }
    }

    @Nested
    internal inner class AddAll {

        @Test
        fun addAll() {
            emptyInts += listOf(11, 12, 13)

            assertEquals(3, emptyInts.size)
            assertEquals(listOf(11, 12, 13), emptyInts.listView)
            assertEquals(3, emptyInts.segmentSizes[0])

            emptyInts += listOf(1, 2)
            assertEquals(listOf(11, 12, 13, 1, 2), emptyInts.listView)
            assertEquals(5, emptyInts.segmentSizes[1])


            emptyInts.removeLastSegment()
            assertEquals(3, emptyInts.size)
        }

        @Test
        fun stringsHaveRightOrder() {
            assertEquals("Hund", animals[0])
            assertEquals("Katze", animals[1])
            assertEquals("Giraffe", animals[2])
        }

        @Test
        fun intsHaveRightOrder() {
            assertEquals(3, fiveInts[0])
            assertEquals(12, fiveInts[1])
            assertEquals(-5, fiveInts[2])
            assertEquals(66, fiveInts[3])
            assertEquals(0, fiveInts[4])
        }
    }

    @Nested
    internal inner class RemoveLastSegment {

        @Test
        fun ints_Multiple_AddAll() {
            emptyInts += listOf(11, 12, 13, 66)
            emptyInts += listOf(5, 4, 3)
            assertEquals(listOf(11, 12, 13, 66, 5, 4, 3), emptyInts.listView)

            emptyInts.removeLastSegment()
            assertEquals(listOf(11, 12, 13, 66), emptyInts.listView)

            emptyInts.removeLastSegment()
            assertEquals(emptyList<Int>(), emptyInts.listView)
            assertEquals(0, emptyInts.segmentSizes.size)

            emptyInts += listOf(7, 6, 5)
            emptyInts.removeLastSegment()
            assertEquals(emptyList<Int>(), emptyInts.listView)
            assertEquals(0, emptyInts.segmentSizes.size)
        }

        @Test
        fun string_Multiple_Add() {
            emptyStrings += "a"
            emptyStrings += "b"
            emptyStrings.removeLastSegment()
            assertEquals(listOf("a"), emptyStrings.listView)
        }
    }

    @Nested
    internal inner class Contains {

        @Test
        fun string_test() {
            assertTrue("Hund" in animals)
            assertFalse("Komodowaran" in animals)
            animals.removeLastSegment()
            assertEquals(0, animals.size)
        }
    }

    @Nested
    internal inner class ListView {

        @Test
        fun ints() {
            assertEquals(listOf(3, 12, -5, 66, 0), fiveInts.listView)
        }

        @Test
        fun strings() {
            assertEquals(listOf("Hund", "Katze", "Giraffe"), animals.listView)
        }
    }

    @Test
    fun manyAdditionsStillDoNothing() {
        repeat(20) { emptyInts += emptyList() }
        assertTrue(emptyInts.size == 0)

        repeat(20) { emptyInts.removeLastSegment() }
        assertTrue(emptyInts.size == 0)

        assertThrows(Exception::class.java) { emptyInts.removeLastSegment() }
    }
}