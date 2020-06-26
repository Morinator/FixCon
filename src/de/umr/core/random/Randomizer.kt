package de.umr.core.random

import kotlin.random.Random.Default.nextDouble

val inv: (Int) -> Double = { 1.0 / it }

fun <T> takeRandom(weightMap: Map<T, Int>,
                   weightMapping: (Int) -> Double = { it.toDouble() },
                   requirement: (Int) -> Boolean = { it >= 0 }): T {

    weightMap.values.forEach { require(requirement(it)) }
    val randomVal = nextDouble(weightMap.values.map { weightMapping(it).also { x -> require(x.isFinite()) } }.sum())

    var currWeight = 0.0
    for ((elem, weight) in weightMap) {
        currWeight += weightMapping(weight)
        if (currWeight >= randomVal) return elem
    }

    throw IllegalStateException("should never be reached")
}