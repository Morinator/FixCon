package de.umr.core.random

import java.util.*
import kotlin.random.Random.Default.nextDouble

class WeightedRandomSet<T>(weightMap: Map<T, Double>) {

    private val map = TreeMap<Double, T>()
    var totalWeight = 0.0

    init {
        weightMap.entries.forEach { add(it.key, it.value) }
    }

    private fun add(element: T, weight: Double) {
        map[totalWeight] = element
        totalWeight += weight
    }

    val random: T get() = map.floorEntry(nextDouble(totalWeight)).value
}