package de.umr.core;

import com.google.common.collect.BiMap;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.umr.core.Factory.edges_from_path;
import static de.umr.core.Factory.graph_from_NetworkRepo;
import static de.umr.core.Factory.vertex_to_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Factory_Test {

    @Test
    void integrationTest() {
        MutableGraph<Integer> g = graph_from_NetworkRepo(Objects.requireNonNull(edges_from_path(".//graph_files//sample")));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertFalse(g.hasEdgeConnecting(1, 3));
        assertTrue(g.hasEdgeConnecting(2, 3));
        assertFalse(g.hasEdgeConnecting(2, 4));
        assertTrue(g.hasEdgeConnecting(8, 9));
        assertFalse(g.hasEdgeConnecting(8, 10));
        assertTrue(g.hasEdgeConnecting(8, 12));
        assertTrue(g.hasEdgeConnecting(15, 16));
        assertTrue(g.hasEdgeConnecting(15, 17));
        assertFalse(g.hasEdgeConnecting(15, 18));
    }

    @Test
    void vertex_to_ID_Test1() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"bli", "bla"});
        list.add(new String[]{"a", "b"});
        list.add(new String[]{"hund", "katze"});

        BiMap<String, Integer> biMap = vertex_to_ID(list);
        assertEquals(0, biMap.get("bli"));
        assertEquals(1, biMap.get("bla"));
        assertEquals(2, biMap.get("a"));
        assertEquals(3, biMap.get("b"));
        assertEquals(4, biMap.get("hund"));
        assertEquals(5, biMap.get("katze"));

        assertEquals(biMap.inverse().get(0), "bli");
        assertEquals(biMap.inverse().get(1), "bla");
        assertEquals(biMap.inverse().get(2), "a");
        assertEquals(biMap.inverse().get(3), "b");
        assertEquals(biMap.inverse().get(4), "hund");
        assertEquals(biMap.inverse().get(5), "katze");
    }

    @Test
    void edge_with_length_not_2() {
        List<String[]> stringList = new ArrayList<>();
        stringList.add(new String[]{"bli", "bla"});
        stringList.add(new String[]{"a", "b", "c"});
        stringList.add(new String[]{"hund", "katze"});

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> vertex_to_ID(stringList));
        assertEquals(exception.getMessage(), "All elements must have 2 elements to represent an edge");

        List<Integer[]> intList = new ArrayList<>();
        intList.add(new Integer[]{1, 2, 3});
        intList.add(new Integer[]{6, 16});
        intList.add(new Integer[]{6, 9});

        exception =  assertThrows(IllegalArgumentException.class, () -> graph_from_NetworkRepo(intList));
        assertEquals(exception.getMessage(), "All elements must have 2 elements to represent an edge");
    }
}