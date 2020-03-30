package de.umr.core.utils;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubSet_Iterator_Test {

    @Test
    void main_test() {
        Set<Set<Integer>> correct_result = new LinkedHashSet<>( Set.of(Set.of(1, 2), Set.of(1,3), Set.of(1, 4), Set.of(2, 3), Set.of(2, 4), Set.of(3, 4)));
        Set<Set<Integer>> actual_result = new LinkedHashSet<>();

        SubSet_Iterator s = new SubSet_Iterator(Set.of(1,2,3,4), 2);
        while (s.hasNext())
            actual_result.add(s.next());

        assertEquals(correct_result, actual_result);
    }

    @Test
    void k_equals_n_test() {
        Set<Set<Integer>> correct_result = new LinkedHashSet<>( Set.of(Set.of(1, 2) ));
        Set<Set<Integer>> actual_result = new LinkedHashSet<>();

        SubSet_Iterator s = new SubSet_Iterator(Set.of(1, 2), 2);
        while (s.hasNext())
            actual_result.add(s.next());

        assertEquals(correct_result, actual_result);
    }
}