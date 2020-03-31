package de.umr.core.utils;


import java.util.*;
import java.util.stream.IntStream;

/**
 * Iterates through all fixed-size subsets of a given finite set.
 * Each of the returned subsets has the type Set<Integer>
 */
public class SubSet_Iterator implements Iterator<Set<Integer>> {

    private boolean hasNext = true;
    private List<Integer> input_values;     //stored as list for more convenient iteration
    private int[] pointers;

    SubSet_Iterator(Set<Integer> input_values, int subset_size) {
        if (subset_size > input_values.size()) throw new InputMismatchException();
        this.input_values = new ArrayList<>(input_values);
        pointers = IntStream.range(0, subset_size).toArray();   //[0, 1, 2, ..., subset_size-1]
    }

    @Override
    public boolean hasNext() {
        return hasNext; }

    @Override
    public Set<Integer> next() {
        if(!hasNext) throw new NoSuchElementException();

        // generate new subset from current pointers
        Set<Integer> next_subset = new LinkedHashSet<>();
        Arrays.stream(pointers).forEach( x -> next_subset.add(input_values.get(x)) );

        update_pointers_and_hasNext();
        return next_subset;
    }

    private void update_pointers_and_hasNext() {
        hasNext = false;

        for (int i = pointers.length - 1; i >= 0 & !hasNext; i--) {

            int offset_right = pointers.length-1 - i;
            if (pointers[i] < input_values.size() - 1 - offset_right) {

                int start_val = pointers[i];
                for (int j = i; j < pointers.length; j++) {
                    int additional_shift = j - i;
                    pointers[j] = start_val + 1 + additional_shift;
                }

                hasNext = true;
            }
        }
    }
}
