package de.umr.fixcon.heuristics

import java.util.*
import kotlin.random.Random


/**
 * @param weightMap Specifies for each *key* the weighted probability for picking this element.
 * In other words: The probability of picking an element is proportional to the value saved in this map.
 *
 * Example: The weightMap {0: 1.0, 5: 3.7} implies that the probability of receiving 5 is 3.7 times as big
 * as the probability of receiving 0. Note that the weights do NOT need to add up to 1.
 */
class RandomCollection<E>(weightMap: Map<E, Double>) {

    private val map = TreeMap<Double, E>()
    private var totalWeight = 0.0

    init {
        weightMap.entries.forEach { entry -> add(entry.key, entry.value) }
    }

    private fun add(result: E, weight: Double): RandomCollection<E> {
        require(weight >= 0) { "Element cannot have a negative weight" }

        return if (weight == 0.0) this else {
            totalWeight += weight
            map[totalWeight] = result
            this
        }
    }

    val randomElement: E
        get() = map.higherEntry(Random.nextDouble(totalWeight)).value
}