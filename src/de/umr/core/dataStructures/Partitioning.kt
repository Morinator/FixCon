package de.umr.core.dataStructures

/**
 * @param T The type of the elements.
 */
class Partitioning<T> {

    /** Maps each element to the subsets it is partitioned in.*/
    private val m = HashMap<T, MutableSet<T>>()

    fun contains(t: T): Boolean = t in m.keys

    fun getPart(t: T): Set<T> = m[t]!!

    val allElements: Set<T> get() = m.keys

    fun addToPart(oldElem: T, newElem: T): Boolean {
        val isNew = newElem in m[oldElem]!!

        m[oldElem]!!.add(newElem)
        m[newElem] = m[oldElem]!!

        return isNew
    }

    fun addInNewPart(t: T): Boolean = if (t in m.keys)
        false
    else {
        m[t] = hashSetOf(t)
        true
    }
}