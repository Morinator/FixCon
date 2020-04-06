package de.umr.core;

import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static de.umr.core.Factory.edgesFromNetworkRepo;
import static de.umr.core.Factory.graphByEdges;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Factory_Test {

    @Test
    void integrationTest() throws IOException {
        MutableGraph<Integer> g = graphByEdges(Objects.requireNonNull(edgesFromNetworkRepo(".//graph_files//sample")));
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
}