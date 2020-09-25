package de.umr.core.dataStructures

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> intersectAll(collection: Collection<Set<T>>): Set<T> = run {
    val setsBySize = collection.toList().sortedBy { it.size }
    HashSet(setsBySize.first()).apply { for (i in 1 until setsBySize.size) retainAll(setsBySize[i]) }
}

fun <T> unorderedPairs(col: Collection<T>): List<Pair<T, T>> =
        col.toList().run { (indices).flatMap { x -> (0 until x).map { y -> Pair(this[x], this[y]) } } }

fun posOfOnes(n: Long) = n.toString(radix = 2).reversed().run {  indices.filterTo(HashSet(), { this[it] == '1' })}