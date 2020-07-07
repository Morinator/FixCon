package de.umr.core.dataStructures

/**This data structure represents a set *S* that is partitioned.
 * This means *S* is divided into subsets that are mutually disjoint, but the union
 * of all the subset is *S*.
 * One potential reason a partition of a set might arise is an equivalence relation.
 * In this case each subset of *S* consists of elements that are *equal* by the relation.
 *
 * @param T The type of the elements.
 */
class SetPartitioning<T> {

    /** Maps each element to the subset it is partitioned in.*/
    private val m = LinkedHashMap<T, MutableSet<T>>()

    /**Immutable set that is the union of every subset.*/
    val elements: Set<T> get() = m.keys

    val size: Int get() = elements.size

    val subsets get() = m.values

    /**@return *True* iff any subset contains [t]*/
    operator fun contains(t: T): Boolean = t in elements

    /**@return The subset the element [t] is in, throws exception if [t] is not in any subset.*/
    operator fun get(t: T): Set<T> = m[t]!!

    /**Adds [newElem] to the subset that [oldElem] lies in.
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

    fun removeElem(elem: T) = if (!contains(elem)) false
    else {
        m[elem]!!.remove(elem)
        m.remove(elem)
        true
    }

    fun removeSubset(elem: T) = if (!contains(elem)) false
    else {
        m[elem]!!.toList().forEach {
            m[it]!!.remove(it)
            m.remove(it)
        }
        true
    }
}

/**Adds all elements from [col] into this data structure. Elements for which [eqPredicate] returns true are
 * added into the same subset. Note that equality of elements in [col] to already added elements in this obejct
 * are NOT detected.
 * In other words: If *a* is already in this [SetPartitioning], and *b* is in [col] and [eqPredicate] evaluates to *true*
 * for *a* and *b*, then *b* is still not added to the subset *a* is in.
 *
 *
 * @param col The collection of elements which are added
 * @param eqPredicate Returns true if two elements from [col] should be regarded as equal in some sense
 */
fun <T> SetPartitioning<T>.addByEQPredicate(col: Collection<T>, eqPredicate: (a: T, b: T) -> Boolean) {
    val representatives: MutableSet<T> = HashSet()
    for (elem in col) {
        val equalElem: T? = representatives.firstOrNull { eqPredicate(elem, it) }
        if (equalElem == null) {
            addInNewSubset(elem)
            representatives.add(elem)
        } else addToSubset(equalElem, elem)
    }
}