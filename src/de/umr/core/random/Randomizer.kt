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

fun <T> randomElementInverted(weightMap: Map<T, Int>): T {
    weightMap.values.forEach { require(it > 0) }
    val randVal = nextDouble(weightMap.values.map { 1.0 / it }.sum())
    var currWeight = 0.0

    for ((elem, weight) in weightMap) {
        currWeight += 1.0 / weight
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}