package de.umr.fixcon;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static de.umr.core.Factory.edgesFromNetworkRepo;
import static de.umr.core.Factory.graphByEdges;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubIter_fromStart_Test {

    @Test
    void mainTest() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        g.putEdge(1, 4);
        g.putEdge(2, 3);
        g.putEdge(2, 5);
        g.putEdge(3, 6);
        g.putEdge(4, 5);
        g.putEdge(5, 6);

        SubIter_fromStart sub_it_4 = new SubIter_fromStart(g, 1, 4);
        Set<Set<Integer>> result_4 = new HashSet<>();
        while (sub_it_4.isValid()) {
            result_4.add(new HashSet<>(sub_it_4.current().nodes()));
            sub_it_4.mutate();
        }
        assertEquals(6, result_4.size());
        assertTrue(result_4.contains(Set.of(1, 2, 4, 3)));
        assertTrue(result_4.contains(Set.of(1, 2, 4, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 6)));
        assertTrue(result_4.contains(Set.of(1, 2, 5, 6)));
        assertTrue(result_4.contains(Set.of(1, 4, 5, 6)));

        SubIter_fromStart sub_it_2 = new SubIter_fromStart(g, 1, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.isValid()) {
            result_2.add(new HashSet<>(sub_it_2.current().nodes()));
            sub_it_2.mutate();
        }
        assertEquals(2, result_2.size());
        assertTrue(result_2.contains(Set.of(1, 2)));
        assertTrue(result_2.contains(Set.of(1, 4)));
    }

    @Test
    void illegal_sizes() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SubIter_fromStart(g, 1, 1));
        assertEquals("Only supports search for subgraphs of size greater 2", exception.getMessage());
        assertThrows(IllegalArgumentException.class,
                () -> new SubIter_fromStart(g, 1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new SubIter_fromStart(g, 1, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new SubIter_fromStart(g, 1, -100));
    }

    @Test
    void targetSize_greaterThan_graphSize() throws IOException {
        MutableGraph<Integer> g = graphByEdges(edgesFromNetworkRepo(".//graph_files//sample"));
        SubIter_fromStart sub_it_25 = new SubIter_fromStart(g, 1, 25);
        int subGraph_counter = 0;
        while (sub_it_25.isValid()) {
            subGraph_counter++;
            sub_it_25.mutate();
        }
        assertEquals(0, subGraph_counter);
    }
}