package unitTests.fixcon.heuristics

import de.umr.fixcon.heuristics.RandomSet
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class RandomSet_Test {

    @Test
    fun basic_test() {
        val map = mapOf(3 to 3.0, 1 to 4.6, 10 to 0.0)
        val randomCollection = RandomSet(map)
        assertTrue(map.containsKey(randomCollection.random))
    }

    @Test
    fun asymptoticWeighting_test() {
        val rCol = RandomSet(mapOf(0 to 1.0, 1 to 2.0))
        val runs = 1_000_000
        val relFreq = (1..runs).map { rCol.random }.count { it == 0 }.toDouble() / runs
        assertTrue(abs(relFreq - 1.0 / 3) < 0.01)
    }

    @Test
    fun negativeWeight_Exception_test() {
        assertThrows(IllegalArgumentException::class.java) { RandomSet(mapOf(0 to 1.0, 1 to -2.0)) }
    }

    @Test
    fun stringCollection_test() {
        val animals = setOf("Hund", "Katze", "Giraffe")
        val rCol = RandomSet(mapOf("Hund" to 1.4, "Katze" to 2.5, "Giraffe" to 23.6))
        repeat(10) {rCol.random in animals}
    }

    @Test
    fun acceptsMixedTypes_test() {
        val rCol = RandomSet(mapOf(1 to 1.2, "hallo" to 1.5))
        repeat(5) { assertTrue(rCol.random in setOf(1, "hallo"))}
    }

    @Test
    fun weightZeroNeverReturned_test() {
        val rCol = RandomSet(mapOf("immer" to 0.1, "nie" to 0.0))
        repeat(100) { assertTrue( rCol.random != "nie")}
    }
}