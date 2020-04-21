package unitTests.fixcon.wrappers;

import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction;
import de.umr.fixcon.wrappers.CFCO_Problem;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CFCO_Problem_Test {

    @Test
    void constructor_test() {
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        Graphs.addEdgeWithVertices(g, 1, 2);
        CFCO_Problem p = new CFCO_Problem(g, 5, new EdgeCountFunction(), new ArrayList<>());
        assertEquals(2, p.getGraph().vertexSet().size());
        assertEquals(5, p.getSubgraphSize());
        assertEquals(0, p.getParameters().size());

        p = new CFCO_Problem(g, 3, new EdgeCountFunction(), List.of(1, 2, 3, 4, 5));
        assertEquals(3, p.getSubgraphSize());
        assertEquals(5, p.getParameters().size());
    }
}