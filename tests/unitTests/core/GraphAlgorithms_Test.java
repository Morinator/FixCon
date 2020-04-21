package unitTests.core;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.GraphAlgorithmsKt.hasTriangle;
import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphAlgorithms_Test {

    @Test
    void hasTriangle_Test() throws IOException {
        Graph<Integer, DefaultEdge> g = graphFromNetworkRepo(".//graph_files//sample");
        Assertions.assertTrue(hasTriangle(g));

        g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
        assertTrue(hasTriangle(g));

        g = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(g, 1, 2);
        assertFalse(hasTriangle(g));

        Graphs.addEdgeWithVertices(g, 1, 3);
        Graphs.addEdgeWithVertices(g, 3, 4);
        assertFalse(hasTriangle(g));

        Graphs.addEdgeWithVertices(g, 1, 4);
        assertTrue(hasTriangle(g));
    }
}