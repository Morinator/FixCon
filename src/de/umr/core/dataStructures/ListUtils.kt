package de.umr.core.dataStructures

import java.lang.IllegalStateException
import java.util.*

/**
 * Provides extensions to the inbuilt types [MutableList] and [Deque].
 */
object ListUtils {

    /**Increments the first entry (the one at index 0) in the given [MutableList].
     * Example: [0, 2, 3].incrementHead() becomes [1, 2, 3]
     */
    fun MutableList<Int>.incrementHead() {
        this[0] = this[0] + 1
    }

    /**Duplicates the first element in the [Deque].
     * Example: [1, 2, 5].duplicateHead() become [1, 1, 2, 5]
     */
    fun <E> Deque<E>.duplicateHead() {
        if (isEmpty()) throw IllegalStateException("This object must be not empty")
        this.push(this.peek())
    }
}