package de.umr.core.dataStructures

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CollectionsUtil_Test {

    @Nested
    internal inner class IntersectAll {

        //result is empty

        @Test
        fun twoDisjointSets() = assertEquals(emptySet<Set<Int>>(), intersectAll(listOf(setOf(6, 4, 7), setOf(9, 8, 3))))

        @Test
        fun noElementLeft_test() = assertEquals(emptySet<Int>(), intersectAll(listOf(setOf(8, 2, 3), setOf(0, 3, 4), setOf(6, 4, 7))))

        @Test
        fun fourEmptySets() = assertEquals(emptySet<Char>(), intersectAll(listOf(emptySet<Char>(), emptySet(), emptySet(), emptySet())))


        //result is not empty

        @Test
        fun twoSets_Size1() = assertEquals(setOf(3), intersectAll(listOf(setOf(3), setOf(3))))

        @Test
        fun twoEqualSets() = assertEquals(setOf(6, 4, 7), intersectAll(listOf(setOf(6, 4, 7), setOf(6, 4, 7))))

        @Test
        fun oneElementLeft_test() = assertEquals(setOf(3), intersectAll(listOf(setOf(1, 2, 3), setOf(2, 3, 4), setOf(3, 4, 7))))

        @Test
        fun allElementsLeft_test() = assertEquals(setOf(1, 2, 3), intersectAll(listOf(setOf(1, 2, 3), setOf(1, 2, 3), setOf(1, 2, 3))))

        @Test
        fun charTest() = assertEquals(setOf('a', 'b'), intersectAll(setOf(setOf('a', 'b', 'z', '#'), setOf('a', 'b', 'r', '5'))))

        @Test
        fun stringTest() {
            val result = setOf("bla", "algo", "33")
            val setA = setOf("bla", "algo", "33", "68", "schubidu", "ananas")
            val setB = setOf("bla", "algo", "33", "criticalClique", "vcbxcb", "twrtrwet")
            val setC = setOf("bla", "algo", "33", "ananas", "68", "criticalClique")
            assertEquals(result, intersectAll(listOf(setA, setB, setC)))
        }
    }
}