package unitTests.core.dataStructures

import de.umr.core.dataStructures.SegmentedList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SegmentedList_Test {

    private val x = SegmentedList<Int>()
    private var x2 = SegmentedList<Int>()
    private var animals = SegmentedList(listOf("Hund", "Katze", "Giraffe"))

    @BeforeEach
    fun listSetup() {
        x.addAll(listOf(11, 12, 13, 66, 34))
    }

    @Test
    fun constructor() {
        assertEquals(0, x2.size)
        assertFalse(x2.contains(1))
    }

    @Test
    fun illegalAccess_test() {
        assertThrows(IndexOutOfBoundsException::class.java) { x2[0] }
        assertThrows(NoSuchElementException::class.java) { x2.removeLastSegment() }
    }

    @Test
    fun constructorFromCollection() {
        assertEquals(5, x.size)
        assertTrue(x.contains(11))
        assertTrue(x.contains(66))
        assertFalse(x.contains(1))
    }

    @Test
    fun add() {
        x2.add(3)
        assertTrue(x2.contains(3))
        assertFalse(x2.contains(1))
        x2.add(3)
        assertEquals(2, x2.size)
        x2.removeLastSegment()
        assertEquals(1, x2.size)
    }

    @Test
    fun addAll() {
        x2.addAll(listOf(11, 12, 13))
        assertEquals(3, x2.size)
        assertTrue(x2.contains(11))
        assertFalse(x2.contains(-11))
        x2.addAll(listOf(1, 2))
        x2.removeLastSegment()
        assertEquals(3, x2.size)

        x2 = SegmentedList()
        x2.addAll(listOf(11, 12, 13))
        assertEquals(3, x2.size)
        assertTrue(x2.contains(11))
        assertFalse(x2.contains(-11))
        x2.addAll(listOf(1, 2))
        x2.removeLastSegment()
        assertEquals(3, x2.size)
    }

    @Test
    fun removeLastSegment() {
        x2.addAll(listOf(11, 12, 13, 66))
        x2.addAll(listOf(5, 4, 3))
        x2.removeLastSegment()
        assertEquals(listOf(11, 12, 13, 66), x2.list)
        x2.removeLastSegment()
        assertEquals(emptyList<Int>(), x2.list)
        x2.addAll(listOf(7, 6, 5))
        x2.removeLastSegment()
        assertEquals(emptyList<Int>(), x2.list)
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
    fun constructorRetainsOrder_test() {
        assertEquals("Hund", animals[0])
        assertEquals("Katze", animals[1])
        assertEquals("Giraffe", animals[2])
    }

    @Test
    fun manyAdditionsStillDoNothing() {
        repeat(20) { x2.addAll(emptyList()) }
        assertTrue(x2.size == 0)
        repeat(20) { x2.removeLastSegment() }
        assertTrue(x2.size == 0)
    }
}