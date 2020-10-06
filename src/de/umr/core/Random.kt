package de.umr.core

import kotlin.random.Random

val inv: (Int) -> Double = { 1.0 / it }
val identity: (Int) -> Double = { 1.0 * it }

fun <T> takeRandom(weightMap: Map<T, Int>,
                   weightFunc: (Int) -> Double = identity): T {

    val randomVal = Random.nextDouble(weightMap.values.sumByDouble { weightFunc(it) })
    var counter = 0.0
    for ((elem, weight) in weightMap) {
        counter += weightFunc(weight)
        if (counter >= randomVal) return elem
    }

    throw IllegalStateException("should never be reached")
}