package de.umr.core.dataStructures

import de.umr.core.extensions.removeLast
import java.util.*
import kotlin.collections.ArrayList

/**This data structure implements a list the is partitioned into individual parts called segments.
 * This data structure is called [SegmentedList] because it consists of segments which can contain multiple elements,
 * and only the last segment can be popped at any given time.
 *
 * The example ((1, 2), (4), (6, 5, 4)) contains 3 segments. The first segment is (1, 2), the second segment
 * is (4) and the third segment is (6, 5, 4).
 *
 * The indexing is continuous, meaning that the entry at index *4* in the example is 5.
 * Reading of entries at an arbitrary index is allowed, which is not a regular property of a stack. In this sense,
 * this data structure is a hybrid of stack and list, because removing of values still is only allowed at the end of the list,
 * like in a regular stack.
 *
 * Additionally, it tracks the frequency of the elements in a [Map]. Therefore, the method [contains] has a constant runtime.
 *
 * @param T The type of the elements stored in this object.*/
class SegmentedList<T> {

    /**Stores the frequency of all elements for constant runtime of [contains]*/
    private val freq = HashMap<T, Int>().withDefault { 0 }

    /**Tracks the size and order of the segments */
    private val segmentStack = ArrayDeque<Int>()

    /**The list that actually stores which element is at which position*/
    private val list = ArrayList<T>()

    /**Immutable view of [list] to the outside.*/
    val listView: List<T> get() = list

    val size: Int get() = list.size

    operator fun get(index: Int): T = list[index]

    /**@return **True** iff the [SegmentedList] contains [elem]. Runtime is constant */
    operator fun contains(elem: T): Boolean = freq.getValue(elem) > 0       //uses default value 0

    /**Adds the element to to the end of the list and creates a new segment for it
     *
     * Example: ((1, 2), (3)).add(5) is ((1, 2), (3), (5))*/
    operator fun plusAssign(elem: T) = plusAssign(listOf(elem))

    /**Appends all elements of [col] to the stack in *one* segment.
     *
     * Example: ((1, 2), (3)).addAll(listOf(5, 4, 7)) is ((1, 2), (3), (5, 4, 7))*/
    operator fun plusAssign(col: Collection<T>) {
        segmentStack.push(col.size)
        col.forEach { freq[it] = freq.getValue(it) + 1;list.add(it) }
    }

    /**removes the last segment. Example: ((1), (5, 3), (6, 4, 3)) -> ((1), (5, 3))*/
    fun removeLastSegment(): Unit = repeat(segmentStack.pop()) {
        freq[list.last()] = freq[list.last()]!! - 1
        list.removeLast()
    }
}