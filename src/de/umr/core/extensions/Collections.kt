package de.umr.core.extensions

import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> intersectAll(collection: Collection<Collection<T>>) =
        if (collection.size == 1) collection.first()
        else HashSet(collection.minBy { it.size } ?: emptySet()).apply { collection.forEach { retainAll(it) } }

/**@return A random [Boolean] value that is *True* with a chance of [chance].*/
fun randBoolean(chance: Double) = Random.nextDouble() < chance



fun incrementHead(deque: Deque<Int>) = deque.push(deque.pop() + 1)

fun duplicateHead(deque: Deque<Int>) = deque.push(deque.peek())

fun <T> removeLast(mutableList: MutableList<T>) = mutableList.removeAt(mutableList.size - 1)
