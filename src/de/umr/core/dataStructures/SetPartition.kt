package de.umr.core.dataStructures

/**This data structure represents a set *S* that is partitioned.
 * This means *S* is divided into subsets that are mutually disjoint, but the union
 * of all the subset is *S*.
 * One potential reason a partition of a set might arise is an equivalence relation.
 * In this case each subset of *S* consists of elements that are *equal* by the relation.
 *
 * @param T The type of the elements.
 */
class SetPartition<T> {

    /** Maps each element to the subset it is partitioned in.*/
    private val m = LinkedHashMap<T, MutableSet<T>>()

    /**Immutable set that is the union of every subset.*/
    fun elements(): Set<T> = m.keys

    fun size(): Int = elements().size

    fun subsets() = m.values

    /**@return *True* iff any subset contains [t]*/
    operator fun contains(t: T): Boolean = t in elements()

    /**@return The subset the element [t] is in, throws exception if [t] is not in any subset.*/
    operator fun get(t: T): Set<T> = m[t]!!

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
    else {
        m[newElem] = hashSetOf(newElem)
        true
    }

    fun removeElem(elem: T) = if (elem !in elements()) false
    else {
        m[elem]!!.remove(elem)
        m.remove(elem)
        true
    }

    fun removeSubset(elem: T) = if (elem !in elements()) false
    else {
        val badElements = m[elem]!!.toSet()
        badElements.forEach {
            m[it]!!.remove(it)
            m.remove(it)
        }
        true
    }
}

fun <T> SetPartition<T>.addByEQPredicate(col: Collection<T>, eqPredicate: (a: T, b: T) -> Boolean) {
    val alreadyAdded: MutableSet<T> = HashSet()
    for (elem in col) {
        val equalElem: T? = alreadyAdded.firstOrNull { eqPredicate(elem, it) }
        if (equalElem == null) {
            addInNewSubset(elem)
            alreadyAdded.add(elem)
        } else addToSubset(equalElem, elem)
    }
}