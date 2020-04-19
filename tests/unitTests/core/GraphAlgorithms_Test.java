package unitTests.core;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.GraphAlgorithmsKt.hasTriangle;
import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphAlgorithms_Test {

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
    void hasTriangle_Test() throws IOException {
        MutableGraph<Integer> g = graphFromNetworkRepo(".//graph_files//sample");
        Assertions.assertTrue(hasTriangle(g));

        g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
        assertTrue(hasTriangle(g));

        g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        assertFalse(hasTriangle(g));

        g.putEdge(1, 3);
        g.putEdge(3, 4);
        assertFalse(hasTriangle(g));

        g.putEdge(1, 4);
        assertTrue(hasTriangle(g));
    }
}