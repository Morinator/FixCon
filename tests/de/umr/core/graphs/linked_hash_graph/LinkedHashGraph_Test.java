package de.umr.core.graphs.linked_hash_graph;

import de.umr.core.graphs.linked_hash_graph.LinkedHashGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LinkedHashGraph_Test {

    private LinkedHashGraph g1 = new LinkedHashGraph();
    private LinkedHashGraph g2 = new LinkedHashGraph();

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
    void addEdge() {
        assertFalse(g1.areNeighbours(2, 3));
        assertFalse(g1.areNeighbours(2, 4));
        assertTrue(g1.addEdge(2, 3));
        assertTrue(g1.addEdge(2, 4));
        assertTrue(g1.areNeighbours(2, 3));
        assertTrue(g1.areNeighbours(2, 4));
        assertTrue(g1.areNeighbours(1, 2));
        assertFalse(g1.areNeighbours(1, 4));
        assertFalse(g1.areNeighbours(5, 6));
        assertTrue(g1.addEdge(1, 4));
        assertTrue(g1.addEdge(5, 6));
        assertTrue(g1.addEdge(1, -100));
        assertFalse(g1.addEdge(1, 4));
        assertFalse(g1.addEdge(1, 4));
        assertFalse(g1.addEdge(5, 6));
        assertTrue(g1.areNeighbours(1, 4));
        assertTrue(g1.areNeighbours(5, 6));
        assertTrue(g1.areNeighbours(1, -100));
    }

    @Test
    void deleteVertex() {
        assertTrue(g1.getVertices().contains(1));
        assertTrue(g1.areNeighbours(1, 2));
        assertTrue(g1.areNeighbours(1, 3));
        assertTrue(g1.removeVertex(1));
        assertFalse(g1.removeVertex(100));
        assertFalse(g1.removeVertex(-100));
        assertFalse(g1.removeVertex(1));
        assertFalse(g1.getVertices().contains(1));
        assertFalse(g1.areNeighbours(1, 2));
        assertFalse(g1.areNeighbours(1, 3));
        assertTrue(g1.areNeighbours(3, 4));
        assertFalse(g1.getVertices().contains(-100));
        g2.removeVertex(2);
        assertEquals(0, g2.degree(2));
        assertEquals(2, g1.vertexCount());
        assertTrue(g1.areNeighbours(3, 4));
    }

    @Test
    void deleteEdge() {
        assertTrue(g1.areNeighbours(1, 2));
        assertTrue(g1.removeEdge(1, 2));
        assertEquals(1, g1.degree(1));
        assertFalse(g1.getVertices().contains(2));
        assertTrue(g1.getVertices().contains(3));
        assertTrue(g1.removeEdge(1, 3));
        assertFalse(g1.getVertices().contains(1));
        assertEquals(1, g1.degree(3));
        assertTrue(g1.removeEdge(3, 4));
        assertEquals(0, g1.vertexCount());
    }

    @Test
    void degree() {
        assertEquals(2, g1.degree(1));
        assertEquals(1, g1.degree(2));
        assertEquals(2, g1.degree(3));
        assertEquals(1, g1.degree(4));


        assertEquals(2, g2.degree(1));
        assertEquals(5, g2.degree(2));
        assertEquals(3, g2.degree(3));
        assertEquals(3, g2.degree(4));
        assertEquals(3, g2.degree(5));
        assertEquals(2, g2.degree(6));

        assertEquals(0, g1.degree(100));
        assertEquals(0, g2.degree(-100));
    }

    @Test
    void isEmpty() {
        assertFalse(g1.isEmpty());
        assertTrue(g1.removeVertex(1));
        assertFalse(g1.removeVertex(2));
        assertFalse(g1.isEmpty());
        assertTrue(g1.removeVertex(3));
        assertTrue(g1.isEmpty());
        assertFalse(g1.removeVertex(4));
        assertTrue(g1.isEmpty());
        assertTrue(g1.addEdge(1, 2));
        assertFalse(g1.isEmpty());
    }

    @Test
    void edgeCount() {
        assertEquals(3, g1.edgeCount());
        assertTrue(g1.removeEdge(1, 2));
        assertEquals(2, g1.edgeCount());
        assertTrue(g1.removeVertex(3));
        assertEquals(0, g1.edgeCount());

        assertEquals(9, g2.edgeCount());
        assertTrue(g2.removeVertex(2));
        assertEquals(4, g2.edgeCount());
        assertTrue(g2.removeEdge(1, 5));
        assertEquals(3, g2.edgeCount());
    }

    @Test
    void getVertices() {
        assertEquals(Set.of(1,2,3,4), g1.getVertices());
        assertTrue(g1.removeEdge(1,2));
        assertEquals(Set.of(1,3,4), g1.getVertices());
        assertTrue(g1.removeVertex(3));
        assertEquals(Set.of(), g1.getVertices());

        assertEquals(Set.of(1,2,3,4,5,6), g2.getVertices());
        g2.removeVertex(2);
        assertEquals(Set.of(1,3,4,5,6), g2.getVertices());
        g2.removeVertex(5);
        assertEquals(Set.of(3,4,6), g2.getVertices());
        g2.removeEdge(3,4);
        assertEquals(Set.of(3, 6), g2.getVertices());
        g2.removeEdge(3,6);
        assertEquals(new LinkedHashSet<>(), g2.getVertices());
    }

    @Test
    void vertexCount() {
        assertEquals(4, g1.vertexCount());
        g1.removeEdge(1,2);
        assertEquals(3, g1.vertexCount());
        g1.removeVertex(3);
        assertEquals(0, g1.vertexCount());

        assertEquals(6, g2.vertexCount());
        g2.removeEdge(1, 2);
        assertEquals(6, g2.vertexCount());
        g2.removeVertex(5);
        assertEquals(4, g2.vertexCount());
        g2.removeVertex(4);
        assertEquals(3, g2.vertexCount());
    }

    @Test
    void getNeighbours() {
        assertEquals(Set.of(2, 3), g1.getNeighbours(1));
        assertEquals(Set.of(1), g1.getNeighbours(2));
        g1.removeVertex(2);
        assertEquals(Set.of(3), g1.getNeighbours(1));
        assertNull(g1.getNeighbours(2));
        assertEquals(Set.of(1,4), g1.getNeighbours(3));
    }

    @Test
    void remove_vertices_if_isolated() {
        g1.removeEdge(3, 4);
        g1.removeEdge(3, 1);
        //vertices 3 and 4 are now isolated
        assertEquals(2, g1.vertexCount());

        g2.removeEdge(1, 2);
        g2.removeEdge(1, 5);
        assertEquals(5, g2.vertexCount());
        g2.removeEdge(4, 5);
        g2.removeEdge(2, 5);
        assertEquals(4, g2.vertexCount());
    }

    @Test
    void toString_test() {
        System.out.println(g1.toString());
    }
}