package de.umr.core.extensions

import java.util.*
import kotlin.collections.Collection
import kotlin.collections.HashSet
import kotlin.collections.Set
import kotlin.collections.emptySet
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.minBy
import kotlin.random.Random

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> Collection<Set<T>>.intersectAll() =
        if (size == 1) first()
        else HashSet(minBy { it.size } ?: emptySet()).apply { this@intersectAll.forEach { retainAll(it) } }

fun ArrayDeque<Int>.incHead() = push(pop() + 1)

fun ArrayDeque<Int>.duplicateHead() = push(pop() + 1)

fun randBoolean(chance: Double) = Random.nextDouble() < chance