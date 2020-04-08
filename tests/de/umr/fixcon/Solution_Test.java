package de.umr.fixcon;

import com.google.common.graph.GraphBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Solution_Test {

    @Test
    void create_solution() {
        Solution s = new Solution(GraphBuilder.undirected().build(), 1);
        assertEquals(GraphBuilder.undirected().build(), s.graph);
        assertEquals(1, s.value);
    }
}