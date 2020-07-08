package de.umr.core.extensions

import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> Collection<Set<T>>.intersectAll() =
        if (size == 1) first()
        else HashSet(minBy { it.size } ?: emptySet()).apply { this@intersectAll.forEach { retainAll(it) } }

fun ArrayDeque<Int>.incHead() = push(pop() + 1)

fun ArrayDeque<Int>.duplicateHead() = push(peek())

fun <T> MutableList<T>.removeLast() = removeAt(size - 1)

fun randBoolean(chance: Double) = Random.nextDouble() < chance