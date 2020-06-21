package de.umr.core.random

import kotlin.random.Random.Default.nextDouble

fun <T> randomElement(weightMap: Map<T, Int>) = helper(weightMap, { it.toDouble() }, { it >= 0 })

fun <T> randomElementInverted(weightMap: Map<T, Int>) = helper(weightMap, { 1.0 / it }, { it > 0 })

private fun <T> helper(weightMap: Map<T, Int>, mapper: (Int) -> Double, requirement: (Int) -> Boolean): T {
    weightMap.values.forEach { require(requirement(it)) }

    val randVal = nextDouble(weightMap.values.map { mapper(it) }.sum())
    var currWeight = 0.0

    for ((elem, weight) in weightMap) {
        currWeight += mapper(weight)
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}