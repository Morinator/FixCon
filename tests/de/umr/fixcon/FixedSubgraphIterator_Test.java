package de.umr.fixcon;

import de.umr.core.graphs.UndirectedGraph;
import de.umr.core.graphs.linked_hash_graph.LinkedHashGraph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedSubgraphIterator_Test {

    @Test
    void mainTest() {
        UndirectedGraph g = new LinkedHashGraph();
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);

        Fixed_SubgraphIterator sub_it_4 = new Fixed_SubgraphIterator(g, 1, 4);
        Set<Set<Integer>> result_4 = new HashSet<>();
        while (sub_it_4.hasNext())
            result_4.add(sub_it_4.next());
        assertEquals(6, result_4.size());
        assertTrue(result_4.contains(Set.of(1, 2, 4, 3)));
        assertTrue(result_4.contains(Set.of(1, 2, 4, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 5)));
        assertTrue(result_4.contains(Set.of(1, 2, 3, 6)));
        assertTrue(result_4.contains(Set.of(1, 2, 5, 6)));
        assertTrue(result_4.contains(Set.of(1, 4, 5, 6)));

        Fixed_SubgraphIterator sub_it_2 = new Fixed_SubgraphIterator(g, 1, 2);
        Set<Set<Integer>> result_2 = new HashSet<>();
        while (sub_it_2.hasNext())
            result_2.add(sub_it_2.next());
        assertEquals(2, result_2.size());
        assertTrue(result_2.contains(Set.of(1, 2)));
        assertTrue(result_2.contains(Set.of(1, 4)));
    }
}
