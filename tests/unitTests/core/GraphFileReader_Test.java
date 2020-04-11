package unitTests.core;

import com.google.common.graph.MutableGraph;
import de.umr.core.GraphFileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static de.umr.core.GraphFileReader.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphFileReader_Test {

    @Test
    void integrationTest() throws IOException {
        MutableGraph<Integer> g = graphFromNetworkRepo(".//graph_files//sample");
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

    @Test
    void exceptionOnEmptyGraph() {
        assertThrows(IllegalArgumentException.class, () -> GraphFileReader.graphByEdges(new ArrayList<>()));
    }
}