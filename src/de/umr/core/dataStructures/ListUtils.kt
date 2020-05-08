package de.umr.core.dataStructures

import java.util.*

object ListUtils {

    fun  incrementHead(list: MutableList<Int>) {
        list[0] = list[0] + 1
    }

    fun duplicateHead(list: Deque<Int>) {
        list.push(list.peek())
    }
}