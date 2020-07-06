package de.umr.core.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.sign

internal class CollectionsExtKtTest {

    @Nested
    internal inner class intersectAll_Test {

        @Test
        fun emptyInput_test() = assertEquals(emptySet<Set<Int>>(), emptyList<Set<Int>>().intersectAll())

        @Test
        fun twoSets_Size1() = assertEquals(setOf(3), listOf(setOf(3), setOf(3)).intersectAll())

        @Test
        fun twoEqualSets() = assertEquals(setOf(6, 4, 7), listOf(setOf(6, 4, 7), setOf(6, 4, 7)).intersectAll())

        @Test
        fun twoDisjointSets() = assertEquals(emptySet<Set<Int>>(), listOf(setOf(6, 4, 7), setOf(9, 8, 3)).intersectAll())

        @Test
        fun oneElementLeft_test() = assertEquals(setOf(3), listOf(setOf(1, 2, 3), setOf(2, 3, 4), setOf(3, 4, 7)).intersectAll())

        @Test
        fun noElementLeft_test() = assertEquals(emptySet<Int>(), listOf(setOf(8, 2, 3), setOf(0, 3, 4), setOf(6, 4, 7)).intersectAll())

        @Test
        fun allElementsLeft_test() = assertEquals(setOf(1, 2, 3), listOf(setOf(1, 2, 3), setOf(1, 2, 3), setOf(1, 2, 3)).intersectAll())

        @Test
        fun fourEmptySets() = assertEquals(emptySet<Char>(), listOf(emptySet<Char>(), emptySet(), emptySet(), emptySet()).intersectAll())
    }

    @Nested
    internal inner class MultiAssociateBy {

        @Test
        fun intsByLastDigit() {
            val m = listOf(1, 4, 7, 34, 21, 56, 23, 67, 43, 76, 11123).multiAssociateBy { it.rem(10) }
            assertEquals(setOf(1, 21), m[1])
            assertEquals(setOf(23, 43, 11123), m[3])
            assertEquals(setOf(4, 34), m[4])
            assertEquals(setOf(56, 76), m[6])
            assertEquals(setOf(7, 67), m[7])
            assertFalse(8 in m.keys)
            assertFalse(9 in m.keys)
        }

        @Test
        fun stringsByLength() {
            val m = listOf("a", "B", "bli", "bla", "blub", "abcde").multiAssociateBy { it.length }
            assertEquals(setOf("a", "B"), m[1])
            assertEquals(setOf("bli", "bla"), m[3])
            assertEquals(setOf("blub"), m[4])
            assertEquals(setOf("abcde"), m[5])
        }

        @Test
        fun doublesBySign() {
            val m = listOf(334.52034, -1.3, 4.6, 7.245, 3.14159, -0.55, -333.5).multiAssociateBy { sign(it).toInt() }
            assertEquals(4, m[1]!!.size)
            assertEquals(3, m[-1]!!.size)
        }
    }
}