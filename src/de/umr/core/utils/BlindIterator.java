package de.umr.core.utils;

/**
 * Interface for all iterators where it is not known if it has a next element without actually calculating
 * it and in the process losing the current element.
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
