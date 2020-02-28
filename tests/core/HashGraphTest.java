package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashGraphTest {

    private HashGraph g1 = new HashGraph();
    private HashGraph g2 = new HashGraph();

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
        assertFalse(g1.adjacent(2, 3));
        assertFalse(g1.adjacent(2, 4));
        g1.addEdge(2, 3);
        g1.addEdge(2, 4);
        assertTrue(g1.adjacent(2, 3));
        assertTrue(g1.adjacent(2, 4));
        assertTrue(g1.adjacent(1, 2));

        assertFalse(g1.adjacent(1, 4));
        assertFalse(g1.adjacent(5, 6));
        g1.addEdge(1, 4);
        g1.addEdge(5, 6);
        g1.addEdge(1, -100);
        assertTrue(g1.adjacent(1, 4));
        assertTrue(g1.adjacent(5, 6));
        assertTrue(g1.adjacent(1, -100));
    }

    @Test
    void deleteNode() {
        assertTrue(g1.hasNode(1));
        assertTrue(g1.adjacent(1, 2));
        assertTrue(g1.adjacent(1, 3));
        g1.deleteNode(1);
        g1.deleteNode(100);
        g1.deleteNode(-100);
        g1.deleteNode(1);
        assertFalse(g1.hasNode(1));
        assertFalse(g1.adjacent(1, 2));
        assertFalse(g1.adjacent(1, 3));
        assertFalse(g1.hasNode(-100));

        g2.deleteNode(2);
        assertEquals(0, g2.degree(2));
    }

    @Test
    void deleteEdge() {
        assertTrue(g1.adjacent(1, 2));
        g1.deleteEdge(1, 2);
        assertEquals(1, g1.degree(1));
        assertFalse(g1.hasNode(2));
        assertTrue(g1.hasNode(3));
        g1.deleteEdge(1, 3);
        assertFalse(g1.hasNode(1));
        assertEquals(1, g1.degree(3));
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
        g1.deleteNode(1);
        g1.deleteNode(2);
        assertFalse(g1.isEmpty());
        g1.deleteNode(3);
        assertFalse(g1.isEmpty());
        g1.deleteNode(4);
        assertTrue(g1.isEmpty());
        g1.addEdge(1, 2);
        assertFalse(g1.isEmpty());
    }

    @Test
    void edgeCount() {
        assertEquals(3, g1.edgeCount());
        g1.deleteEdge(1, 2);
        assertEquals(2, g1.edgeCount());
        g1.deleteNode(3);
        assertEquals(0, g1.edgeCount());

        assertEquals(9, g2.edgeCount());
        g2.deleteNode(2);
        assertEquals(4, g2.edgeCount());
        g2.deleteEdge(1, 5);
        assertEquals(3, g2.edgeCount());
    }

    @Test
    void getNodes() {
        assertEquals(Set.of(1,2,3,4), g1.getNodes());
        g1.deleteEdge(1,2);
        assertEquals(Set.of(1,3,4), g1.getNodes());
        g1.deleteNode(3);
        assertEquals(Set.of(), g1.getNodes());

        assertEquals(Set.of(1,2,3,4,5,6), g2.getNodes());
        g2.deleteNode(2);
        assertEquals(Set.of(1,3,4,5,6), g2.getNodes());
        g2.deleteNode(5);
        assertEquals(Set.of(3,4,6), g2.getNodes());
        g2.deleteEdge(3,4);
        assertEquals(Set.of(3,4,6), g2.getNodes());
    }

    @Test
    void nodeCount() {
        assertEquals(4, g1.nodeCount());
        g1.deleteEdge(1,2);
        assertEquals(3, g1.nodeCount());
        g1.deleteNode(3);
        assertEquals(0, g1.nodeCount());

        assertEquals(6, g2.nodeCount());
        g2.deleteEdge(1, 2);
        assertEquals(6, g2.nodeCount());
        g2.deleteNode(5);
        assertEquals(4, g2.nodeCount());
        g2.deleteNode(4);
        assertEquals(3, g2.nodeCount());
    }

    @Test
    void getNeighbours() {
        assertEquals(Set.of(2, 3), g1.getNeighbours(1));
        assertEquals(Set.of(1), g1.getNeighbours(2));
        g1.deleteNode(2);
        assertEquals(Set.of(3), g1.getNeighbours(1));
        assertEquals(new HashSet<>(), g1.getNeighbours(2));
        assertEquals(Set.of(1,4), g1.getNeighbours(3));
    }
}