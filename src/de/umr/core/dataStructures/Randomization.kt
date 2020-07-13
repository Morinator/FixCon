package de.umr.core.dataStructures

import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble

val inv: (Int) -> Double = { 1.0 / it }
val identity: (Int) -> Double = { 1.0 * it }

fun <T> takeRandom(weightMap: Map<T, Int>,
                   weightFunc: (Int) -> Double = identity): T {

    val randomVal = nextDouble(weightMap.values.sumByDouble { weightFunc(it) })
    var counter = 0.0
    for ((elem, weight) in weightMap) {
        counter += weightFunc(weight)
        if (counter >= randomVal) return elem
    }

    throw IllegalStateException("should never be reached")
}

/**@return A random [Boolean] value that is *True* with a chance of [chance].*/
fun randBoolean(chance: Double) = nextDouble() < chance