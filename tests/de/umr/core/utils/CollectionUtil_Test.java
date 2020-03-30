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

    @Test
    void change_stack_head() {
        Deque<Integer> dq = new ArrayDeque<>();
        dq.push(3);
        dq.push(5);
        CollectionUtil.change_stack_head(dq, 1);
        assertEquals(6, dq.peek());
        CollectionUtil.change_stack_head(dq, -3);
        assertEquals(3, dq.peek());
        dq.poll();
        CollectionUtil.change_stack_head(dq, 8);
        assertEquals(11, dq.peek());
    }

    @Test
    void duplicate_stack_head() {
        Deque<Integer> dq = new ArrayDeque<>();
        dq.push(3);
        CollectionUtil.duplicate_stack_head(dq);
        assertEquals(3, dq.poll());
        assertEquals(3, dq.poll());
        assertEquals(0, dq.size());

        dq.push(1);
        CollectionUtil.duplicate_stack_head(dq);
        dq.push(2);
        CollectionUtil.duplicate_stack_head(dq);
        assertEquals(2, dq.poll());
        assertEquals(2, dq.poll());
        assertEquals(1, dq.poll());
        assertEquals(1, dq.poll());
        assertEquals(0, dq.size());
    }

    @Test
    void list_remove_lastN() {
    }
}