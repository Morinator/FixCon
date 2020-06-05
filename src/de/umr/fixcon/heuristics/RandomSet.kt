package de.umr.fixcon.heuristics

import java.util.*
import kotlin.random.Random.Default.nextDouble


/**@param weightMap Specifies for each *key* the weighted probability for picking this element.
 * In other words: The probability of picking an element is proportional to the value saved in this map.
 *
 * @param T The type of the elements in the collection.
 *
 * Example: The weightMap {0: 1.0, 5: 3.7} implies that the probability of receiving 5 is 3.7 times as big
 * as the probability of receiving 0. Note that the weights do NOT need to add up to 1.*/
class RandomSet<T>(weightMap: Map<T, Double>) {

    private val map = TreeMap<Double, T>()
    private var totalWeight = 0.0

    init {
        weightMap.entries.forEach { add(it.key, it.value) }
    }

    val random: T get() = map.higherEntry(nextDouble(totalWeight)).value

    private fun add(result: T, weight: Double): RandomSet<T> {
        require(weight >= 0) { "Element cannot have a negative weight" }
        (weight > 0).also { if (it) totalWeight += weight; if (it) map[totalWeight] = result }
        return this
    }
}