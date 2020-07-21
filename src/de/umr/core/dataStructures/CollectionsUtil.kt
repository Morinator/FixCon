package de.umr.core.dataStructures

import java.util.*
import kotlin.collections.HashSet

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> intersectAll(collection: Collection<Set<T>>): Set<T> = when (collection.size) {
    0 -> emptySet()
    1 -> collection.first()
    else -> {
        val setsBySize = collection.toList().sortedBy { it.size }
        HashSet(setsBySize.first()).apply { for (i in 1 until setsBySize.size) retainAll(setsBySize[i]) }
    }
}

fun incrementHead(dq: Deque<Int>) = dq.push(dq.pop() + 1)

fun duplicateHead(dq: Deque<Int>) = dq.push(dq.peek())

fun <T> unorderedPairs(col: Collection<T>): List<Pair<T, T>> =
        col.toList().run { (indices).flatMap { x -> (0 until x).map { y -> Pair(this[x], this[y]) } } }

fun posOfOnes(n: Long) = n.toString(radix = 2).reversed().run {  indices.filterTo(HashSet(), { this[it] == '1' })}