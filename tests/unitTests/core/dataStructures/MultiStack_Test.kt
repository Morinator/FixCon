package unitTests.core.dataStructures

import de.umr.core.dataStructures.MultiStack
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MultiStack_Test {

    private val x = MultiStack<Int>()

    @BeforeEach
    fun listSetup() {
        x.addAll(listOf(11, 12, 13, 66, 34))
    }

    @Test
    fun constructor() {
        val x = MultiStack<Int>()
        assertEquals(0, x.size)
        assertFalse(x.contains(1))
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
        val x: MultiStack<Int> = MultiStack()
        x.add(3)
        assertTrue(x.contains(3))
        assertFalse(x.contains(1))
        x.add(3)
        x.removeLastSegment()
        assertEquals(1, x.size)
    }

    @Test
    fun addAll() {
        val y = MultiStack<Int>()
        y.addAll(listOf(11, 12, 13))
        assertEquals(3, y.size)
        assertTrue(y.contains(11))
        assertFalse(y.contains(-11))
        y.addAll(listOf(1, 2))
        y.removeLastSegment()
        assertEquals(3, y.size)
    }

    @Test
    fun removeLastSegment() {
        val x = MultiStack<Int>()
        x.addAll(listOf(11, 12, 13, 66))
        x.addAll(listOf(5, 4, 3))
        x.removeLastSegment()
        assertEquals(listOf(11, 12, 13, 66), x)
        x.removeLastSegment()
        assertEquals(emptyList<Int>(), x)
        x.addAll(listOf(7, 6, 5))
        x.removeLastSegment()
        assertEquals(emptyList<Int>(), x)
    }

    @Test
    fun set() {
        assertTrue(x.contains(11))
        x[0] = 11
        assertTrue(x.contains(11))
        x[0] = 67
        assertFalse(x.contains(11))
        assertTrue(x.contains(67))
    }
}