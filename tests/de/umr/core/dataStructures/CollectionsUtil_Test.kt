package de.umr.core.dataStructures

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
    internal inner class IncrementHead {

        @Test
        fun oneZero() {
            val dq: Deque<Int> = ArrayDeque(listOf(0))
            incrementHead(dq)
            assertEquals(dq.toList(), listOf(1))
        }

        @Test
        fun oneTwelve() {
            val dq: Deque<Int> = ArrayDeque(listOf(12))
            incrementHead(dq)
            assertEquals(dq.toList(), listOf(13))
        }

        @Test
        fun bigDeque() {
            val dq: Deque<Int> = ArrayDeque((5 downTo 0).toList())
            incrementHead(dq)
            assertEquals(dq.toList(), listOf(6, 4, 3, 2, 1, 0))
        }

        @Test
        fun exceptionOnEmptyDeque() {
            val dq: Deque<Int> = ArrayDeque()
            assertThrows(NoSuchElementException::class.java) { incrementHead(dq) }
        }
    }

    @Nested
    internal inner class DuplicateHead {

        @Test
        fun oneZero() {
            val dq: Deque<Int> = ArrayDeque(listOf(0))
            duplicateHead(dq)
            assertEquals(dq.toList(), listOf(0, 0))
        }

        @Test
        fun oneTwelve() {
            val dq: Deque<Int> = ArrayDeque(listOf(12))
            duplicateHead(dq)
            assertEquals(dq.toList(), listOf(12, 12))
        }

        @Test
        fun bigDeque() {
            val dq: Deque<Int> = ArrayDeque((5 downTo 0).toList())
            duplicateHead(dq)
            assertEquals(dq.toList(), listOf(5, 5, 4, 3, 2, 1, 0))
        }

        @Test
        fun exceptionOnEmptyDeque() {
            val dq: Deque<Int> = ArrayDeque()
            assertThrows(NoSuchElementException::class.java) { incrementHead(dq) }
        }

    }

    @Nested
    internal inner class RemoveLast {

        @Test
        fun oneZero() {
            val li = mutableListOf(0)
            assertEquals(0, removeLast(li))
            assertEquals(listOf<Int>(), li)
        }

        @Test
        fun oneTwelve() {
            val li = mutableListOf(12)
            assertEquals(12, removeLast(li))
            assertEquals(listOf<Int>(), li)
        }

        @Test
        fun bigDeque() {
            val li = mutableListOf('a', 'e', '6', '#', 'k')
            assertEquals('k', removeLast(li))
            assertEquals(listOf('a', 'e', '6', '#'), li)
        }

        @Test
        fun exceptionOnEmptyDeque() {
            assertThrows(IndexOutOfBoundsException::class.java) { removeLast(mutableListOf<Int>()) }
        }
    }

    @Nested
    internal inner class UnorderedPairs {

        @Test
        fun size0() {
            val li = listOf<Int>()
            assertEquals(setOf<Pair<Int, Int>>(), unorderedPairs(li))
        }

        @Test
        fun size1() {
            val li = listOf(0)
            assertEquals(setOf<Pair<Int, Int>>(), unorderedPairs(li))
        }

        @Test
        fun size2() {
            val li = (0..1).toList()
            assertEquals(setOf(1 to 0), unorderedPairs(li))
        }

        @Test
        fun size3() {
            val li = (0..2).toList()
            assertEquals(setOf(1 to 0, 2 to 0, 2 to 1), unorderedPairs(li))
        }

        @Test
        fun size5() {
            val li = (0..4).toList()
            assertEquals(setOf(1 to 0, 2 to 0, 2 to 1, 3 to 0, 3 to 1, 3 to 2, 4 to 0, 4 to 1, 4 to 2, 4 to 3), unorderedPairs(li))
        }
    }
}