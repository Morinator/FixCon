package de.umr.core.extensions

/**@return The intersection of multiple sets in a new [HashSet] object.*/
fun <T> Collection<Set<T>>.intersectAll() =
        if (size == 1) first()
        else HashSet(minBy { it.size } ?: emptySet()).apply { this@intersectAll.forEach { retainAll(it) } }