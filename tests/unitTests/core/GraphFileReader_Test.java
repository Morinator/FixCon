package unitTests.core;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static de.umr.core.GraphFileReaderKt.graphByEdges;
import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphFileReader_Test {

    @Test
    void integrationTest() throws IOException {
        Graph<Integer, DefaultEdge> g = graphFromNetworkRepo(".//graph_files//sample");
        assertTrue(g.containsEdge(1, 2));
        assertFalse(g.containsEdge(1, 3));
        assertTrue(g.containsEdge(2, 3));
        assertFalse(g.containsEdge(2, 4));
        assertTrue(g.containsEdge(8, 9));
        assertFalse(g.containsEdge(8, 10));
        assertTrue(g.containsEdge(8, 12));
        assertTrue(g.containsEdge(15, 16));
        assertTrue(g.containsEdge(15, 17));
        assertFalse(g.containsEdge(15, 18));
    }

    @Test
    void exceptionOnEmptyGraph() {
        assertThrows(IllegalArgumentException.class, () -> graphByEdges(new ArrayList<>()));
    }
}