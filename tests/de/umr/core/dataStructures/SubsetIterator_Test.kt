package de.umr.core.dataStructures

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class SubsetIterator_Test {

    @Test
    fun size3() {
        val res = mutableSetOf<Set<Int>>()
        val i = SubsetIterator(setOf(1, 2, 3))
        while (i.hasNext()) res.add(i.next().toSet())
        assertEquals(setOf(setOf(), setOf(1), setOf(2), setOf(3), setOf(1, 2), setOf(2, 3), setOf(1, 3), setOf(1, 2, 3)), res)
    }

    @Test
    fun size10() {
        val res = mutableSetOf<Set<Int>>()
        val i = SubsetIterator((0 until 10).toSet())
        while (i.hasNext()) res.add(i.next().toSet())
        assertEquals(2 pow 10, res.size)
    }
}