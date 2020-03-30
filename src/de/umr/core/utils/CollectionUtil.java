package de.umr.core.utils;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class CollectionUtil {

    static String string_SetOfSets(final Set<Set<Integer>> input) {
        return input.stream()
                .map(i -> i.stream().sorted().map(String::valueOf).collect(joining(" ")))
                .collect(joining("\n"));
    }

    public static void change_stack_head(final Deque<Integer> stack, final int change_value) {
        stack.push(stack.pop() + change_value);
    }


    public static void duplicate_stack_head(final Deque<Integer> stack) {
        stack.push(stack.peek());
    }

    /**removes the last N elements from the list. Throws indexOutOfBoundsExceptions if N is larger than list size*/
    public static void list_remove_lastN(final List input_list, final int N) {
        for (int i = 0; i < N; i++)
            input_list.remove(input_list.size()-1);
    }
}
