package de.umr.core.extensions

import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> intersectAll(collection: Collection<Collection<T>>) =
        if (collection.size == 1) collection.first()
        else HashSet(collection.minBy { it.size } ?: emptySet()).apply { collection.forEach { retainAll(it) } }

fun Deque<Int>.incrementHead() = push(pop() + 1)

fun Deque<Int>.duplicateHead() = push(peek())

fun <T> MutableList<T>.removeLast() = removeAt(size - 1)

/**@return A random [Boolean] value that is *True* with a chance of [chance].*/
fun randBoolean(chance: Double) = Random.nextDouble() < chance