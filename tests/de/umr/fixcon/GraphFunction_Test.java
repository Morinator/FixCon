package de.umr.fixcon;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.endpointPairs_from_NetworkRepo;
import static de.umr.core.Factory.graph_from_endpointPairs;
import static org.junit.jupiter.api.Assertions.*;

class GraphFunction_Test {

    private final MutableGraph<Integer> graphA = GraphBuilder.undirected().build();
    private final MutableGraph<Integer> graphB = GraphBuilder.undirected().build();
    private GraphFunction graphFunction;

    @BeforeEach
    void setup() {
        graphB.putEdge(1, 2);
        graphB.putEdge(1, 3);
        graphB.putEdge(2, 3);
        graphB.putEdge(3, 4);
        graphB.putEdge(4, 5);
    }

    @Test
    void error_on_wrong_functionType() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new GraphFunction(StandardFunction.isNoTriangle_Indicator));
        assertEquals("This function is not supported yet", e.getMessage());
    }

    @Test
    void edgeCount_Test_Small() {
        graphFunction = new GraphFunction(StandardFunction.edgeCount);
        assertTrue(graphFunction.isEdgeMonotone);

        assertEquals(0, graphFunction.apply(graphA));

        graphA.putEdge(1, 2);
        assertEquals(1, graphFunction.apply(graphA));

        assertEquals(5, graphFunction.apply(graphB));
    }

    @Test
    void edgeCount_Test_Big() throws IOException {
        graphFunction = new GraphFunction(StandardFunction.edgeCount);
        MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//inf-power.mtx"));
        assertEquals(6594, graphFunction.apply(g));

        g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//soc-brightkite.mtx"));
        assertEquals(212945, graphFunction.apply(g));
    }

    @Test
    void minDegree_exception_on_empty_graph() {
        graphFunction = new GraphFunction(StandardFunction.minDegree);
        Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
        assertEquals("Graph does not contain any edges", e.getMessage());
    }

    @Test
    void minDegree_Test_Small() {
        graphFunction = new GraphFunction(StandardFunction.minDegree);
        assertTrue(graphFunction.isEdgeMonotone);

        graphA.putEdge(1, 2);
        assertEquals(1, graphFunction.apply(graphA));

        graphA.putEdge(1, 3);
        graphA.putEdge(2, 3);
        assertEquals(2, graphFunction.apply(graphA));
    }

    @Test
    void minDegree_Test_Big() throws IOException {
        graphFunction = new GraphFunction(StandardFunction.minDegree);
        MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//frb100-40.mtx"));
        assertEquals(3553, graphFunction.apply(g));
    }

    @Test
    void negativeMaxDegree_exception_on_empty_graph() {
        graphFunction = new GraphFunction(StandardFunction.negative_MaxDegree);
        Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
        assertEquals("Graph does not contain any edges", e.getMessage());
    }


    @Test
    void negativeMaxDegree_Test_Small() {
        graphFunction = new GraphFunction(StandardFunction.negative_MaxDegree);
        assertFalse(graphFunction.isEdgeMonotone);

        graphA.putEdge(1, 2);
        assertEquals(-1, graphFunction.apply(graphA));

        assertEquals(-3, graphFunction.apply(graphB));
    }

    @Test
    void negativeMaxDegree_Test_Big() throws IOException {
        graphFunction = new GraphFunction(StandardFunction.negative_MaxDegree);
        MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//inf-power.mtx"));
        assertEquals(-19, graphFunction.apply(g));
    }

    @Test
    void isTree_exception_on_empty_graph() {
        graphFunction = new GraphFunction(StandardFunction.isTree_Indicator);
        Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
        assertEquals("This graph has 0 vertices", e.getMessage());
    }

    @Test
    void isTree() throws IOException {
        graphFunction = new GraphFunction(StandardFunction.isTree_Indicator);

        graphA.putEdge(1, 2);
        assertEquals(1, graphFunction.apply(graphA));
        graphA.putEdge(3, 4);
        assertEquals(0, graphFunction.apply(graphA));

        assertEquals(0, graphFunction.apply(graphB));

        assertEquals(1, graphFunction.apply(graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//CustomTree.txt"))));
    }
}