package unitTests.fixcon.iterators;

import de.umr.fixcon.itarators.SubIteratorFromStart;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubIterator_fromStart_Test {

    @Test
    void mainTest() {
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(g, 1, 2);
        Graphs.addEdgeWithVertices(g, 1, 4);
        Graphs.addEdgeWithVertices(g, 2, 3);
        Graphs.addEdgeWithVertices(g, 2, 5);
        Graphs.addEdgeWithVertices(g, 3, 6);
        Graphs.addEdgeWithVertices(g, 4, 5);
        Graphs.addEdgeWithVertices(g, 5, 6);

        SubIteratorFromStart sub_it_4 = new SubIteratorFromStart(g, 1, 4);
        Set<Set<Integer>> result_4 = new HashSet<>();
        while (sub_it_4.isValid()) {
            result_4.add(new HashSet<>(sub_it_4.current().vertexSet()));
            sub_it_4.mutate();
        }
        assertEquals(6, result_4.size());
        assertTrue(result_4.contains(Set.of(1, 2, 4, 3)));
        assertTrue(result_4.contains(Set.of(1, 2, 4, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 6)));
        assertTrue(result_4.contains(Set.of(1, 2, 5, 6)));
        assertTrue(result_4.contains(Set.of(1, 4, 5, 6)));

        SubIteratorFromStart sub_it_2 = new SubIteratorFromStart(g, 1, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.isValid()) {
            result_2.add(new HashSet<>(sub_it_2.current().vertexSet()));
            sub_it_2.mutate();
        }
        assertEquals(2, result_2.size());
        assertTrue(result_2.contains(Set.of(1, 2)));
        assertTrue(result_2.contains(Set.of(1, 4)));
    }

    @Test
    void illegal_sizes() {
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(g, 1, 2);
        assertThrows(IllegalArgumentException.class, () -> new SubIteratorFromStart(g, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new SubIteratorFromStart(g, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new SubIteratorFromStart(g, 1, -1));
        assertThrows(IllegalArgumentException.class, () -> new SubIteratorFromStart(g, 1, -100));
    }

    @Test
    void targetSize_greaterThan_graphSize() throws IOException {
        Graph<Integer, DefaultEdge> g = graphFromNetworkRepo(".//graph_files//sample");
        SubIteratorFromStart sub_it_25 = new SubIteratorFromStart(g, 1, 25);
        int subGraph_counter = 0;
        while (sub_it_25.isValid()) {
            subGraph_counter++;
            sub_it_25.mutate();
        }
        assertEquals(0, subGraph_counter);
    }
}