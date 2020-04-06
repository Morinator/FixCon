package de.umr.fixcon.graphFunctions;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.endpointPairs_from_NetworkRepo;
import static de.umr.core.Factory.graph_from_endpointPairs;
import static org.junit.jupiter.api.Assertions.*;

class Objective_Test {

    private MutableGraph<Integer> graphA;
    private MutableGraph<Integer> graphB;
    private Objective graphFunction;

    @BeforeEach
    void setup() {  //rebuilds them every time to make sure the inner tests are independent from another
        graphA = GraphBuilder.undirected().build();
        graphB = GraphBuilder.undirected().build();
        graphB.putEdge(1, 2);
        graphB.putEdge(1, 3);
        graphB.putEdge(2, 3);
        graphB.putEdge(3, 4);
        graphB.putEdge(4, 5);
    }

    @Nested
    class edgeCount_Tests {
        @Test
        void edgeCount_Test_Small() {
            graphFunction = new Objective(StandardObjectives.edgeCount);
            assertTrue(graphFunction.isEdgeMonotone);

            assertEquals(0, graphFunction.apply(graphA));

            graphA.putEdge(1, 2);
            assertEquals(1, graphFunction.apply(graphA));

            assertEquals(5, graphFunction.apply(graphB));
        }

        @Test
        void edgeCount_Test_Big() throws IOException {
            graphFunction = new Objective(StandardObjectives.edgeCount);
            MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//inf-power.mtx"));
            assertEquals(6594, graphFunction.apply(g));

            g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//soc-brightkite.mtx"));
            assertEquals(212945, graphFunction.apply(g));
        }
    }

    @Nested
    class minDegree_Tests {
        @Test
        void minDegree_exception_on_empty_graph() {
            graphFunction = new Objective(StandardObjectives.minDegree);
            Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
            assertEquals("Graph does not contain any edges", e.getMessage());
        }

        @Test
        void minDegree_Test_Small() {
            graphFunction = new Objective(StandardObjectives.minDegree);
            assertTrue(graphFunction.isEdgeMonotone);

            graphA.putEdge(1, 2);
            assertEquals(1, graphFunction.apply(graphA));

            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);
            assertEquals(2, graphFunction.apply(graphA));
        }

        @Test
        void minDegree_Test_Big() throws IOException {
            graphFunction = new Objective(StandardObjectives.minDegree);
            MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//p-hat1500-3.mtx"));
            assertEquals(912, graphFunction.apply(g));
        }
    }

    @Nested
    class maxDegree_Tests {
        @Test
        void maxDegree_exception_on_empty_graph() {
            graphFunction = new Objective(StandardObjectives.maxDegree);
            Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
            assertEquals("Graph does not contain any edges", e.getMessage());
        }


        @Test
        void maxDegree_Test_Small() {
            graphFunction = new Objective(StandardObjectives.maxDegree);
            assertFalse(graphFunction.isEdgeMonotone);

            graphA.putEdge(1, 2);
            assertEquals(1, graphFunction.apply(graphA));

            assertEquals(3, graphFunction.apply(graphB));
        }

        @Test
        void maxDegree_Test_Big() throws IOException {
            graphFunction = new Objective(StandardObjectives.maxDegree);
            MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//inf-power.mtx"));
            assertEquals(19, graphFunction.apply(g));
        }
    }

    @Nested
    class isTree_Tests {
        @Test
        void isTree_exception_on_empty_graph() {
            graphFunction = new Objective(StandardObjectives.isTree);
            Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA));
            assertEquals("This graph has 0 vertices", e.getMessage());
        }

        @Test
        void isTree() throws IOException {
            graphFunction = new Objective(StandardObjectives.isTree);

            graphA.putEdge(1, 2);
            assertEquals(1, graphFunction.apply(graphA));
            graphA.putEdge(3, 4);
            assertEquals(0, graphFunction.apply(graphA));

            assertEquals(0, graphFunction.apply(graphB));

            assertEquals(1, graphFunction.apply(graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//CustomTree.txt"))));
        }
    }

    @Nested
    class isDegreeConstrained_Tests {
        @Test
        void isDegreeConstrained_exception_on_wrong_borders() {
            graphFunction = new Objective(StandardObjectives.isDegreeConstrained);
            Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA, 2, 1));
            assertEquals("Lower and upper bounds are in wrong order", e.getMessage());
        }

        @Test
        void isDegreeConstrained_Test_Small() throws IOException {
            graphFunction = new Objective(StandardObjectives.isDegreeConstrained);

            assertEquals(1, graphFunction.apply(graphA, 123, 999));     //graph is empty

            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(1, 4);
            assertEquals(0, graphFunction.apply(graphA, 1, 2));
            assertEquals(1, graphFunction.apply(graphA, 1, 3));

            assertEquals(1, graphFunction.apply(graphB, 1, 3));

            MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//p-hat1500-3.mtx"));
            graphFunction = new Objective(StandardObjectives.isDegreeConstrained);
            System.out.println(graphFunction.apply(g, 913, 11111));

        }
    }

    @Nested
    class is_N_regular_Tests {
        @Test
        void is_N_regular_exception_Test() {
            graphFunction = new Objective(StandardObjectives.is_N_Regular);
            Exception e = assertThrows(IllegalArgumentException.class, () -> graphFunction.apply(graphA, -10));
            assertEquals("A vertex cannot have a degree lower than 0", e.getMessage());
        }

        @Test
        void is_N_regular_Test_Small() {
            graphFunction = new Objective(StandardObjectives.is_N_Regular);
            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);
            assertEquals(1, graphFunction.apply(graphA, 2));
        }

        @Test
        void is_N_regular_Test_Big() throws IOException {
            graphFunction = new Objective(StandardObjectives.is_N_Regular);
            MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//hamming10-4.mtx"));
            assertEquals(1, graphFunction.apply(g, 848));
        }
    }
}