package de.umr.core.random

import kotlin.random.Random.Default.nextDouble

fun <T> randomElement(weightMap: Map<T, Int>): T {

    val randVal = nextDouble(weightMap.values.sum().toDouble())
    var currWeight = 0

    for ((elem, weight) in weightMap) {
        currWeight += weight
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}