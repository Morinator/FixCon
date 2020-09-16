package de.umr.core.dataStructures

import de.umr.core.pow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.NoSuchElementException

internal class CollectionsUtil_Test {

    @Nested
    internal inner class IntersectAll {

        //result is empty

        @Test
        fun emptyInput_test() = assertEquals(emptySet<Set<Int>>(), intersectAll(emptyList<Set<Int>>()))

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

    @Nested
    internal inner class UnorderedPairs {

        @Test
        fun size0() {
            val li = listOf<Int>()
            assertEquals(emptyList<Pair<Int, Int>>(), unorderedPairs(li))
        }

        @Test
        fun size1() {
            val li = listOf(0)
            assertEquals(emptyList<Pair<Int, Int>>(), unorderedPairs(li))
        }

        @Test
        fun size2() {
            val li = (0..1).toList()
            assertEquals(listOf(1 to 0), unorderedPairs(li))
        }

        @Test
        fun size3() {
            val li = (0..2).toList()
            assertEquals(listOf(1 to 0, 2 to 0, 2 to 1), unorderedPairs(li))
        }

        @Test
        fun size5() {
            val li = (0..4).toList()
            assertEquals(listOf(1 to 0, 2 to 0, 2 to 1, 3 to 0, 3 to 1, 3 to 2, 4 to 0, 4 to 1, 4 to 2, 4 to 3), unorderedPairs(li))
        }
    }

    @Nested
    internal inner class Pow {

        @Test
        fun oneOne() = assertEquals(1, 1 pow 1)

        @Test
        fun twoZero() = assertEquals(1, 2 pow 0)

        @Test
        fun twoOne() = assertEquals(2, 2 pow 1)

        @Test
        fun twoThree() = assertEquals(8, 2 pow 3)

        @Test
        fun minusThreeFour() = assertEquals(81, -3 pow 4)

        @Test
        fun minsTwoThree() = assertEquals(-8, -2 pow 3)
    }

    @Nested
    internal inner class PosOfOnes {

        @Test
        fun one() = assertEquals(setOf(0), posOfOnes(1))

        @Test
        fun seven() = assertEquals(setOf(0, 1, 2), posOfOnes(7))

        @Test
        fun seventyFive() = assertEquals(setOf(0, 1, 3, 6), posOfOnes(75))

        @Test
        fun hundret() = assertEquals(setOf(2, 5, 6), posOfOnes(100))
    }

}