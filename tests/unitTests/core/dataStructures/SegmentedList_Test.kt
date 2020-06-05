package unitTests.core.dataStructures

import de.umr.core.dataStructures.SegmentedList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SegmentedList_Test {

    private val x = SegmentedList<Int>()

    @BeforeEach
    fun listSetup() {
        x.addAll(listOf(11, 12, 13, 66, 34))
    }

    @Test
    fun constructor() {
        val x2 = SegmentedList<Int>()
        assertEquals(0, x2.size)
        assertFalse(x2.contains(1))
    }

    @Test
    fun illegalAccess_test() {
        val x2 = SegmentedList<Int>()
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
        val x2 = SegmentedList<Int>()
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
        var x2 = SegmentedList<Int>()
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
        val x2 = SegmentedList<Int>()
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
        val x2 = SegmentedList(listOf("Hund", "Katze", "Giraffe"))
        assertTrue("Hund" in x2)
        assertFalse("Komodowaran" in x2)
        assertEquals(3, x2.size)
        x2.removeLastSegment()
        assertEquals(0, x2.size)
    }
    
    @Test
    fun constructorRetainsOrder_test() {
        val x2 = SegmentedList(listOf("Hund", "Katze", "Giraffe"))
        assertEquals("Hund", x2[0])
        assertEquals("Katze", x2[1])
        assertEquals("Giraffe", x2[2])
    }

    @Test
    fun manyAdditionsStillDoNothing() {
        val x2 = SegmentedList<String>()
        repeat(20){ x2.addAll(emptyList())}
        assertTrue(x2.size == 0)
        repeat(20) { x2.removeLastSegment()}
        assertTrue(x2.size == 0)
    }
}