package de.umr.core.utils;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class CollectionUtil {

    static String string_SetOfSets(Set<Set<Integer>> input) {
        return input.stream()
                .map(i -> i.stream().sorted().map(String::valueOf).collect(joining(" ")))
                .collect(joining("\n"));
    }

    public static void change_stack_head(Deque<Integer> stack, int change_value) {
        stack.push(stack.pop() + change_value);
    }

    public static void duplicate_stack_head(Deque<Integer> stack) {
        stack.push(stack.peek());
    }

    public static void list_remove_lastN(List li, int n) {
        for (int i = 0; i < n; i++)
            li.remove(li.size()-1);
    }
}
