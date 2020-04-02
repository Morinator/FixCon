package de.umr.core.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionUtil_Test {

    @Test
    void string_SetOfSets() {
        Set<Set<Integer>> s = new LinkedHashSet<>();
        s.add(Set.of(1,2, 4, 6, 78));
        s.add(Set.of(11,12,13));
        s.add(Set.of(55,66,77));

        System.out.println(CollectionUtil.string_SetOfSets(s));
    }
}