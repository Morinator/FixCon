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

    /**Adds [newElem] in a new subset, that then only contains [newElem].*/
    operator fun plusAssign(newElem: T) {
        if (!contains(newElem)) m[newElem] = hashSetOf(newElem)
    }

    operator fun plusAssign(elements: Collection<T>) {
        val someElement = elements.first()
        this += someElement
        elements.forEach { addToSubset(someElement, it) }
    }

    /**Adds [newElem] to the subset [oldElem] is already in
     */
    fun addToSubset(oldElem: T, newElem: T) {
        if (!contains(newElem)) {
            m[oldElem]!!.add(newElem)
            m[newElem] = m[oldElem]!!
        }
    }

    operator fun minusAssign(elem: T) {
        m[elem]?.remove(elem)
        m.remove(elem)
    }

    operator fun minusAssign(elements: Collection<T>) {
        elements.forEach { this -= it }
    }

    fun removeSubset(elem: T) {
        m[elem]!!.toList().forEach { this -= it }
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
    fun addByEQPredicate(col: Collection<T>, eqPredicate: (a: T, b: T) -> Boolean) {
        val representatives: MutableSet<T> = HashSet()
        for (elem in col) {
            val equalElem: T? = representatives.firstOrNull { eqPredicate(elem, it) }
            if (equalElem == null) {
                this += elem
                representatives.add(elem)
            } else addToSubset(equalElem, elem)
        }
    }

    /**Adds all subsets of [other] as new subsets.
     * This methods requires the elements of [other] to be disjoint from
     * the element in this <=> [other] may not contain an element that is already saved in this object.*/
    fun disjointUnion(other: SetPartitioning<T>) {
        other.elements.forEach { require(!contains(it)) }
        other.subsets.forEach { plusAssign(it) }
    }
}