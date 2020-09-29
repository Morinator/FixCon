package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SetStack_Test {

    private val empty = SetStack<Int>()
    private val one = SetStack<Int>()
    private val three = SetStack<Int>()

    @BeforeEach
    fun setup() {
        empty.clear()

        one.clear()
        one.push(setOf(1, 5, 13))

        three.clear()
        three.push(setOf(1, 5))
        three.push(setOf(7, 123))
        three.push(setOf(6, 4, 2, 11, 57, 43))
    }

    @Test
    fun contains() {
        assertTrue(1 in one)
        assertTrue(5 in one)
        assertFalse(11 in one)

        assertTrue(7 in three)
        assertFalse(-1 in three)
    }

    @Test
    fun getSize() {
        assertEquals(0, empty.size)
        assertEquals(3, one.size)
        assertEquals(10, three.size)
    }

    @Test
    fun addToLast() {
        one.addToLast(setOf(-23))
        assertEquals(4, one.size)

        three.addToLast(setOf(-3, -5, -65))
        assertEquals(13, three.size)
    }

    @Test
    fun push() {
        one.push(setOf(-3, -96))
        assertEquals(2, one.stackSize)
        assertEquals(5, one.size)
    }

    @Test
    fun removeLast() {
        one.removeLast()
        assertEquals(0, one.size)
        assertEquals(0, one.stackSize)

        three.removeLast()
        assertEquals(2, three.stackSize)
        repeat(2) { three.removeLast()}
        assertEquals(0, three.stackSize)
    }

    @Test
    fun noExceptionIfRemovalOnEmptyObject() {
        repeat(2) {one.removeLast()}    //should just do nothing the second time and not throw an exception
    }
}