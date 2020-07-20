package de.umr.core.dataStructures

class NonEmptySubsetIterator<T>(s: Set<T>) : Iterator<Set<T>> {
    private val l = s.toList()
    private val curr = mutableSetOf<T>()
    private val i = BitSwapIterator()

    override fun hasNext(): Boolean = curr.size < l.size

    override fun next(): Set<T> {
        i.next().forEach { if (l[it] in curr) curr.remove(l[it]) else curr.add(l[it]) }
        return curr
    }
}