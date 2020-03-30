package de.umr.core.graphs;

import de.umr.core.graphs.linked_hash_graph.LinkedHashGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the default-methods of the interface UndirectedGraph. For this it uses the implementation from
 * LinkedHashGraph, but only tests methods that are already implemented in the interface.
 */
class UndirectedGraph_Test {

    private LinkedHashGraph g1 = new LinkedHashGraph();
    private LinkedHashGraph g2 = new LinkedHashGraph();
    private LinkedHashGraph g3 = new LinkedHashGraph(); //stays empty intentionally

    @BeforeEach
    void BuildUp() {
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(3, 4);

        g2.addEdge(1, 2);
        g2.addEdge(1, 5);
        g2.addEdge(2, 3);
        g2.addEdge(2, 4);
        g2.addEdge(2, 5);
        g2.addEdge(2, 6);
        g2.addEdge(3, 4);
        g2.addEdge(3, 6);
        g2.addEdge(4, 5);
    }

    @Test
    void edgeCount() {
        assertEquals(3, g1.edgeCount());
        g1.removeEdge(1, 2);
        assertEquals(2, g1.edgeCount());
        g1.removeEdge(3, 4);
        assertEquals(1, g1.edgeCount());
        g1.removeVertex(1);
        assertEquals(0, g1.edgeCount());
        g1.addEdge(10, 11);
        assertEquals(1, g1.edgeCount());

        assertEquals(9, g2.edgeCount());
        g2.addEdge(1, 2);
        assertEquals(9, g2.edgeCount());
        g2.removeVertex(1);
        assertEquals(7, g2.edgeCount());
        g2.removeEdge(3, 6);
        assertEquals(6, g2.edgeCount());
        g2.removeVertex(5);
        assertEquals(4, g2.edgeCount());

        assertEquals(0, g3.edgeCount());
    }

    @Test
    void vertexCount() {
        assertEquals(4, g1.vertexCount());
        g1.removeEdge(1, 2);
        assertEquals(3, g1.vertexCount());
        g1.removeEdge(3, 4);
        assertEquals(2, g1.vertexCount());
        g1.removeVertex(1);
        assertEquals(0, g1.vertexCount());

        assertEquals(6, g2.vertexCount());
        g2.removeEdge(1, 2);
        assertEquals(6, g2.vertexCount());
        g2.removeEdge(1, 5);
        assertEquals(5, g2.vertexCount());
        g2.removeVertex(6);
        assertEquals(4, g2.vertexCount());
        g2.removeVertex(4);
        assertEquals(3, g2.vertexCount());
        g2.removeEdge(2, 5);
        assertEquals(2, g2.vertexCount());
        g2.removeEdge(2, 3);
        assertEquals(0, g2.vertexCount());

        assertEquals(0, g3.vertexCount());
    }

    @Test
    void isEmpty() {
        assertFalse(g1.isEmpty());
        g1.removeEdge(1, 2);
        assertFalse(g1.isEmpty());
        g1.removeEdge(3, 4);
        assertFalse(g1.isEmpty());
        g1.removeVertex(1);
        assertTrue(g1.isEmpty());
        g1.addEdge(10, 11);
        assertFalse(g1.isEmpty());
        g1.removeVertex(10);
        assertTrue(g1.isEmpty());

        assertFalse(g2.isEmpty());
        g2.removeEdge(1, 2);
        assertFalse(g2.isEmpty());
        g2.removeEdge(1, 5);
        assertFalse(g2.isEmpty());
        g2.removeVertex(6);
        assertFalse(g2.isEmpty());
        g2.removeVertex(4);
        assertFalse(g2.isEmpty());
        g2.removeEdge(2, 5);
        assertFalse(g2.isEmpty());
        g2.removeEdge(2, 3);
        assertTrue(g2.isEmpty());
        g2.removeVertex(100);
        assertTrue(g2.isEmpty());
        g2.addEdge(-4, 0);
        assertFalse(g2.isEmpty());
        g2.addEdge(10, 0);
        assertFalse(g2.isEmpty());
        g2.removeVertex(0);
        assertTrue(g2.isEmpty());

        assertTrue(g3.isEmpty());
    }

    @Test
    void degree() {
        assertEquals(0, g1.degree(1000));
        assertEquals(0, g1.degree(-1000));
        assertEquals(2, g1.degree(1));
        assertEquals(1, g1.degree(2));
        assertEquals(2, g1.degree(3));
        assertEquals(1, g1.degree(4));
        assertEquals(0, g1.degree(100));
        g1.removeEdge(1, 2);
        assertEquals(1, g1.degree(1));
        assertEquals(0, g1.degree(2));
        assertEquals(2, g1.degree(3));
        assertEquals(1, g1.degree(4));
        assertEquals(0, g1.degree(100));
        g1.removeEdge(3, 4);
        assertEquals(1, g1.degree(1));
        assertEquals(0, g1.degree(2));
        assertEquals(1, g1.degree(3));
        assertEquals(0, g1.degree(4));
        assertEquals(0, g1.degree(100));
        g1.removeVertex(1);
        assertEquals(0, g1.degree(1));
        assertEquals(0, g1.degree(2));
        assertEquals(0, g1.degree(3));
        assertEquals(0, g1.degree(4));
        assertEquals(0, g1.degree(100));
        g1.addEdge(10, 11);
        assertEquals(1, g1.degree(10));
        assertEquals(1, g1.degree(11));
        assertEquals(0, g1.degree(12));
        g1.removeVertex(10);
        assertEquals(0, g1.degree(10));
        assertEquals(0, g1.degree(11));
        assertEquals(0, g1.degree(12));

        assertEquals(2, g2.degree(1));
        assertEquals(5, g2.degree(2));
        assertEquals(3, g2.degree(3));
        assertEquals(3, g2.degree(4));
        assertEquals(3, g2.degree(5));
        assertEquals(2, g2.degree(6));
        assertEquals(0, g2.degree(100));
        g2.removeEdge(1, 2);
        assertEquals(1, g2.degree(1));
        assertEquals(4, g2.degree(2));
        assertEquals(3, g2.degree(3));
        g2.removeEdge(1, 5);
        assertEquals(0, g2.degree(1));
        assertEquals(2, g2.degree(5));
        assertEquals(3, g2.degree(3));
        g2.removeVertex(6);
        assertEquals(0, g2.degree(1));
        assertEquals(3, g2.degree(2));
        assertEquals(2, g2.degree(3));
        assertEquals(3, g2.degree(4));
        assertEquals(2, g2.degree(5));
        assertEquals(0, g2.degree(6));
        assertEquals(0, g2.degree(0));
        g2.removeVertex(4);
        assertEquals(0, g2.degree(1));
        assertEquals(2, g2.degree(2));
        assertEquals(1, g2.degree(3));
        assertEquals(1, g2.degree(5));
        assertEquals(0, g2.degree(6));
        assertEquals(0, g2.degree(7));
        g2.removeEdge(2, 5);
        assertEquals(0, g2.degree(5));
        assertEquals(1, g2.degree(2));
        assertEquals(1, g2.degree(3));
        g2.removeEdge(2, 3);
        assertEquals(0, g2.degree(5));
        assertEquals(0, g2.degree(2));
        assertEquals(0, g2.degree(3));
        g2.removeVertex(100);
        assertEquals(0, g2.degree(1));
        assertEquals(0, g2.degree(2));
        assertEquals(0, g2.degree(3));
        assertEquals(0, g2.degree(4));
        assertEquals(0, g2.degree(5));
        assertEquals(0, g2.degree(6));
        assertEquals(0, g2.degree(7));
        g2.addEdge(1, 2);
        assertEquals(1, g2.degree(1));
        assertEquals(1, g2.degree(2));
        assertEquals(0, g2.degree(3));
        g2.addEdge(1, 2);
        assertEquals(1, g2.degree(1));
        assertEquals(1, g2.degree(2));
        assertEquals(0, g2.degree(3));

        assertEquals(0, g3.degree(0));
        assertEquals(0, g3.degree(1));
        assertEquals(0, g3.degree(9999));
        assertEquals(0, g3.degree(-123));
    }

    @Test
    void areNeighbours() {
        assertFalse(g1.areNeighbours(1, 1));
        assertTrue(g1.areNeighbours(1, 2));
        assertTrue(g1.areNeighbours(1, 3));
        assertFalse(g1.areNeighbours(1, 4));
        assertFalse(g1.areNeighbours(2, 3));
        assertFalse(g1.areNeighbours(2, 4));
        assertFalse(g1.areNeighbours(2, 6));
        assertTrue(g1.areNeighbours(3, 4));
        assertFalse(g1.areNeighbours(3, 6));
        assertFalse(g1.areNeighbours(4, 5));
        assertTrue(g1.areNeighbours(4, 3));
        g1.removeEdge(1, 2);
        assertFalse(g1.areNeighbours(1, 2));
        assertTrue(g1.areNeighbours(1, 3));
        assertFalse(g1.areNeighbours(2, 4));
        g1.removeEdge(3, 4);
        assertTrue(g1.areNeighbours(1, 3));
        assertFalse(g1.areNeighbours(1, 2));
        assertFalse(g1.areNeighbours(3, 4));
        g1.removeVertex(1);
        assertFalse(g1.areNeighbours(1, 3));
        assertFalse(g1.areNeighbours(10, 11));
        assertFalse(g1.areNeighbours(10, -11));
        assertFalse(g1.areNeighbours(1, 2));
        g1.addEdge(10, 11);
        assertTrue(g1.areNeighbours(10 ,11));
        assertTrue(g1.areNeighbours(11, 10));
        assertFalse(g1.areNeighbours(1, 10));
        assertFalse(g1.areNeighbours(2, 10));
        assertFalse(g1.areNeighbours(10, 2));
        g1.removeVertex(10);
        assertFalse(g1.areNeighbours(10, 11));
        assertFalse(g1.areNeighbours(10, 10));
        assertFalse(g1.areNeighbours(1, 2));
        assertFalse(g1.areNeighbours(1, 3));
        assertFalse(g1.areNeighbours(-1111, 3333));

        assertTrue(g2.areNeighbours(1, 2));
        assertTrue(g2.areNeighbours(2, 1));
        assertTrue(g2.areNeighbours(1, 5));
        assertTrue(g2.areNeighbours(2, 3));
        assertTrue(g2.areNeighbours(2, 4));
        assertTrue(g2.areNeighbours(2, 5));
        assertTrue(g2.areNeighbours(2, 6));
        assertFalse(g2.areNeighbours(2, 2));
        assertTrue(g2.areNeighbours(3, 6));
        assertFalse(g2.areNeighbours(3, 1));
        assertTrue(g2.areNeighbours(4, 5));
        assertFalse(g2.areNeighbours(4, 1));
        assertFalse(g2.areNeighbours(4, 111));
        g2.removeVertex(1);
        assertFalse(g2.areNeighbours(1, 2));
        assertFalse(g2.areNeighbours(1, 5));
        assertFalse(g2.areNeighbours(1, 6));
        assertTrue(g2.areNeighbours(2, 5));
        assertTrue(g2.areNeighbours(2, 6));
        g2.removeEdge(3, 6);
        assertFalse(g2.areNeighbours(3, 6));
        assertTrue(g2.areNeighbours(2, 6));
        g2.removeVertex(6);
        assertFalse(g2.areNeighbours(3, 6));
        assertFalse(g2.areNeighbours(2, 6));
        g2.removeVertex(4);
        assertFalse(g2.areNeighbours(2, 4));
        assertFalse(g2.areNeighbours(5, 4));
        assertFalse(g2.areNeighbours(4, 4));
        assertTrue(g2.areNeighbours(2, 3));

        assertFalse(g3.areNeighbours(1, 1));
        assertFalse(g3.areNeighbours(1, 2));
        assertFalse(g3.areNeighbours(1, 999));
        assertFalse(g3.areNeighbours(0, -68463));
    }
}