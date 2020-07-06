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
}