package unitTests.fixcon.wrappers;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction;
import de.umr.fixcon.wrappers.CFCO_Problem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CFCO_Problem_Test {

    @Test
    void constructor_test() {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        assertThrows(IllegalArgumentException.class, () -> new CFCO_Problem(g, 5, new EdgeCountFunction()));

        g.putEdge(1, 2);
        CFCO_Problem p = new CFCO_Problem(g, 5, new EdgeCountFunction());
        assertEquals(2, p.graph.nodes().size());
        assertEquals(5, p.subgraphSize);
        assertEquals(0, p.parameters.length);

        p = new CFCO_Problem(g, 3, new EdgeCountFunction(), 1, 2, 3, 4, 5);
        assertEquals(3, p.subgraphSize);
        assertEquals(5, p.parameters.length);
    }
}