package de.umr.core.dataStructures

import java.util.*

object ListUtils {

    fun MutableList<Int>.incrementHead() {
        this[0] = this[0] + 1
    }

    fun <E> Deque<E>.duplicateHead() = this.push(this.peek())
}