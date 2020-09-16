package de.umr.core.dataStructures

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class BitFlipIterator_Test {

    @Test
    fun twentyElements() {
        val l = mutableListOf<Set<Int>>()
        val i = BitFlipIterator()
        repeat(16) { l.add(i.next()) }
        assertEquals(listOf(setOf(0), setOf(0, 1), setOf(0), setOf(0, 1, 2), setOf(0), setOf(0, 1), setOf(0), setOf(0, 1, 2, 3), setOf(0),
                setOf(0, 1), setOf(0), setOf(0, 1, 2), setOf(0), setOf(0, 1), setOf(0), setOf(0, 1, 2, 3, 4)), l)
    }

    @Test
    fun hasNextForLongTime() {
        val i = BitFlipIterator()
        repeat(100_000) {
            assertTrue(i.hasNext())
            i.next()
        }
    }

}