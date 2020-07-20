package de.umr.core.dataStructures

class BitSwapIterator : Iterator<Set<Int>> {
    var curr = 1L
    var prev = 0L

    override fun hasNext() = true   //assumes you'll never actually reach 2^64

    override fun next(): Set<Int> {

        return posOfOnes(curr xor prev).also { curr++; prev++ }
    }
}