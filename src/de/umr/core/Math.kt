package de.umr.core

import kotlin.random.Random

infix fun Int.pow(a: Int): Int {
    var res = 1
    for (i in 0 until a) res *= this
    return res
}

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

/**@return A random [Boolean] value that is *True* with a chance of [chance].*/
fun randBoolean(chance: Double) = Random.nextDouble() < chance