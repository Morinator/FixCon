package de.umr.fixcon.heuristics

import java.util.*

/**
 * @param weightMap Specifies for each *key* the weighted probability for picking this element. In other
 * words: The probability of picking an element is proportional to the value saved in this map.
 */
class RandomCollection(weightMap: Map<Int, Double>) {
    private val map: NavigableMap<Double, Int> = TreeMap()

    private var totalWeight = 0.0

    init {
        weightMap.entries.forEach { entry -> add(entry.key, entry.value) }
    }

    private fun add(result: Int, weight: Double): RandomCollection {
        require(weight >= 0) { "Element cannot have a negative weight" }
        if (weight == 0.0) return this

        totalWeight += weight
        map[totalWeight] = result
        return this
    }

    fun pickRandom(): Int = map.higherEntry(Random().nextDouble() * totalWeight).value

}