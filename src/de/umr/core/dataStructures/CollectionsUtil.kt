package de.umr.core.dataStructures

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> intersectAll(collection: Collection<Set<T>>): Set<T> = run {
    val setsBySize = collection.toList().sortedBy { it.size }
    HashSet(setsBySize.first()).apply { for (i in 1 until setsBySize.size) retainAll(setsBySize[i]) }
}