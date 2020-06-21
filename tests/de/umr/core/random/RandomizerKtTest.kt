package de.umr.core.random

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class RandomizerKtTest {

    @Nested
    internal inner class randomElement {
        @Test
        fun basic_test() {
            val map = mapOf(3 to 3, 1 to 4, 10 to 0)
            assertTrue(map.containsKey(randomElement(map)))
        }

        @Test
        fun asymptoticWeighting_test() {
            val map = mapOf(0 to 1, 1 to 2)
            val runs = 1_000_000
            val relFreq = (1..runs).map { randomElement(map) }.count { it == 0 }.toDouble() / runs
            assertEquals(relFreq, 1.0 / 3, 0.01)
        }

        @Test
        fun laplaceDistribution() {
            val runs = 1_000_000
            val freqMap = mutableMapOf<Int, Int>()
            repeat(runs) {
                val randVal = (1..6).random()
                freqMap[randVal] = freqMap.getOrDefault(randVal, 0) + 1
            }
            freqMap.values.forEach { assertEquals(it.toDouble() / runs, 1.0 / 6, 0.01) }
        }

        @Test
        fun negativeWeight_Exception_test() {
            assertThrows(IllegalArgumentException::class.java) { randomElement(mapOf(0 to 1, 1 to -2)) }
        }

        @Test
        fun weightSumZero_Exception_test() {
            assertThrows(IllegalArgumentException::class.java) { randomElement(mapOf(0 to 0, 1 to 0)) }
        }

        @Test
        fun stringCollection_test() =
                repeat(10) { randomElement(mapOf("Hund" to 14, "Katze" to 25, "Giraffe" to 236)) in setOf("Hund", "Katze", "Giraffe") }

        @Test
        fun acceptsMixedTypes_test() =
                repeat(5) { assertTrue(randomElement(mapOf(1 to 12, "hallo" to 15)) in setOf(1, "hallo")) }

        @Test
        fun weightZeroNeverReturned_test() =
                repeat(100) { assertTrue(randomElement(mapOf("immer" to 1, "nie" to 0)) != "nie") }
    }

    @Nested
    internal inner class randomElementInverted {
        @Test
        fun basic_test() {
            val map = mapOf(3 to 3, 1 to 4, 10 to 2)
            assertTrue(map.containsKey(randomElementInverted(map)))
        }

        @Test
        fun asymptoticWeighting_test() {
            val map = mapOf(0 to 1, 1 to 2)
            val runs = 1_000_000
            val relFreq = (1..runs).map { randomElementInverted(map) }.count { it == 0 }.toDouble() / runs
            assertEquals(2.0 / 3, relFreq, 0.01)
        }

        @Test
        fun negativeWeight_Exception_test() {
            assertThrows(IllegalArgumentException::class.java) { randomElementInverted(mapOf(0 to 1, 1 to -2)) }
        }

        @Test
        fun weightSumZero_Exception_test() {
            assertThrows(IllegalArgumentException::class.java) { randomElementInverted(mapOf(0 to 0, 1 to 0)) }
        }

        @Test
        fun stringCollection_test() =
                repeat(10) { randomElementInverted(mapOf("Hund" to 14, "Katze" to 25, "Giraffe" to 236)) in setOf("Hund", "Katze", "Giraffe") }

        @Test
        fun acceptsMixedTypes_test() =
                repeat(5) { assertTrue(randomElementInverted(mapOf(1 to 12, "hallo" to 15)) in setOf(1, "hallo")) }
    }

}