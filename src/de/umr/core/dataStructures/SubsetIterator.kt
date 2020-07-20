package de.umr.core.dataStructures

import kotlin.math.pow

class SubsetIterator<T>(s: Set<T>) : Iterator<Set<T>> {

    init {
        require(s.size <= 64)   //long-datatype has 64 bits for bit-mask
    }

    private val l = s.toList()
    private var counter: Long = 0
    private val maxCounter = 2 pow l.size
    private val curr = mutableSetOf<T>()

    override fun hasNext(): Boolean = counter < maxCounter

    override fun next(): Set<T> {
        for (i in l.indices)
            if (counter and (1L shl i) > 0) curr.add(l[i]) else curr.remove(l[i])
        counter++
        return curr
    }
}

fun main() {
    val l = mutableListOf<Set<Int>>()
    val i = SubsetIterator(setOf(1, 2, 3, 4))
    while (i.hasNext()) l.add(i.next().toSet())
    println(l)
}