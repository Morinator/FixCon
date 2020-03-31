package de.umr.core.graphs;

import com.google.common.graph.MutableGraph;
import de.umr.core.graphs.Factory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Factory_Test {

    @Test
    void createClique() {
        MutableGraph<Integer> g = Factory.createClique(0);
        assertEquals(0, g.nodes().size());

        g = Factory.createClique(1);
        assertEquals(0, g.nodes().size());

        g = Factory.createClique(2);
        assertEquals(2, g.nodes().size());
        assertTrue(g.hasEdgeConnecting(0,1));
        assertFalse(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(-10,2));

        g = Factory.createClique(3);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(0,2));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertFalse(g.hasEdgeConnecting(0,3));
        assertFalse(g.hasEdgeConnecting(0,4));

        g = Factory.createClique(6);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(0,3));
        assertTrue(g.hasEdgeConnecting(1,5));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertTrue(g.hasEdgeConnecting(3,1));
        assertTrue(g.hasEdgeConnecting(3,0));
        assertTrue(g.hasEdgeConnecting(4,3));
        assertFalse(g.hasEdgeConnecting(0,6));
        assertFalse(g.hasEdgeConnecting(-1,4));
        assertFalse(g.hasEdgeConnecting(2,2));

    }

    @Test
    void createCircle() {
        MutableGraph<Integer> g = Factory.createCircle(0);
        assertEquals(0, g.nodes().size());

        g = Factory.createCircle(1);
        assertEquals(0, g.nodes().size());

        g = Factory.createCircle(2);
        assertEquals(2, g.nodes().size());
        assertTrue(g.hasEdgeConnecting(0,1));
        assertFalse(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(-10,2));

        g = Factory.createCircle(3);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(0,2));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertFalse(g.hasEdgeConnecting(0,3));
        assertFalse(g.hasEdgeConnecting(0,4));

        g = Factory.createCircle(6);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertTrue(g.hasEdgeConnecting(2,3));
        assertTrue(g.hasEdgeConnecting(3,4));
        assertTrue(g.hasEdgeConnecting(4,5));
        assertFalse(g.hasEdgeConnecting(0,6));
        assertFalse(g.hasEdgeConnecting(2,4));
        assertFalse(g.hasEdgeConnecting(-2,4));
        assertFalse(g.hasEdgeConnecting(1,4));
        assertFalse(g.hasEdgeConnecting(2,5));
        assertFalse(g.hasEdgeConnecting(2,2));

    }

    @Test
    void createPath() {
        MutableGraph<Integer> g = Factory.createPath(0);
        assertEquals(0, g.nodes().size());

        g = Factory.createPath(1);
        assertEquals(0, g.nodes().size());

        g = Factory.createPath(2);
        assertEquals(2, g.nodes().size());
        assertTrue(g.hasEdgeConnecting(0,1));
        assertFalse(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(-10,2));

        g = Factory.createPath(3);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertFalse(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(0,4));
        assertFalse(g.hasEdgeConnecting(-10,4));
        assertFalse(g.hasEdgeConnecting(2,2));

        g = Factory.createPath(6);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(1,2));
        assertTrue(g.hasEdgeConnecting(2,3));
        assertTrue(g.hasEdgeConnecting(3,4));
        assertTrue(g.hasEdgeConnecting(4,5));
        assertFalse(g.hasEdgeConnecting(5,0));
        assertFalse(g.hasEdgeConnecting(2,4));
        assertFalse(g.hasEdgeConnecting(-2,4));
        assertFalse(g.hasEdgeConnecting(1,4));
        assertFalse(g.hasEdgeConnecting(2,5));
        assertFalse(g.hasEdgeConnecting(2,2));
    }

    @Test
    void createStar() {
        MutableGraph<Integer> g = Factory.createStar(0);
        assertEquals(0, g.nodes().size());

        g = Factory.createStar(1);
        assertEquals(0, g.nodes().size());

        g = Factory.createStar(2);
        assertEquals(2, g.nodes().size());
        assertTrue(g.hasEdgeConnecting(0,1));
        assertFalse(g.hasEdgeConnecting(0,0));
        assertFalse(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(-10,2));

        g = Factory.createStar(3);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(0,2));
        assertFalse(g.hasEdgeConnecting(0,3));
        assertFalse(g.hasEdgeConnecting(4,4));
        assertFalse(g.hasEdgeConnecting(-10,1));
        assertFalse(g.hasEdgeConnecting(2,2));

        g = Factory.createStar(6);
        assertTrue(g.hasEdgeConnecting(0,1));
        assertTrue(g.hasEdgeConnecting(0,2));
        assertTrue(g.hasEdgeConnecting(0,3));
        assertTrue(g.hasEdgeConnecting(0,4));
        assertTrue(g.hasEdgeConnecting(0,5));
        assertFalse(g.hasEdgeConnecting(1,5));
        assertFalse(g.hasEdgeConnecting(2,4));
        assertFalse(g.hasEdgeConnecting(-2,4));
        assertFalse(g.hasEdgeConnecting(1,4));
        assertFalse(g.hasEdgeConnecting(2,5));
        assertFalse(g.hasEdgeConnecting(2,2));
    }
}