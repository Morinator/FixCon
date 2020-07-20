package de.umr.core.dataStructures

class SubsetIterator<T>(s: Set<T>) : Iterator<Set<T>> {

    init {
        require(s.size <= 64)   //long-datatype has 64 bits for bit-mask
    }

    private val l = s.toList()
    private val curr = mutableSetOf<T>()

    private var counter: Long = 0
    private val maxCount = 2 pow l.size

    override fun hasNext(): Boolean = counter < maxCount

    override fun next(): Set<T> {
        for (i in l.indices) if (counter and (1L shl i) > 0) curr.add(l[i]) else curr.remove(l[i])
        return curr.also { counter++ }
    }
}