package unitTests.core.dataStructures

import de.umr.core.dataStructures.MultiStack
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MultiStack_Test {

    private val stack1 = MultiStack<Int>()

    @BeforeEach
    fun listSetup() {
        stack1.addAll(listOf(11, 12, 13, 66, 34))
    }

    @Test
    fun constructor() {
        val stack2 = MultiStack<Int>()
        assertEquals(0, stack2.size)
        assertFalse(stack2.contains(1))
    }

    @Test
    fun constructorFromCollection() {
        assertEquals(5, stack1.size)
        assertTrue(stack1.contains(11))
        assertTrue(stack1.contains(66))
        assertFalse(stack1.contains(1))
    }

    @Test
    fun add() {
        val stack2: MultiStack<Int> = MultiStack()
        stack2.add(3)
        assertTrue(stack2.contains(3))
        assertFalse(stack2.contains(1))
        stack2.add(3)
        stack2.removeLastSegment()
        assertEquals(1, stack2.size)
    }

    @Test
    fun addAll() {
        val stack2 = MultiStack<Int>()
        stack2.addAll(listOf(11, 12, 13))
        assertEquals(3, stack2.size)
        assertTrue(stack2.contains(11))
        assertFalse(stack2.contains(-11))
        stack2.addAll(listOf(1, 2))
        stack2.removeLastSegment()
        assertEquals(3, stack2.size)
    }

    @Test
    fun removeLastSegment() {
        val stack2 = MultiStack<Int>()
        stack2.addAll(listOf(11, 12, 13, 66))
        stack2.addAll(listOf(5, 4, 3))
        stack2.removeLastSegment()
        assertEquals(listOf(11, 12, 13, 66), stack2)
        stack2.removeLastSegment()
        assertEquals(emptyList<Int>(), stack2)
        stack2.addAll(listOf(7, 6, 5))
        stack2.removeLastSegment()
        assertEquals(emptyList<Int>(), stack2)
    }

    @Test
    fun set() {
        assertTrue(stack1.contains(11))
        stack1[0] = 11
        assertTrue(stack1.contains(11))
        stack1[0] = 67
        assertFalse(stack1.contains(11))
        assertTrue(stack1.contains(67))
    }
}