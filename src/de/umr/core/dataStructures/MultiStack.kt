package de.umr.core.dataStructures

import java.util.*

class MultiStack<E>() : ArrayList<E>() {

    private val freq = HashMap<E, Int>()
    private val segmentStack = LinkedList<Int>()

    constructor(x: Collection<E>) : this() {
        addAll(x)
    }

    override fun add(element: E): Boolean {
        segmentStack.push(1)
        return addWithoutSegmentEffect(element)
    }

    private fun addWithoutSegmentEffect(element: E) : Boolean {
        incrementFreqForElement(element)
        return super.add(element)
    }

    override fun contains(element: E) = freq.getOrDefault(element, 0) > 0

    override fun clear() {
        segmentStack.clear()
        freq.clear()
        super.clear()
    }

    override fun set(index: Int, element: E): E {
        decrementFreqForElement(get(index))
        incrementFreqForElement(element)
        return super.set(index, element)
    }

    override fun removeAt(index: Int): E {
        decrementFreqForElement(get(index))
        return super.removeAt(index)
    }

    override fun addAll(elements: Collection<E>): Boolean {
        segmentStack.push(elements.size)
        elements.forEach { addWithoutSegmentEffect(it) }
        return elements.isNotEmpty()
    }

    fun removeTailSegment() = repeat(segmentStack.pop()) { removeAt(size-1)}

    private fun incrementFreqForElement(element: E) {
        freq[element] = freq.getOrDefault(element, 0) + 1
    }

    private fun decrementFreqForElement(element: E) {
        freq[element] = freq[element]!! - 1     //assumes the element was present
    }
}