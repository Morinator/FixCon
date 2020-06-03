package de.umr.core.dataStructures

import java.util.*
import kotlin.collections.ArrayList

/**
 * This data structure implements a list the is partitioned into individual parts called segments.
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
 */
class SegmentedList<E>() {

    /**stores the frequency of all elements for constant runtime of [contains]*/
    private val freq = HashMap<E, Int>().withDefault { 0 }

    /**enables the use of [removeLastSegment] */
    private val segmentSizeStack: Deque<Int> = LinkedList()

    val list: MutableList<E> = ArrayList()

    /**The constructor stores all the given values into one *segment* */
    constructor(x: Collection<E>) : this() {
        addAll(x)
    }

    /**adds the element to to the end of the list and creates a new segment for it*/
    fun add(element: E) {
        segmentSizeStack.push(1)
        addWithoutNewSegment(element)
    }

    operator fun plusAssign(element: E) = add(element)

    /**Appends all [elements] to the stack in *one* segment.
     *
     * @return True iff the object changed as a result of the call, so iff [elements] was not empty*/
    fun addAll(elements: Collection<E>) {
        segmentSizeStack.push(elements.size)
        elements.forEach { addWithoutNewSegment(it) }
    }

    operator fun plusAssign(elements: Collection<E>) = addAll(elements)

    private fun addWithoutNewSegment(element: E){
        freq[element] = freq.getValue(element) + 1
        list.add(element)
    }

    /** **True** iff the [SegmentedList] contains [element]. Runtime is constant */
    fun contains(element: E) = freq.getValue(element) > 0

    /**removes the last segment. Example: ((1), (5, 3), (6, 4, 3)) -> ((1), (5, 3))*/
    fun removeLastSegment() = repeat(segmentSizeStack.pop()) {
        freq[list.last()] = freq[list.last()]!! - 1     //!! is safe because the element was present
        list.removeAt(size - 1)
    }

    operator fun get(index: Int) = list[index]

    val size: Int get() = list.size
}