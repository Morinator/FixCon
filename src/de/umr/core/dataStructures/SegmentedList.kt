package de.umr.core.dataStructures

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

    private val freq = HashMap<T, Int>().withDefault { 0 }
    val segmentList = ArrayList<Int>()
    private val elementList = ArrayList<T>()

    val listView: List<T> get() = elementList
    val size: Int get() = elementList.size

    operator fun get(index: Int): T = elementList[index]

    /**Has constant runtime*/
    operator fun contains(elem: T): Boolean = freq.getValue(elem) > 0       //uses default value 0

    /**Adds the element to to the end of the list and creates a new segment for it*/
    operator fun plusAssign(elem: T) = plusAssign(listOf(elem))

    /**Appends all elements of [col] to the stack in *one* segment.*/
    operator fun plusAssign(col: Collection<T>) {
        segmentList.add(col.size + if (elementList.size > 0) segmentList.last() else 0)
        for (elem in col) {
            freq[elem] = freq.getValue(elem) + 1    //use default-value of 0
            elementList.add(elem)
        }
    }

    fun removeLastSegment() {
        repeat(segmentList.last() - if (segmentList.size >= 2) segmentList[segmentList.size - 2] else 0) {
            freq[elementList.last()] = freq[elementList.last()]!! - 1
            elementList.removeAt(elementList.size - 1)    //remove last element
        }
        segmentList.removeAt(segmentList.size - 1)  //remove last element
    }
}