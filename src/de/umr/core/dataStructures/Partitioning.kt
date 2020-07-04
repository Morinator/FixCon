package de.umr.core.dataStructures

/**
 * @param T The type of the elements.
 */
class Partitioning<T> {

    /** Maps each element to the subset it is partitioned in.*/
    private val m = HashMap<T, MutableSet<T>>()

    /**@return *True* iff any subset contains [t]*/
    fun contains(t: T): Boolean = t in m.keys

    /**@return The subset the element [t] is in, throws exception if [t] is not in any subset.*/
    fun subset(t: T): Set<T> = m[t]!!

    /**Immutable set that is the union of every subset.*/
    val elements: Set<T> get() = m.keys

    /**Adds [newElem] to the subset [oldElem] lies in.
     * @return *True* iff [newElem] was not in any subset yet. */
    fun addToSubset(oldElem: T, newElem: T): Boolean {
        if (contains(newElem)) return false

        m[oldElem]!!.add(newElem)
        m[newElem] = m[oldElem]!!
        return true
    }

    fun addInNewSubset(t: T): Boolean = if (contains(t))
        false
    else {
        m[t] = hashSetOf(t)
        true
    }
}