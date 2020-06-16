package de.umr.core.random

import java.lang.IllegalStateException
import kotlin.random.Random.Default.nextDouble

fun <T> randomElement(weightMap: Map<T, Double>): T {

    val randVal = nextDouble(weightMap.values.sum())
    var currWeight = 0.0

    for ((elem, weight) in weightMap) {
        currWeight += weight
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}