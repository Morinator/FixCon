package unitTests.fixcon.iterators;

import de.umr.fixcon.itarators.SubIterator;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubIterator_Test {

    @Test
    void test_7erFrankGraph() {
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(g, 1, 2);
        Graphs.addEdgeWithVertices(g, 1, 4);
        Graphs.addEdgeWithVertices(g, 2, 3);
        Graphs.addEdgeWithVertices(g, 2, 5);
        Graphs.addEdgeWithVertices(g, 3, 6);
        Graphs.addEdgeWithVertices(g, 4, 5);
        Graphs.addEdgeWithVertices(g, 5, 6);

        SubIterator subGraph_iterator = new SubIterator(g, 2);
        Set<Set<Integer>> result = new HashSet<>();
        while (subGraph_iterator.isValid()) {
            result.add(new HashSet<>(subGraph_iterator.current().vertexSet()));
            subGraph_iterator.mutate();
        }

        assertEquals(7, result.size());
        assertTrue(result.contains(Set.of(1, 2)));
        assertTrue(result.contains(Set.of(1, 4)));
        assertTrue(result.contains(Set.of(2, 3)));
        assertTrue(result.contains(Set.of(2, 5)));
        assertTrue(result.contains(Set.of(4, 5)));
        assertTrue(result.contains(Set.of(3, 6)));
        assertTrue(result.contains(Set.of(5, 6)));
    }

    @Test @Disabled //takes too long
    void test_zebraGraph() throws IOException {
        Graph<Integer, DefaultEdge> g = graphFromNetworkRepo(".//graph_files//out.moreno_zebra_zebra");
        SubIterator subGraph_iterator = new SubIterator(g, 21);
        int counter = 0;
        while (subGraph_iterator.isValid()) {
            counter++;
            subGraph_iterator.mutate();
        }
        assertEquals(230, counter);
    }

    @Test
    void test_infPowerGraph() throws IOException {
        Graph<Integer, DefaultEdge> g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
        SubIterator subGraph_iterator = new SubIterator(g, 6);
        int counter = 0;
        while (subGraph_iterator.isValid()) {
            counter++;
            subGraph_iterator.mutate();
        }
        assertEquals(1260958, counter);
    }

    @Test
    void test_size2() {
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(g, 1, 2);
        Graphs.addEdgeWithVertices(g, 1, 4);
        Graphs.addEdgeWithVertices(g, 2, 3);
        Graphs.addEdgeWithVertices(g, 2, 5);
        Graphs.addEdgeWithVertices(g, 3, 6);
        Graphs.addEdgeWithVertices(g, 4, 5);
        Graphs.addEdgeWithVertices(g, 5, 6);

        SubIterator sub_it_2 = new SubIterator(g, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.isValid()) {
            result_2.add(new HashSet<>(sub_it_2.current().vertexSet()));
            sub_it_2.mutate();
        }
        assertEquals(7, result_2.size());
        assertTrue(result_2.contains(Set.of(1, 2)));
        assertTrue(result_2.contains(Set.of(1, 4)));
        assertTrue(result_2.contains(Set.of(2, 3)));
        assertTrue(result_2.contains(Set.of(2, 5)));
        assertTrue(result_2.contains(Set.of(3, 6)));
        assertTrue(result_2.contains(Set.of(4, 5)));
        assertTrue(result_2.contains(Set.of(5, 6)));
    }
}
