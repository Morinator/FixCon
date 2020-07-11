package de.umr.core.dataStructures

import kotlin.random.Random.Default.nextDouble

val inv: (Int) -> Double = { 1.0 / it }

fun <T> takeRandom(weightMap: Map<T, Int>,
                   weightFunc: (Int) -> Double = { it.toDouble() }): T {

    val randomVal = nextDouble(weightMap.values.map { weightFunc(it) }.sum())
    var counter = 0.0
    for ((elem, weight) in weightMap) {
        counter += weightFunc(weight)
        if (counter >= randomVal) return elem
    }

    throw IllegalStateException("should never be reached")
}