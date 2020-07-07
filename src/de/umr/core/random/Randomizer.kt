package de.umr.core.random

import kotlin.random.Random.Default.nextDouble

val inv: (Int) -> Double = { 1.0 / it }

fun <T> takeRandom(weightMap: Map<T, Int>,
                   weightFunc: (Int) -> Double = { it.toDouble() }): T {

    val randomVal = nextDouble(weightMap.values.map { weightFunc(it) }.sum())
    var currWeight = 0.0
    for ((elem, weight) in weightMap) {
        currWeight += weightFunc(weight)
        if (currWeight >= randomVal) return elem
    }

    throw IllegalStateException("should never be reached")
}