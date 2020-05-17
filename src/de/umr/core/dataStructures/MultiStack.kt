package de.umr.core.dataStructures

import java.util.*

/**
 * This data structure implements a continuous list of lists, called a [MultiStack].
 * The example ((1, 2), (4), (6, 5, 4)) contains of 3 segments, with the third segment being (6, 5, 4).
 * The indexing is continuous, meaning that the entry at index *4* in the example is 5.
 *
 * [add] creates a new sublist for the single element, [addAll] creates one sublist for all the new elements.
 *
 * Additionally, it tracks the frequency of the elements in a [HashMap]. Therefore, [contains] has a runtime of **O(1)**.
 */
class MultiStack<E>() : ArrayList<E>() {

    /**frequency of all elements for constant runtime of [contains]*/
    private val freq = HashMap<E, Int>()

    /**enables the use of [removeLastSegment] */
    private val segmentStack = LinkedList<Int>()

    /**stores all these values into one *segment* */
    constructor(x: Collection<E>) : this() {
        addAll(x)
    }

    /**adds the element to to the end of the list and creates a new segment for it*/
    override fun add(element: E): Boolean {
        segmentStack.push(1)
        return addWithoutNewSegment(element)
    }

    /**only used in other methods. Updates the frequency-map und adds the element to the parent list*/
    private fun addWithoutNewSegment(element: E): Boolean {
        incrementFreqForElement(element)
        return super.add(element)
    }

    /****True** iff the [MultiStack] contains the element. Runtime is *O(1)* */
    override fun contains(element: E) = freq.getOrDefault(element, 0) > 0

    /**resets the entire data structure. It is completely empty after the call of [clear]*/
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

    /**Appends all [elements] to the stack*/
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