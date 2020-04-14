package de.umr.core.utils;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.joining;

public final class CollectionUtil {

    private CollectionUtil() { //is not supposed to be instantiated
    }

    /**
     * @param setOfSets The set of sets that is supposed to be formatted in a string
     * @return prettyString of the input-set
     */
    public static String string_SetOfSets(final Set<Set<Integer>> setOfSets) {
        return setOfSets.stream()
                .map(i -> i.stream().sorted().map(String::valueOf).collect(joining(" ")))
                .collect(joining("\n"));
    }

    /**
     * removes the last N elements from the list. Throws indexOutOfBoundsExceptions if N is larger than list size
     */
    public static <T> void list_remove_lastN(final List<T> input_list, final int n) {
        checkArgument(n >= 0);
        for (int i = 0; i < n; i++)
            input_list.remove(input_list.size() - 1);
    }
}
