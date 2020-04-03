package de.umr.core.utils;

/**
 * Interface for all iterators that always return the same object, but change it in order to return the next value.
 * Therefore, the object should not be changed unintentionally, because that would result in unwanted side-effects.
 * This prevents the use of "hasNext()" as this would have to calculate the next value for the object, but this method
 * is not expected to have side-effects. The only method that changes the object is generateNext(), which also is
 * its only purpose.
 *
 * The standard pattern for using this interface would be:
 * while (iter.hasCurrent()) {
 *     doSomethingWith(iter.getCurrent());
 *     iter.generateNext();
 * }
 */
public interface BlindIterator<T> {

    /**
     * @return True iff the current element is a valid return.
     */
    boolean hasCurrent();

    /**
     * @return Returns the current element, but does NOT generate the next element. This method can be called
     * as often as wanted, without any side-effects.
     */
    T getCurrent();

    /**Generates the next element, which can then be retrieved with getCurrent(). This method thus only provides
     * this side-effect. If it is called while the current element is invalid, it may produce and exception,
     * depending on the implementation on the iterator.
     */
    void generateNext();
}
