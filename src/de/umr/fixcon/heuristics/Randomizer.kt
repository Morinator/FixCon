package de.umr.fixcon.heuristics

import java.lang.IllegalStateException
import kotlin.random.Random.Default.nextDouble

fun <T> randomElement(weightMap: Map<T, Double>): T {

    weightMap.values.forEach { require(it >= 0) }       //only positive weights
    require(weightMap.values.sum() > 0)                 //sum of weights must be positive

    val randVal = nextDouble(weightMap.values.sum())
    var currWeight = 0.0

    for ((elem, weight) in weightMap) {
        currWeight += weight
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}