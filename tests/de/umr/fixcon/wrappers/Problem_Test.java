package de.umr.fixcon.wrappers;

import com.google.common.graph.GraphBuilder;
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem_Test {

    @Test
    void constructor_test() {
        Problem p = new Problem(GraphBuilder.undirected().build(), 5, new EdgeCount());
        assertEquals(GraphBuilder.undirected().build(), p.graph);
        assertEquals(5, p.subgraphSize);
        assertEquals(0, p.parameters.length);

        p = new Problem(GraphBuilder.undirected().build(), 3, new EdgeCount(), 1, 2, 3, 4, 5);
        assertEquals(GraphBuilder.undirected().build(), p.graph);
        assertEquals(3, p.subgraphSize);
        assertEquals(5, p.parameters.length);
    }
}