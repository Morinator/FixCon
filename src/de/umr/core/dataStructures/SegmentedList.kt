package de.umr.core.dataStructures

import java.util.*
import kotlin.collections.ArrayList

/**This data structure implements a list the is partitioned into individual parts called segments.
 * This data structure is called [SegmentedList] because it consists of segments which can contain multiple elements, and only
 * the last segment can be popped at any given time.
 *
 * The example ((1, 2), (4), (6, 5, 4)) contains 3 segments. The first segment is (1, 2), the second segment
 * is (4) and the third segment is (6, 5, 4).
 *
 * The indexing is continuous, meaning that the entry at index *4* in the example is 5.
 *
 * The method [add] creates a new segment for the single element
 * Example: ((1, 2), (3)).add(5) is ((1, 2), (3), (5))
 *
 * The method [addAll] creates only one segment for all the new elements.
 * Example: ((1, 2), (3)).addAll(listOf(5, 4, 7) is ((1, 2), (3), (5, 4, 7))
 *
 * Additionally, it tracks the frequency of the elements in a [Map]. Therefore, the method [contains] has a constant runtime.
 *
 * Reading of entries at an arbitrary index is allowed, which is not a regular property of a stack. In this sense,
 * this data structure is a hybrid of stack and list, because removing of values still is only allowed at the end of the list,
 * like in a regular stack.
 *
 * @param T The type of the elements stored in this object.*/
class SegmentedList<T>() {

    /**Stores the frequency of all elements for constant runtime of [contains]*/
    private val freq = HashMap<T, Int>().withDefault { 0 }

    /**Tracks the size and order of the segments */
    private val segmentStack: Deque<Int> = LinkedList()

    /**The list that actually stores which element is at which position*/
    val list: MutableList<T> = ArrayList()

    val size: Int get() = list.size

    /**Stores all the given values in one *segment* */
    constructor(col: Collection<T>) : this() {
        addAll(col)
    }

    operator fun get(index: Int): T = list[index]

    /** **True** iff the [SegmentedList] contains [elem]. Runtime is constant */
    operator fun contains(elem: T): Boolean = freq.getValue(elem) > 0

    /**Adds the element to to the end of the list and creates a new segment for it*/
    fun add(elem: T): Unit = segmentStack.push(1).also { updateListAndFreq(elem) }

    /**Appends all [col] to the stack in *one* segment.*/
    fun addAll(col: Collection<T>): Unit =
            segmentStack.push(col.size).also { col.forEach { updateListAndFreq(it) } }

    /**removes the last segment. Example: ((1), (5, 3), (6, 4, 3)) -> ((1), (5, 3))*/
    fun removeLastSegment(): Unit = repeat(segmentStack.pop()) {
        freq[list.last()] = freq[list.last()]!! - 1     //!! is safe because the element was present
        list.removeAt(size - 1)
    }

    private fun updateListAndFreq(element: T) {
        freq[element] = freq.getValue(element) + 1
        list.add(element)
    }
}