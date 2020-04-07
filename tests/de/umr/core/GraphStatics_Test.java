package de.umr.core;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphStatics_Test {

    @Test
    void areOpenTwins() {
    }

    @Test
    void areClosedTwins() {
    }

    @Test
    void isSmallerTwin() {
    }

    @Test
    void diameter() {
    }

    @Test
    void hasTriangle() throws IOException {
        MutableGraph<Integer> g = graphFromNetworkRepo(".//graph_files//sample");
        assertTrue(GraphStatics.hasTriangle(g));

        g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
        assertTrue(GraphStatics.hasTriangle(g));

        g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        assertFalse(GraphStatics.hasTriangle(g));

        g.putEdge(1, 3);
        g.putEdge(3, 4);
        assertFalse(GraphStatics.hasTriangle(g));

        g.putEdge(1, 4);
        assertTrue(GraphStatics.hasTriangle(g));
    }
}