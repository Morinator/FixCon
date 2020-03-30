package de.umr.fixcon;

import de.umr.core.graphs.UndirectedGraph;
import de.umr.core.graphs.linked_hash_graph.LinkedHashGraph;
import de.umr.core.io.GraphFile_Readers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubgraphIterator_Test {

    @Test
    void test_7erFrank() {
        UndirectedGraph g = new LinkedHashGraph();
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);

        SubgraphIterator subGraph_iterator = new SubgraphIterator(g, 2);
        Set<Set<Integer>> result = new HashSet<>();
        while (subGraph_iterator.hasNext()) {
            result.add(subGraph_iterator.next());
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

    @Test
    void test_zebra() throws IOException {
        UndirectedGraph g = new LinkedHashGraph(GraphFile_Readers.readFile_NetworkRepository_Format(".//data//out.moreno_zebra_zebra"));
        SubgraphIterator subGraph_iterator = new SubgraphIterator(g, 21);
        int counter = 0;
        while (subGraph_iterator.hasNext()) {
            subGraph_iterator.next();
            counter++;
        }
        assertEquals(230, counter);
    }

    @Test
    void test_infPower() throws IOException {
        UndirectedGraph g = new LinkedHashGraph(GraphFile_Readers.readFile_NetworkRepository_Format(".//data//inf-power.mtx"));
        SubgraphIterator subGraph_iterator = new SubgraphIterator(g, 6);
        int counter = 0;
        while (subGraph_iterator.hasNext()) {
            subGraph_iterator.next();
            counter++;
        }
        assertEquals(1260958, counter);
    }

    @Test
        //tests that the iterator returns every subgraph of size k exactly once.
    void test_big() throws IOException {
        UndirectedGraph g = new LinkedHashGraph(GraphFile_Readers.readFile_NetworkRepository_Format(".//data//out.dolphins"));
        SubgraphIterator subGraph_iterator = new SubgraphIterator(g, 9);
        int counter = 0;
        while (subGraph_iterator.hasNext()) {
            subGraph_iterator.next();
            counter++;
        }
        assertEquals(12495833, counter);
    }

    @Test
    void test_size2() {
        UndirectedGraph g = new LinkedHashGraph();
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);

        SubgraphIterator sub_it_2 = new SubgraphIterator(g, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.hasNext())
            result_2.add(sub_it_2.next());
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