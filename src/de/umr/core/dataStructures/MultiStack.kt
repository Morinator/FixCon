package de.umr.core.dataStructures

import java.util.*

/**
 * This data structure implements a partitioned list. The individual parts are called segments.
 *
 * The example ((1, 2), (4), (6, 5, 4)) contains 3 segments, with the first segment being (1, 2) and the
 * third segment being (6, 5, 4).
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
 * This data structure is called [MultiStack] because it consists of segments which can contain multiple elements, and only
 * the last segment can be popped at any given time.
 *
 * Reading of entries at an arbitrary index is allowed, which is not a regular property of a stack. In this sense,
 * this data structure is a hybrid of stack and list, because removing of values still is only allowed at the end of the list,
 * like in a regular stack.
 */
class MultiStack<E>() : ArrayList<E>() {

    /**stores the frequency of all elements for constant runtime of [contains]*/
    private val freq = HashMap<E, Int>()

    /**enables the use of [removeLastSegment] */
    private val segmentStack = LinkedList<Int>()

    /**The constructor stores all the given values into one *segment* */
    constructor(x: Collection<E>) : this() {
        addAll(x)
    }

    /**adds the element to to the end of the list and creates a new segment for it*/
    override fun add(element: E): Boolean {
        segmentStack.push(1)
        return addWithoutNewSegment(element)
    }

    /**This method is only used privately in other methods.
     * It updates the entry in [freq] for [element] und adds the element to the parent [List]*/
    private fun addWithoutNewSegment(element: E): Boolean {
        incrementFreqForElement(element)
        return super.add(element)
    }

    /****True** iff the [MultiStack] contains [element]. Runtime is constant */
    override fun contains(element: E) = freq.getOrDefault(element, 0) > 0

    /**Resets the entire data structure. It is completely empty after the call of [clear], so it contains no segments.*/
    override fun clear() {
        segmentStack.clear()
        freq.clear()
        super.clear()
    }

    /**Sets the value at [index] to [element]
     * @return The element that was previously stored at [index]*/
    override fun set(index: Int, element: E): E {
        decrementFreqForElement(get(index))
        incrementFreqForElement(element)
        return super.set(index, element)
    }

    @Deprecated("This *stack-like* data structure does NOT support removal at arbitrary indices")
    override fun removeAt(index: Int): E {
        decrementFreqForElement(get(index))
        return super.removeAt(index)
    }

    /**Appends all [elements] to the stack in *one* segment.
     *
     * @return True iff the object changed as a result of the call, so iff [elements] was not empty*/
    override fun addAll(elements: Collection<E>): Boolean {
        segmentStack.push(elements.size)
        elements.forEach { addWithoutNewSegment(it) }
        return elements.isNotEmpty()
    }

    /**removes the last segment. Example: ((1), (5, 3), (6, 4, 3)) -> ((1), (5, 3))*/
    fun removeLastSegment() = repeat(segmentStack.pop()) { removeAt(size - 1) }

    /**The value stored for the key [element] in [freq] is increased by 1*/
    private fun incrementFreqForElement(element: E) {
        freq[element] = freq.getOrDefault(element, 0) + 1
    }

    /**The value stored for the key[element] in [freq] is decreased by 1*/
    private fun decrementFreqForElement(element: E) {
        freq[element] = freq[element]!! - 1     //assumes the element was present
    }
}