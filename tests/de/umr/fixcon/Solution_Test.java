package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Solution_Test {

    @Test
    void create_solution() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        g.putEdge(1, 3);
        g.putEdge(2, 3);
        g.putEdge(3, 4);
        Solution s = new Solution(g);
        s.update(Set.of(1, 2, 4), 1);

        Graph<Integer> resultGraph = s.getSolution();
        assertTrue(resultGraph.hasEdgeConnecting(1, 2));
        assertFalse(resultGraph.hasEdgeConnecting(1, 4));
        assertEquals(1, s.getValue());
    }
}