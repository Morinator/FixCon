package de.umr.core.dataStructures

import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.extensions.closedNB
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

internal class SetPartitioningTest {

    private val ints = SetPartitioning<Int>()
    private val intsFilled = SetPartitioning<Int>().apply {
        addInNewSubset(1);addToSubset(1, 2);addToSubset(2, 3);addInNewSubset(4);addToSubset(4, 5);addInNewSubset(6)
    }

    private val strings = SetPartitioning<String>()
    private val stringsFilled = SetPartitioning<String>().apply {
        addInNewSubset("a");addToSubset("a", "b");addInNewSubset("c")
    }

    @Nested
    internal inner class Constructor {
        @Test
        fun emptyAtStart_Int() {
            assertEquals(0, ints.size)
            repeat(20) { assertFalse(ints.contains(nextInt())) }
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
            assertSame(intsFilled[1], intsFilled[2])
            assertSame(intsFilled[2], intsFilled[3])
            assertEquals(intsFilled[1], setOf(1, 2, 3))

            assertSame(intsFilled[4], intsFilled[5])
            assertEquals(intsFilled[4], setOf(4, 5))

            assertEquals(intsFilled[6], setOf(6))
        }

        @Test
        fun subset_onlySize1Sets() {
            ints.addInNewSubset(1)
            ints.addInNewSubset(2)
            ints.addInNewSubset(3)

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
            ints.addInNewSubset(1)
            ints.addToSubset(1, 2)
            ints.addInNewSubset(3)

            assertFalse(ints.addToSubset(1, 1))
            assertFalse(ints.addToSubset(1, 2))
            assertFalse(ints.addToSubset(1, 3))

            assertTrue(ints.addToSubset(2, 4))
        }

        @Test
        fun addToSubset_ExceptionIfOldValueNotPresent() {
            assertThrows(Exception::class.java) { ints.addToSubset(1, 2) }
        }

        @Test
        fun addInNewSubset() {
            assertTrue(ints.addInNewSubset(1))
            assertFalse(ints.addInNewSubset(1))

            assertTrue(ints.addInNewSubset(2))
            assertFalse(ints.addInNewSubset(1))
            assertFalse(ints.addInNewSubset(2))

            assertTrue(ints.addToSubset(2, 3))
            assertFalse(ints.addInNewSubset(3))

            assertEquals(setOf(1, 2, 3), ints.elements)
        }
    }

    @Nested
    internal inner class Removal {

        @Test
        fun singleRemove() {
            intsFilled.removeElem(1)
            assertTrue(1 !in intsFilled)
            assertSame(intsFilled[2], intsFilled[3])
            assertEquals(intsFilled[2], setOf(2, 3))

            assertEquals(setOf(2, 3, 4, 5, 6), intsFilled.elements)
        }

        @Test
        fun multiRemove() {
            intsFilled.removeElem(1)
            intsFilled.removeElem(2)
            intsFilled.removeElem(3)
            assertTrue(3 !in intsFilled)
            assertThrows(Exception::class.java){intsFilled[1]}

            assertEquals(setOf(4,5), intsFilled[4])
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
        private val p = SetPartitioning<Int>()

        @Test
        fun closedNB_clique10() {
            val g = createClique(10)
            p.addByEQPredicate((0 until 10).toList()) { x, y -> g.closedNB(x) == g.closedNB(y) }
            assertEquals(10, p[0].size)
        }

        @Test
        fun ints_difference3() {
            p.addByEQPredicate(listOf(0, 1, 2, 4, 7, 11)) { x, y -> abs(x - y).rem(3) == 0 }
            assertEquals(1, p[0].size)
            assertEquals(3, p[1].size)
            assertEquals(2, p[2].size)
        }

        @Test
        fun closedNB_path6_toOldValues() {
            val g = createPath(10)
            p.addInNewSubset(100);p.addToSubset(100, 101);p.addToSubset(100, 102)
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
            assertThrows(Exception::class.java){intsFilled[2]}

            assertEquals(setOf(4,5), intsFilled[5])
            assertEquals(setOf(4, 5, 6), intsFilled.elements)
        }

        @Test
        fun subsetSize1() {
            intsFilled.removeSubset(6)
            assertTrue(6 !in intsFilled)
            assertThrows(Exception::class.java){intsFilled[6]}

            assertEquals(setOf(1,2,3,4,5), intsFilled.elements)
            assertEquals(setOf(1,2,3), intsFilled[2])
            assertEquals(setOf(4,5), intsFilled[4])
        }

    }
}