package de.umr.core.dataStructures

import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.extensions.closedNB
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

internal class Partitioning_Test {

    private val ints = Partitioning<Int>()

    private val intsFilled = Partitioning<Int>().apply { //      { {1, 2, 3}, {4, 5}, {6} }
        this += setOf(1, 2, 3)
        this += setOf(4, 5)
        this += 6
    }

    private val strings = Partitioning<String>()

    private val stringsFilled = Partitioning<String>().apply {   //      { {a, b}, {c} }
        this += setOf("a", "b");this += "c"
    }

    @Nested
    internal inner class Constructor {

        @Test
        fun emptyAtStart_Int() {
            assertEquals(0, ints.size)
            repeat(20) { assertFalse(ints.contains(nextInt())) }
            assertEquals(emptySet<Int>(), ints.elements)
            assertEquals(0, ints.subsets.size)
        }

        @Test
        fun emptyAtStart_String() {
            assertEquals(0, strings.size)
            assertFalse("BiBaButzemann" in strings)
        }

        @Test
        fun rightNumberOfSubsets() {
            assertEquals(3, intsFilled.elements.map { intsFilled[it] }.distinct().count())
            assertEquals(2, stringsFilled.elements.map { stringsFilled[it] }.distinct().count())
        }
    }

    @Nested
    internal inner class Contains {

        @Test
        fun containsString() {
            assertTrue("a" in stringsFilled)
            assertTrue("b" in stringsFilled)
            assertFalse("Knoblauch" in stringsFilled)
        }

        @Test
        fun containsInt() {
            (1..6).forEach { assertTrue(it in intsFilled) }
            assertFalse(-1 in intsFilled)
            assertFalse(100 in intsFilled)
        }
    }

    @Nested
    internal inner class Subset {

        @Test
        fun subset() {
            assertTrue(intsFilled[1] === intsFilled[2])
            assertTrue(intsFilled[2] === intsFilled[3])
            assertEquals(intsFilled[1], setOf(1, 2, 3))

            assertTrue(intsFilled[4] === intsFilled[5])
            assertEquals(intsFilled[4], setOf(4, 5))

            assertEquals(intsFilled[6], setOf(6))
        }

        @Test
        fun subset_onlySize1Sets() {
            ints += 1
            ints += 2
            ints += 3

            assertEquals(1, ints[1].size)
            assertEquals(1, ints[2].size)
            assertEquals(1, ints[3].size)

            assertEquals(3, ints.size)
        }
    }

    @Nested
    internal inner class Adding {

        @Test
        fun addToSubset() {
            ints += 1
            ints.addToSubset(1, 2)
            ints += 3
            assertEquals(setOf(1, 2), ints[2])
            assertEquals(setOf(3), ints[3])
        }

        @Test
        fun addToSubset_ExceptionIfOldValueNotPresent() {
            assertThrows(Exception::class.java) { ints.addToSubset(1, 2) }
        }

        @Test
        fun addInNewSubset() {
            ints += 1
            assertEquals(1, ints.size)
            ints += 1
            assertEquals(1, ints.size)

            ints += 2
            ints += 1
            ints += 2
            assertEquals(setOf(1, 2), ints.elements)

            ints.addToSubset(2, 3)
            assertEquals(setOf(2, 3), ints[2])

            ints += 3
            assertEquals(setOf(2, 3), ints[2])

            assertEquals(setOf(1, 2, 3), ints.elements)
        }
    }

    @Nested
    internal inner class Removal {

        @Test
        fun singleRemove() {
            intsFilled -= 1
            assertTrue(1 !in intsFilled)
            assertTrue(intsFilled[2] === intsFilled[3])
            assertEquals(intsFilled[2], setOf(2, 3))

            assertEquals(setOf(2, 3, 4, 5, 6), intsFilled.elements)
        }

        @Test
        fun remove_elemMissing() {
            intsFilled -= 7  //7 is not present
            assertTrue(intsFilled[2] === intsFilled[3])
            assertEquals(intsFilled[2], setOf(1, 2, 3))

            assertEquals(setOf(1, 2, 3, 4, 5, 6), intsFilled.elements)
        }

        @Test
        fun multiRemove() {
            intsFilled -= setOf(1, 2, 3)
            assertTrue(3 !in intsFilled)
            assertThrows(Exception::class.java) { intsFilled[1] }

            assertEquals(setOf(4, 5), intsFilled[4])
            assertEquals(setOf(4, 5, 6), intsFilled.elements)
        }
    }

    @Nested
    internal inner class Elements {
        @Test
        fun elements() {
            assertEquals(setOf(1, 2, 3, 4, 5, 6), intsFilled.elements)
            assertEquals(setOf("a", "c", "b"), stringsFilled.elements)
        }
    }

    @Nested
    internal inner class AddByEquivalencePredicate {
        private val p = Partitioning<Int>()

        @Test
        fun closedNB_clique10() {
            val g = createClique(10)
            p.addByEQPredicate((0 until 10).toList()) { x, y -> g.closedNB(x) == g.closedNB(y) }
            assertEquals(10, p[0].size)
        }

        @Test
        fun ints_difference3() {
            p.addByEQPredicate(listOf(0, 1, 2, 4, 7, 11)) { x, y -> abs(x - y).rem(3) == 0 }
            assertEquals(setOf(0), p[0])
            assertEquals(setOf(1, 4, 7), p[1])
            assertEquals(setOf(2, 11), p[2])
        }

        @Test
        fun closedNB_path6_toExistingValues() {
            val g = createPath(10)
            p += 100;p.addToSubset(100, 101);p.addToSubset(100, 102)
            p.addByEQPredicate((0 until 10).toList()) { x, y -> g.closedNB(x) == g.closedNB(y) }
            (0 until 10).forEach {
                assertEquals(1, p[it].size)
            }
            assertEquals(3, p[100].size)
        }
    }

    @Nested
    internal inner class RemoveSubset {
        @Test
        fun subsetSize3() {
            intsFilled.removeSubset(1)
            assertTrue(1 !in intsFilled)
            assertThrows(Exception::class.java) { intsFilled[2] }

            assertEquals(setOf(4, 5), intsFilled[5])
            assertEquals(setOf(4, 5, 6), intsFilled.elements)
        }

        @Test
        fun subsetSize1() {
            intsFilled.removeSubset(6)
            assertTrue(6 !in intsFilled)
            assertThrows(Exception::class.java) { intsFilled[6] }

            assertEquals(setOf(1, 2, 3, 4, 5), intsFilled.elements)
            assertEquals(setOf(1, 2, 3), intsFilled[2])
            assertEquals(setOf(4, 5), intsFilled[4])
        }

    }

    @Nested
    internal inner class DisjointUnion {

        @Test
        fun testA() {
            val other = Partitioning<Int>().apply { this += setOf(10, 13, 99, 1004) }
            intsFilled.disjointUnion(other)
            assertEquals(4, intsFilled.subsets.size)
            assertEquals(setOf(10, 13, 99, 1004), intsFilled[13])
            assertEquals(setOf(1, 2, 3), intsFilled[3])
        }
    }

    @Nested
    internal inner class Merge {

        @Test
        fun firstArgumentNotInPartitioning() {
            assertThrows(Exception::class.java) { intsFilled.merge(100, 1) }
        }

        @Test
        fun secondArgumentNotInPartitioning() {
            assertThrows(Exception::class.java) { intsFilled.merge(1, 100) }
        }

        @Test
        fun twoArgumentNotInPartitioning() {
            assertThrows(Exception::class.java) { intsFilled.merge(-100, 100) }
        }

        @Test
        fun bothArgumentsInSameSubset() {
            intsFilled.merge(1, 2)
            assertEquals(listOf(setOf(1, 2, 3), setOf(4, 5), setOf(6)), intsFilled.subsets)    //nothing happened
        }

        @Test
        fun mergedTwoSubsets() {
            intsFilled.merge(1, 4)
            assertEquals(listOf(setOf(1, 2, 3, 4, 5), setOf(6)), intsFilled.subsets)
        }

        @Test
        fun doubleTrouble() {
            intsFilled.merge(1, 4)
            intsFilled.merge(4, 6)
            assertEquals(listOf(setOf(1, 2, 3, 4, 5,6)), intsFilled.subsets)
        }
    }
}