package de.umr.fixcon;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import de.umr.core.FileReader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static de.umr.core.FileReader.*;

class SubIter_Test {

    @Test
    void test_7erFrankGraph() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        g.putEdge(1, 4);
        g.putEdge(2, 3);
        g.putEdge(2, 5);
        g.putEdge(3, 6);
        g.putEdge(4, 5);
        g.putEdge(5, 6);

        SubIter subGraph_iterator = new SubIter(g, 2);
        Set<Set<Integer>> result = new HashSet<>();
        while (subGraph_iterator.hasCurrent()) {
            result.add(new HashSet<>(subGraph_iterator.getCurrent().nodes()));
            subGraph_iterator.generateNext();
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
    void test_zebraGraph() {
        MutableGraph<Integer> g = graph_from_NetworkRepo(edges_from_path(".//graph_files//out.moreno_zebra_zebra"));
        SubIter subGraph_iterator = new SubIter(g, 21);
        int counter = 0;
        while (subGraph_iterator.hasCurrent()) {
            counter++;
            subGraph_iterator.generateNext();
        }
        assertEquals(230, counter);
    }

    @Test
    void test_infPowerGraph() {
        MutableGraph<Integer> g = graph_from_NetworkRepo(edges_from_path(".//graph_files//inf-power.mtx"));
        SubIter subGraph_iterator = new SubIter(g, 6);
        int counter = 0;
        while (subGraph_iterator.hasCurrent()) {
            counter++;
            subGraph_iterator.generateNext();
        }
        assertEquals(1260958, counter);
    }

    @Test
    void test_bigGraph() {
        MutableGraph<Integer> g = graph_from_NetworkRepo(edges_from_path(".//graph_files//out.dolphins"));
        SubIter subGraph_iterator = new SubIter(g, 9);
        int counter = 0;
        while (subGraph_iterator.hasCurrent()) {
            counter++;
            subGraph_iterator.generateNext();
        }
        assertEquals(12495833, counter);
    }

    @Test
    void test_size2() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        g.putEdge(1, 4);
        g.putEdge(2, 3);
        g.putEdge(2, 5);
        g.putEdge(3, 6);
        g.putEdge(4, 5);
        g.putEdge(5, 6);

        SubIter sub_it_2 = new SubIter(g, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.hasCurrent()) {
            result_2.add(new HashSet<>(sub_it_2.getCurrent().nodes()));
            sub_it_2.generateNext();
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
