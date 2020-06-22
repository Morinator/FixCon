package de.umr.core.random

import kotlin.random.Random.Default.nextDouble

val reciprocal: (Int) -> Double = { x -> 1.0 / x }

fun <T> randomElement(weightMap: Map<T, Int>, mapper: (Int) -> Double = { it.toDouble() }, requirement: (Int) -> Boolean = { it >= 0 }): T {
    weightMap.values.forEach { require(requirement(it)) }

    val randVal = nextDouble(weightMap.values.map { mapper(it).also { x -> require(x.isFinite()) } }.sum())
    var currWeight = 0.0

    for ((elem, weight) in weightMap) {
        currWeight += mapper(weight)
        if (randVal <= currWeight) return elem
    }

    throw IllegalStateException("should never be reached")
}