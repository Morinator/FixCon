package unitTests.core.utils;

import de.umr.core.utils.CollectionUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionUtil_Test {

    @Test
    void string_SetOfSets_size3() {
        Set<Set<Integer>> s = new LinkedHashSet<>();
        s.add(Set.of(1,2, 4, 6, 78));
        s.add(Set.of(11,12,13));
        s.add(Set.of(55,66,77));
        String resultString = CollectionUtil.string_SetOfSets(s);

        //Tests if the inner sets are sorted
        assertTrue(resultString.contains("1 2 4 6 78"));
        assertTrue(resultString.contains("11 12 13"));
        assertTrue(resultString.contains("55 66 77"));
        assertEquals(3, resultString.split("\n").length); //consists of 3 lines
    }

    @Test
    void emptySet_test() {
        Set<Set<Integer>> s = new LinkedHashSet<>();
        assertEquals("", CollectionUtil.string_SetOfSets(s));
    }

    @Test
    void list_remove_lastN_successful() {
        List<Integer> list1 = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        CollectionUtil.list_remove_lastN(list1, 3);
        assertEquals(2, list1.size());
        assertEquals(List.of(1, 2), list1);

        CollectionUtil.list_remove_lastN(list1, 0);
        assertEquals(2, list1.size());
        assertEquals(List.of(1, 2), list1);

        List<Integer> list2 = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        CollectionUtil.list_remove_lastN(list2, 5);
        assertEquals(0, list2.size());
    }

    @Test
    void list_remove_lastN_exceptions() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        assertThrows(IllegalArgumentException.class, () -> CollectionUtil.list_remove_lastN(list, -10));

        assertThrows(IndexOutOfBoundsException.class, () -> CollectionUtil.list_remove_lastN(list, 10));
    }
}