package de.umr.core.utils;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FastList_Test {

    private static int SIZE = 10_000_000;
    private static int timeThreshold = 10; //in nanoseconds

    @Test
    void contain() {
        List<Integer> arrayList = new ArrayList<>(IntStream.range(0, SIZE).boxed().collect(toList()));
        assertTrue(testTime(arrayList, SIZE-1) > timeThreshold);

        List<Integer> fastList = new FastList<>(IntStream.range(0, SIZE).boxed().collect(toList()));
        assertTrue(testTime(fastList, SIZE - 1) < timeThreshold/2);

        Set<Integer> set = new HashSet<>(IntStream.range(0, SIZE).boxed().collect(toList()));
        assertTrue(testTime(set, SIZE - 1) < timeThreshold/2);
    }

    private static long testTime(Collection col, int search_val) {
        long before = currentTimeMillis();
        assertTrue(col.contains(search_val));
        return currentTimeMillis() - before;
    }
}