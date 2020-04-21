package unitTests.core;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import static de.umr.core.StandardGraphFactoryKt.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandardGraphFactory_Test {

    @Test
    void createClique_Test() {
        assertThrows(IllegalArgumentException.class, () -> createClique(1));

        Graph<Integer, DefaultEdge> g = createClique(3);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(0, 2));
        assertTrue(g.containsEdge(1, 2));
        assertFalse(g.containsEdge(1, 3));

        g = createClique(4);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(0, 2));
        assertTrue(g.containsEdge(0, 3));
        assertTrue(g.containsEdge(1, 2));
        assertTrue(g.containsEdge(1, 3));
        assertTrue(g.containsEdge(2, 3));
        assertFalse(g.containsEdge(1, 4));
        assertFalse(g.containsEdge(1, 123));
    }

    @Test
    void createCircle_Test() {
        assertThrows(IllegalArgumentException.class, () -> createCircle(1));

        Graph<Integer, DefaultEdge> g = createCircle(3);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(1, 2));
        assertTrue(g.containsEdge(2, 0));
        assertFalse(g.containsEdge(1, 3));

        g = createCircle(4);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(1, 2));
        assertTrue(g.containsEdge(2, 3));
        assertTrue(g.containsEdge(3, 0));
        assertFalse(g.containsEdge(1, 4));
        assertFalse(g.containsEdge(1, 123));
    }

    @Test
    void createPath_Test() {
        assertThrows(IllegalArgumentException.class, () -> createPath(1));

        Graph<Integer, DefaultEdge> g = createPath(3);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(1, 2));
        assertFalse(g.containsEdge(2, 0));
        assertFalse(g.containsEdge(1, 3));

        g = createPath(4);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(1, 2));
        assertTrue(g.containsEdge(2, 3));
        assertFalse(g.containsEdge(3, 0));
        assertFalse(g.containsEdge(1, 4));
        assertFalse(g.containsEdge(1, 123));
    }

    @Test
    void createStar_Test() {
        assertThrows(IllegalArgumentException.class, () -> createStar(1));

        Graph<Integer, DefaultEdge> g = createStar(3);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(0, 2));
        assertFalse(g.containsEdge(1, 2));
        assertFalse(g.containsEdge(0, 3));

        g = createStar(4);
        assertTrue(g.containsEdge(0, 1));
        assertTrue(g.containsEdge(0, 2));
        assertTrue(g.containsEdge(0, 3));
        assertFalse(g.containsEdge(1, 3));
        assertFalse(g.containsEdge(0, 4));
        assertFalse(g.containsEdge(1, 123));
    }
}
