package de.umr.fixcon.graphFunctions.standardFunctions;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.GraphFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.Factory.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.*;

class GraphFunction_Test {

    private MutableGraph<Integer> graphA, graphB, g;    //1st: empty graph.  2nd: small graph.  3rd: graph from files
    private GraphFunction func;

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
            func = new EdgeCount();
        }

        @Test
        void optimum() {
            assertThrows(IllegalArgumentException.class, () -> func.optimum(-1));
            assertThrows(IllegalArgumentException.class, () -> func.optimum(-999));
            assertEquals(0, func.optimum(0));
            assertEquals(0, func.optimum(1));
            assertEquals(1, func.optimum(2));
            assertEquals(3, func.optimum(3));
            assertEquals(6, func.optimum(4));
            assertEquals(10, func.optimum(5));
            assertEquals(15, func.optimum(6));
            assertEquals(21, func.optimum(7));
        }

        @Test
        void small() {
            assertTrue(func.isEdgeMonotone());

            assertEquals(0, func.apply(graphA));

            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));

            graphA.putEdge(1, 3);
            assertEquals(2, func.apply(graphA));

            assertEquals(5, func.apply(graphB));
        }

        @Test
        void big() throws IOException {
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
            func = new MinDegree();
        }

        @Test
        void minDegree_exception_on_empty_graph() {
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA));
        }

        @Test
        void minDegree_Test_Small() {
            assertTrue(func.isEdgeMonotone());

            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));

            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);   //graph is now a triangle
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
        GraphFunction maxDegree = new MaxDegree();
        GraphFunction negativeMaxDegree = new NegMaxDegree();

        @Test
        void maxDegree_exception_on_empty_graph() {
            assertThrows(IllegalArgumentException.class, () -> negativeMaxDegree.apply(graphA));
            assertThrows(IllegalArgumentException.class, () -> maxDegree.apply(graphA));
        }

        @Test
        void maxDegree_Test_Small() {
            assertTrue(maxDegree.isEdgeMonotone());
            assertFalse(negativeMaxDegree.isEdgeMonotone());

            graphA.putEdge(1, 2);
            assertEquals(1, maxDegree.apply(graphA));
            assertEquals(3, maxDegree.apply(graphB));
            assertEquals(-1, negativeMaxDegree.apply(graphA));
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
            func = new IsTree();
        }

        @Test
        void isTree_exception_on_empty_graph() {
            assertFalse(func.isEdgeMonotone());
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA));
        }

        @Test
        void isTree() throws IOException {
            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(3, 4);
            assertEquals(0, func.apply(graphA));

            assertEquals(0, func.apply(graphB));

            assertEquals(1, func.apply(graphFromNetworkRepo(".//graph_files//CustomTree.txt")));
        }
    }

    @Nested
    class isDegreeConstrained_Tests {
        @BeforeEach
        void setObjective() {
            func = new IsDegreeConstrained();
        }

        @Test
        void isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone());
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, 2, 1));
        }

        @Test
        void isDegreeConstrained_Test_Small() throws IOException {
            assertEquals(1, func.apply(graphA, 123, 999));     //graph is empty

            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(1, 4);
            assertEquals(0, func.apply(graphA, 1, 2));
            assertEquals(1, func.apply(graphA, 1, 3));

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
            func = new IsNRegular();
        }

        @Test
        void is_N_regular_exception_Test() {
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, -10));
        }

        @Test
        void is_N_regular_Test_Small() {
            graphA.putEdge(1, 2);
            graphA.putEdge(1, 3);
            graphA.putEdge(2, 3);
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
            func = new HasNoTriangles();
        }

        @Test
        void test_small_graphs() throws IOException {
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(1, 2);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(1, 3);
            assertEquals(1, func.apply(graphA));
            graphA.putEdge(2, 3);
            assertEquals(0, func.apply(graphA));

            assertEquals(0, func.apply(graphB));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(0, func.apply(g));
        }
    }

}