package de.umr.core.graphs.linked_hash_graph;

import de.umr.core.graphs.linked_hash_graph.Factory;
import de.umr.core.graphs.UndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Factory_Test {

    @Test
    void createClique() {
        UndirectedGraph g = Factory.createClique(0);
        assertEquals(0, g.getVertices().size());

        g = Factory.createClique(1);
        assertEquals(0, g.getVertices().size());

        g = Factory.createClique(2);
        assertEquals(2, g.getVertices().size());
        assertTrue(g.areNeighbours(0,1));
        assertFalse(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(-10,2));

        g = Factory.createClique(3);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(0,2));
        assertTrue(g.areNeighbours(1,2));
        assertFalse(g.areNeighbours(0,3));
        assertFalse(g.areNeighbours(0,4));

        g = Factory.createClique(6);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(0,3));
        assertTrue(g.areNeighbours(1,5));
        assertTrue(g.areNeighbours(1,2));
        assertTrue(g.areNeighbours(3,1));
        assertTrue(g.areNeighbours(3,0));
        assertTrue(g.areNeighbours(4,3));
        assertFalse(g.areNeighbours(0,6));
        assertFalse(g.areNeighbours(-1,4));
        assertFalse(g.areNeighbours(2,2));

    }

    @Test
    void createCircle() {
        UndirectedGraph g = Factory.createCircle(0);
        assertEquals(0, g.getVertices().size());

        g = Factory.createCircle(1);
        assertEquals(0, g.getVertices().size());

        g = Factory.createCircle(2);
        assertEquals(2, g.getVertices().size());
        assertTrue(g.areNeighbours(0,1));
        assertFalse(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(-10,2));

        g = Factory.createCircle(3);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(0,2));
        assertTrue(g.areNeighbours(1,2));
        assertFalse(g.areNeighbours(0,3));
        assertFalse(g.areNeighbours(0,4));

        g = Factory.createCircle(6);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(1,2));
        assertTrue(g.areNeighbours(2,3));
        assertTrue(g.areNeighbours(3,4));
        assertTrue(g.areNeighbours(4,5));
        assertFalse(g.areNeighbours(0,6));
        assertFalse(g.areNeighbours(2,4));
        assertFalse(g.areNeighbours(-2,4));
        assertFalse(g.areNeighbours(1,4));
        assertFalse(g.areNeighbours(2,5));
        assertFalse(g.areNeighbours(2,2));

    }

    @Test
    void createPath() {
        UndirectedGraph g = Factory.createPath(0);
        assertEquals(0, g.getVertices().size());

        g = Factory.createPath(1);
        assertEquals(0, g.getVertices().size());

        g = Factory.createPath(2);
        assertEquals(2, g.getVertices().size());
        assertTrue(g.areNeighbours(0,1));
        assertFalse(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(-10,2));

        g = Factory.createPath(3);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(1,2));
        assertFalse(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(0,4));
        assertFalse(g.areNeighbours(-10,4));
        assertFalse(g.areNeighbours(2,2));

        g = Factory.createPath(6);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(1,2));
        assertTrue(g.areNeighbours(2,3));
        assertTrue(g.areNeighbours(3,4));
        assertTrue(g.areNeighbours(4,5));
        assertFalse(g.areNeighbours(5,0));
        assertFalse(g.areNeighbours(2,4));
        assertFalse(g.areNeighbours(-2,4));
        assertFalse(g.areNeighbours(1,4));
        assertFalse(g.areNeighbours(2,5));
        assertFalse(g.areNeighbours(2,2));
    }

    @Test
    void createStar() {
        UndirectedGraph g = Factory.createStar(0);
        assertEquals(0, g.getVertices().size());

        g = Factory.createStar(1);
        assertEquals(0, g.getVertices().size());

        g = Factory.createStar(2);
        assertEquals(2, g.getVertices().size());
        assertTrue(g.areNeighbours(0,1));
        assertFalse(g.areNeighbours(0,0));
        assertFalse(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(-10,2));

        g = Factory.createStar(3);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(0,2));
        assertFalse(g.areNeighbours(0,3));
        assertFalse(g.areNeighbours(4,4));
        assertFalse(g.areNeighbours(-10,1));
        assertFalse(g.areNeighbours(2,2));

        g = Factory.createStar(6);
        assertTrue(g.areNeighbours(0,1));
        assertTrue(g.areNeighbours(0,2));
        assertTrue(g.areNeighbours(0,3));
        assertTrue(g.areNeighbours(0,4));
        assertTrue(g.areNeighbours(0,5));
        assertFalse(g.areNeighbours(1,5));
        assertFalse(g.areNeighbours(2,4));
        assertFalse(g.areNeighbours(-2,4));
        assertFalse(g.areNeighbours(1,4));
        assertFalse(g.areNeighbours(2,5));
        assertFalse(g.areNeighbours(2,2));
    }
}