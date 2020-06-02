package unitTests.fixcon.heuristics

import de.umr.fixcon.heuristics.RandomCollection
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class RandomCollection_Test {

    @Test
    fun basic_test() {
        val map = mapOf(3 to 3.0, 1 to 4.6, 10 to 0.0)
        val randomCollection = RandomCollection(map)
        assertTrue(map.containsKey(randomCollection.pickRandom()))
    }

    @Test
    fun asymptoticWeighting_test() {
        val randomCollection = RandomCollection(mapOf(0 to 1.0, 1 to 2.0))
        val runs = 1000000
        var zeros = 0.0
        repeat(runs) {
            if (randomCollection.pickRandom() == 0) zeros++
        }
        val relativeFrequency: Double = zeros / runs
        assertTrue(abs(relativeFrequency - 1.0 / 3) < 0.05)
    }
}