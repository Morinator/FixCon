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

fun incrementHead(deque: Deque<Int>) = deque.push(deque.pop() + 1)

fun duplicateHead(deque: Deque<Int>) = deque.push(deque.peek())

fun <T> removeLast(mutableList: MutableList<T>) = mutableList.removeAt(mutableList.size - 1)

fun <T> unorderedPairs(col: Collection<T>): Set<Pair<T, T>> {
    val l = col.toList()
    return mutableSetOf<Pair<T, T>>().apply {
        for (i in l.indices)
            for (j in 0 until i)
                add(l[i] to l[j])
    }
}