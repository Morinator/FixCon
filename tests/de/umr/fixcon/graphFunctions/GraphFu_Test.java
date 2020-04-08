package de.umr.fixcon.graphFunctions;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.standardFunctions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.*;

class GraphFu_Test {

    private MutableGraph<Integer> graphA, graphB, g;    //1st: empty graph.  2nd: small graph.  3rd: graph from files
    private GraphFu func;

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

        @BeforeEach
        void setObjective() {
            func = new EdgeCountFu();
        }

        @Test
        void edgeCount_Test_Small() {
            assertTrue(func.isEdgeMonotone());

            assertEquals(0, func.apply(graphA));

            graphA.putEdge(1, 2);
            assertEquals(1, func.optimum(graphA));
            assertEquals(1, func.apply(graphA));

            graphA.putEdge(1, 3);
            assertEquals(3, func.optimum(graphA));
            assertEquals(2, func.apply(graphA));

            assertEquals(10, func.optimum(graphB));
            assertEquals(5, func.apply(graphB));
        }

        @Test
        void edgeCount_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(6594, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//soc-brightkite.mtx");
            assertEquals(212945, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx");
            assertEquals(434176, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(847244, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx");
            assertEquals(25569, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//inf-openflights.edges");
            assertEquals(15677, func.apply(g));
        }
    }

    @Nested
    class minDegree_Tests {

        @BeforeEach
        void setObjective() {
            func = new MinDegreeFu();
        }

        @Test
        void minDegree_exception_on_empty_graph() {
            Exception e = assertThrows(IllegalArgumentException.class, () -> func.apply(graphA));
            assertEquals("Graph does not contain any edges", e.getMessage());
        }

        @Test
        void minDegree_Test_Small() {
            assertTrue(func.isEdgeMonotone());

            graphA.putEdge(1, 2);
            assertEquals(1, func.optimum(graphA));
            assertEquals(1, func.apply(graphA));

            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);   //graph is now a triangle
            assertEquals(2, func.optimum(graphA));
            assertEquals(2, func.apply(graphA));
        }

        @Test
        void minDegree_Test_Big() throws IOException {

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(912, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx");
            assertEquals(1, func.apply(g));

            g = graphFromNetworkRepo(".//graph_files//coPapersCiteseer.mtx");
            assertEquals(1, func.apply(g));

        }
    }

    @Nested
            //Tests both ObjectiveFunctions maxDegree and negativeMaxDegree at once
    class maxDegree_Plus_And_Minus_Tests {
        GraphFu maxDegree = new MaxDegreeFu();
        GraphFu negativeMaxDegree = new NegMaxDegreeFu();

        @Test
        void maxDegree_exception_on_empty_graph() {
            assertThrows(IllegalArgumentException.class, () -> negativeMaxDegree.apply(graphA));
            Exception e = assertThrows(IllegalArgumentException.class, () -> maxDegree.apply(graphA));
            assertEquals("Graph does not contain any edges", e.getMessage());
        }

        @Test
        void maxDegree_Test_Small() {
            assertTrue(maxDegree.isEdgeMonotone());
            assertFalse(negativeMaxDegree.isEdgeMonotone());

            graphA.putEdge(1, 2);
            assertEquals(1, maxDegree.optimum(graphA));
            assertEquals(1, maxDegree.apply(graphA));
            assertEquals(4, maxDegree.optimum(graphB));
            assertEquals(3, maxDegree.apply(graphB));
            assertEquals(-1, negativeMaxDegree.optimum(graphA));
            assertEquals(-1, negativeMaxDegree.apply(graphA));
            assertEquals(-1, negativeMaxDegree.optimum(graphB));
            assertEquals(-3, negativeMaxDegree.apply(graphB));
        }

        @Test
        void maxDegree_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(19, maxDegree.apply(g));
            assertEquals(-19, negativeMaxDegree.apply(g));
        }
    }

    @Nested
    class isTree_Tests {

        @BeforeEach
        void setObjective() {
            func = new IsTreeFu();
        }

        @Test
        void isTree_exception_on_empty_graph() {
            assertFalse(func.isEdgeMonotone());
            Exception e = assertThrows(IllegalArgumentException.class, () -> func.apply(graphA));
            assertEquals("This graph has 0 vertices", e.getMessage());
        }

        @Test
        void isTree() throws IOException {
            assertEquals(1, func.optimum(graphA));
            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(3, 4);
            assertEquals(0, func.apply(graphA));

            assertEquals(1, func.optimum(graphB));
            assertEquals(0, func.apply(graphB));

            assertEquals(1, func.apply(graphFromNetworkRepo(".//graph_files//CustomTree.txt")));
        }
    }

    @Nested
    class isDegreeConstrained_Tests {
        @BeforeEach
        void setObjective() {
            func = new IsDegreeConstrainedFu();
        }

        @Test
        void isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone());
            Exception e = assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, 2, 1));
            assertEquals("Lower and upper bounds are in wrong order", e.getMessage());
        }

        @Test
        void isDegreeConstrained_Test_Small() throws IOException {
            assertEquals(1, func.apply(graphA, 123, 999));     //graph is empty

            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(1, 4);
            assertEquals(1, func.optimum(graphA));
            assertEquals(0, func.apply(graphA, 1, 2));
            assertEquals(1, func.apply(graphA, 1, 3));

            assertEquals(1, func.optimum(graphB));
            assertEquals(1, func.apply(graphB, 1, 3));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            System.out.println(func.apply(g, 913, 11111));

        }

        @Test
        void isDegreeConstrained_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(1, func.apply(g, 912, 1330));
            assertEquals(0, func.apply(g, 913, 1330));
            assertEquals(0, func.apply(g, 912, 1329));

            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(1, func.apply(g, 1, 19));
            assertEquals(0, func.apply(g, 2, 19));
            assertEquals(0, func.apply(g, 1, 18));
        }
    }

    @Nested
    class is_N_regular_Tests {
        @BeforeEach
        void setObjective() {
            func = new IsNRegularFu();
        }

        @Test
        void is_N_regular_exception_Test() {
            Exception e = assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, -10));
            assertEquals("A vertex cannot have a degree lower than 0", e.getMessage());
        }

        @Test
        void is_N_regular_Test_Small() {
            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);
            assertEquals(1, func.optimum(graphA));
            assertEquals(1, func.optimum(graphA, 123, 789));
            assertEquals(1, func.apply(graphA, 2));
        }

        @Test
        void is_N_regular_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx");
            assertEquals(1, func.apply(g, 848));
        }
    }

    @Nested
    class hasNoTriangles_Tests {
        @BeforeEach
        void setObjective() {
            func = new HasNoTrianglesFu();
        }

        @Test
        void test_small_graphs() throws IOException {
            assertEquals(1, func.optimum(graphA));
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(1, 3);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(2, 3);
            assertEquals(0, func.apply(graphA));

            assertEquals(1, func.optimum(graphB));
            assertEquals(0, func.apply(graphB));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(0, func.apply(g));
        }
    }

}