package de.umr.fixcon.itarators

/**
 * Interface for all iterators that always return the same object, but change it in order to return the next value.
 * Therefore, the object should not be changed unintentionally, because that would result in unwanted side-effects.
 * The way this iterator works prevents the use of "hasNext()" as this would have to calculate the next value for
 * the object, but this method is not expected to have side-effects. The only method that changes the object
 * is mutate(), which also is its only purpose.
 *
 * The standard pattern for using this interface would be:
 * while (iter.isValid()) {
 * doSomethingWith(iter.current());
 * iter.mutate();
 * }
 */
interface GraphIterator<T> {
    /**
     * @return True iff the current element is a valid return.
     */
    val isValid: Boolean

    /**
     * @return Returns the current element, but does NOT generate the next element. This method can be called
     * as often as wanted, without any side-effects.
     */
    fun current(): T

    /**Generates the next element, which can then be retrieved with current(). This method thus only provides
     * this side-effect. If it is called while the current element is invalid, it may produce and exception,
     * depending on the implementation on the iterator.
     */
    fun mutate()
}