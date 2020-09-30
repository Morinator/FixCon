package de.umr.core.dataStructures

class SetStack<T> {

    private val elements = HashSet<T>()
    private val stack = ArrayDeque<MutableSet<T>>()

    /**@return *True* iff any subset contains [t]*/
    operator fun contains(t: T): Boolean = t in elements

    val size: Int get() = elements.size
    val stackSize: Int get() = stack.size

    private fun addingUtil(col: Collection<T>) {
        require(col.all { it !in elements }) { "At least one of the elements is already present." }
        elements.addAll(col)
    }

    fun addToLast(col: Collection<T>) {
        addingUtil(col)
        stack.last().addAll(col)
    }

    fun push(col: Collection<T>) {
        addingUtil(col)
        stack.addLast(col.toMutableSet())
    }

    fun removeLast() {
        if (stack.isNotEmpty()) elements.removeAll(stack.removeLast())
    }

    fun clear() {
        elements.clear()
        stack.clear()
    }
}