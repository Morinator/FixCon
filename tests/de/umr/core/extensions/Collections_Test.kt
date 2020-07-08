package de.umr.core.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.NoSuchElementException

internal class Collections_Test {

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
        fun charTest() = assertEquals(setOf('a', 'b'), intersectAll(setOf(listOf('a', 'b', 'z', '#'), setOf('a', 'b', 'r', '5'))))

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
            dq.incrementHead()
            assertEquals(dq.toList(), listOf(1))
        }

        @Test
        fun oneTwelve() {
            val dq: Deque<Int> = ArrayDeque(listOf(12))
            dq.incrementHead()
            assertEquals(dq.toList(), listOf(13))
        }

        @Test
        fun bigDeque() {
            val dq: Deque<Int> = ArrayDeque((5 downTo 0).toList())
            dq.incrementHead()
            assertEquals(dq.toList(), listOf(6, 4, 3, 2, 1, 0))
        }

        @Test
        fun exceptionOnEmptyDeque() {
            val dq: Deque<Int> = ArrayDeque()
            assertThrows(NoSuchElementException::class.java) { dq.incrementHead() }
        }
    }

    @Nested
    internal inner class DuplicateHead {

        @Test
        fun oneZero() {
            val dq: Deque<Int> = ArrayDeque(listOf(0))
            dq.duplicateHead()
            assertEquals(dq.toList(), listOf(0, 0))
        }

        @Test
        fun oneTwelve() {
            val dq: Deque<Int> = ArrayDeque(listOf(12))
            dq.duplicateHead()
            assertEquals(dq.toList(), listOf(12, 12))
        }

        @Test
        fun bigDeque() {
            val dq: Deque<Int> = ArrayDeque((5 downTo 0).toList())
            dq.duplicateHead()
            assertEquals(dq.toList(), listOf(5, 5, 4, 3, 2, 1, 0))
        }

        @Test
        fun exceptionOnEmptyDeque() {
            val dq: Deque<Int> = ArrayDeque()
            assertThrows(NoSuchElementException::class.java) { dq.incrementHead() }
        }

    }

    @Nested
    internal inner class RemoveLast {

        @Test
        fun oneZero() {
            val li = mutableListOf(0)
            assertEquals(0, li.removeLast())
            assertEquals(listOf<Int>(), li)
        }

        @Test
        fun oneTwelve() {
            val li = mutableListOf(12)
            assertEquals(12, li.removeLast())
            assertEquals(listOf<Int>(), li)
        }

        @Test
        fun bigDeque() {
            val li = mutableListOf('a', 'e', '6', '#', 'k')
            assertEquals('k', li.removeLast())
            assertEquals(listOf('a', 'e', '6', '#'), li)
        }

        @Test
        fun exceptionOnEmptyDeque() {
            assertThrows(IndexOutOfBoundsException::class.java) { mutableListOf<Int>().removeLast() }
        }
    }
}