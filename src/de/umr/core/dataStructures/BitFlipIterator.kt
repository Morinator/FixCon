package de.umr.core.dataStructures

/**This object iterates through the flipping bits in binary representation if you count the natural numbers beginning from 0.
 *
 *
 * Examples:        From 0 to 1, only the bit at index 0 changes         0
 *                                                                       1
 *                  ------------------------------------------------------
 *                  From 1 to 2, the bits at indices 0 and 1 change.    01
 *                                                                      10
 */
class BitFlipIterator : Iterator<Set<Int>> {
    private var curr = 1L
    private var prev = 0L

    override fun hasNext() = true   //assumes you'll never actually reach 2^64

    override fun next(): Set<Int> {
        val result = posOfOnes(curr xor prev)
        curr++
        prev++
        return result
    }
}