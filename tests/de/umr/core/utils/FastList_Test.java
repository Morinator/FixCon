package de.umr.core.utils;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

class FastList_Test {

    private static int SIZE = 10_000_000;
    private static int timeThreshold = 10; //in nanoseconds

    @Test
    void contain_speedTest() {
        List<Integer> arrayList = IntStream.range(0, SIZE).boxed().collect(toList());
        assertTrue(testTime(arrayList, SIZE-1) > timeThreshold);

        List<Integer> fastList = IntStream.range(0, SIZE).boxed().collect(toCollection(FastList::new));
        assertTrue(testTime(fastList, SIZE - 1) < timeThreshold/2);

        Set<Integer> set = IntStream.range(0, SIZE).boxed().collect(toSet());
        assertTrue(testTime(set, SIZE - 1) < timeThreshold/2);
    }

    private static long testTime(Collection col, int search_val) {
        long before = currentTimeMillis();
        assertTrue(col.contains(search_val));
        return currentTimeMillis() - before;
    }

    @Test
    void iterator_test() {
        List<Integer> fastList = IntStream.range(0, 5).boxed().collect(toList());
        Iterator<Integer> list_iterator = fastList.iterator();
        assertTrue(list_iterator.hasNext());
        assertEquals(0, list_iterator.next());
        assertEquals(1, list_iterator.next());
        assertEquals(2, list_iterator.next());
        assertEquals(3, list_iterator.next());
        assertEquals(4, list_iterator.next());
        assertFalse(list_iterator.hasNext());
    }
}