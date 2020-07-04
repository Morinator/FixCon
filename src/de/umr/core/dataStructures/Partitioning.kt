package de.umr.core.dataStructures

/**
 * @param T The type of the elements.
 */
class Partitioning<T> {

    /** Maps each element to the subset it is partitioned in.*/
    private val m = HashMap<T, MutableSet<T>>()

    /**@return *True* iff any subset contains [t]*/
    operator fun contains(t: T): Boolean = t in m.keys

    /**@return The subset the element [t] is in, throws exception if [t] is not in any subset.*/
    fun subset(t: T): Set<T> = m[t]!!

    /**Immutable set that is the union of every subset.*/
    val elements: Set<T> get() = m.keys

    /**Adds [newElem] to the subset that [oldElem] lies in.
     *
     * @throws [NullPointerException] if [oldElem] wasn't actually present in any subset.
     * @return *True* iff [newElem] was not present in any subset. */
    fun addToSubset(oldElem: T, newElem: T) = if (contains(newElem)) false
    else {
        m[oldElem]!!.add(newElem)
        m[newElem] = m[oldElem]!!
        true
    }

    /**Adds [newElem] into a new subset.
     * @return *True* iff [newElem] was not present in any subset.*/
    fun addInNewSubset(newElem: T): Boolean = if (contains(newElem)) false
    else true.also { m[newElem] = hashSetOf(newElem) }
}