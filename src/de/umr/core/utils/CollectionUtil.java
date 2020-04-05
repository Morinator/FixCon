package de.umr.core.utils;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public final class CollectionUtil {

    private CollectionUtil() { //is not supposed to be instantiated
    }


    /**
     * @param setOfSets
     * @return prettyString of the input-set
     */
    static String string_SetOfSets(final Set<Set<Integer>> setOfSets) {
        return setOfSets.stream()
                .map(i -> i.stream().sorted().map(String::valueOf).collect(joining(" ")))
                .collect(joining("\n"));
    }

    /**
     * removes the last N elements from the list. Throws indexOutOfBoundsExceptions if N is larger than list size
     */
    public static void list_remove_lastN(final List input_list, final int n) {
        if (n < 0) throw new IllegalArgumentException("Cannot delete a negative amount of elements");
        for (int i = 0; i < n; i++)
            input_list.remove(input_list.size() - 1);
    }
}
