package de.umr.core.utils

import java.util.*

class ExtensionList<E>() : ArrayList<E>() {

    private val freq = HashMap<E, Int>()

    constructor(x: Collection<E>) : this() {
        x.forEach { add(it) }
    }


    override fun add(element: E): Boolean {
        incrementFreqForElement(element)
        return super.add(element)
    }

    override fun contains(element: E) = freq.getOrDefault(element, 0) > 0

    override fun clear() {
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
        elements.forEach { add(it) }
        return elements.isNotEmpty()
    }

    fun removeFromEnd(n: Int) {
        if (n > size) throw Exception("Can't remove $n elents from a list of size $size")
        repeat(n) { removeAt(size - 1) }
    }

    private fun incrementFreqForElement(element: E) {
        freq[element] = freq.getOrDefault(element, 0) + 1
    }

    private fun decrementFreqForElement(element: E) {
        freq[element] = freq[element]!! - 1     //assumes the element was present
    }
}