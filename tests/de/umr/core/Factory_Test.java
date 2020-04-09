package de.umr.core;

import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Factory_Test {

    @Test
    void integrationTest() throws IOException {
        MutableGraph<Integer> g = graphFromNetworkRepo(".//graph_files//sample");
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
    void createClique_Test() {
        MutableGraph<Integer> g = createClique(1);
        System.out.println(g.nodes().isEmpty());

        g = createClique(3);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(0, 2));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertFalse(g.hasEdgeConnecting(1, 3));

        g = createClique(4);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(0, 2));
        assertTrue(g.hasEdgeConnecting(0, 3));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertTrue(g.hasEdgeConnecting(1, 3));
        assertTrue(g.hasEdgeConnecting(2, 3));
        assertFalse(g.hasEdgeConnecting(1, 4));
        assertFalse(g.hasEdgeConnecting(1, 123));
    }

    @Test
    void createCircle_Test() {
        assertThrows(IllegalArgumentException.class, () -> createCircle(1));

        MutableGraph<Integer> g = createCircle(3);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertTrue(g.hasEdgeConnecting(2, 0));
        assertFalse(g.hasEdgeConnecting(1, 3));

        g = createCircle(4);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertTrue(g.hasEdgeConnecting(2, 3));
        assertTrue(g.hasEdgeConnecting(3, 0));
        assertFalse(g.hasEdgeConnecting(1, 4));
        assertFalse(g.hasEdgeConnecting(1, 123));
    }

    @Test
    void createPath_Test() {
        MutableGraph<Integer> g = createPath(1);
        assertTrue(g.nodes().isEmpty());

        g = createPath(3);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertFalse(g.hasEdgeConnecting(2, 0));
        assertFalse(g.hasEdgeConnecting(1, 3));

        g = createPath(4);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(1, 2));
        assertTrue(g.hasEdgeConnecting(2, 3));
        assertFalse(g.hasEdgeConnecting(3, 0));
        assertFalse(g.hasEdgeConnecting(1, 4));
        assertFalse(g.hasEdgeConnecting(1, 123));
    }

    @Test
    void createStar_Test() {
        MutableGraph<Integer> g = createStar(1);
        assertTrue(g.nodes().isEmpty());

        g = createStar(3);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(0, 2));
        assertFalse(g.hasEdgeConnecting(1, 2));
        assertFalse(g.hasEdgeConnecting(0, 3));

        g = createStar(4);
        assertTrue(g.hasEdgeConnecting(0, 1));
        assertTrue(g.hasEdgeConnecting(0, 2));
        assertTrue(g.hasEdgeConnecting(0, 3));
        assertFalse(g.hasEdgeConnecting(1, 3));
        assertFalse(g.hasEdgeConnecting(0, 4));
        assertFalse(g.hasEdgeConnecting(1, 123));
    }
}