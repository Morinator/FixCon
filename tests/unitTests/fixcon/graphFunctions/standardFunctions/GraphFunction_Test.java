package unitTests.fixcon.graphFunctions.standardFunctions;

import de.umr.fixcon.graphFunctions.GraphFunction;
import de.umr.fixcon.graphFunctions.standardFunctions.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.*;

class GraphFunction_Test {

    private Graph<Integer, DefaultEdge> graphA, graphB, g;    //1st: empty graph.  2nd: small graph.  3rd: graph from files
    private GraphFunction func;

    @BeforeEach
    void setup() {  //rebuilds them every time to make sure the inner tests are independent from another
        graphA = new SimpleGraph<>(DefaultEdge.class);
        graphB = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(graphB, 1, 2);
        Graphs.addEdgeWithVertices(graphB, 1, 3);
        Graphs.addEdgeWithVertices(graphB, 2, 3);
        Graphs.addEdgeWithVertices(graphB, 3, 4);
        Graphs.addEdgeWithVertices(graphB, 4, 5);
    }

    @Nested
    class edgeCount_Tests {

        @BeforeEach
        void setObjective() {
            func = new EdgeCountFunction();
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

            assertEquals(0, func.apply(graphA, new ArrayList<>()));

            Graphs.addEdgeWithVertices(graphA, 1, 2);
            assertEquals(1, func.apply(graphA, new ArrayList<>()));

            Graphs.addEdgeWithVertices(graphA, 1, 3);
            assertEquals(2, func.apply(graphA, new ArrayList<>()));

            assertEquals(5, func.apply(graphB, new ArrayList<>()));
        }

        @Test
        void big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(6594, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//soc-brightkite.mtx");
            assertEquals(212945, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx");
            assertEquals(434176, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(847244, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx");
            assertEquals(25569, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//inf-openflights.edges");
            assertEquals(15677, func.apply(g, new ArrayList<>()));
        }
    }

    @Nested
    class minDegree_Tests {

        @BeforeEach
        void setObjective() {
            func = new MinDegreeFunction();
        }

        @Test
        void minDegree_Test_Small() {
            assertTrue(func.isEdgeMonotone());

            Graphs.addEdgeWithVertices(graphA, 1, 2);
            assertEquals(1, func.apply(graphA, new ArrayList<>()));

            Graphs.addEdgeWithVertices(graphA, 1, 3);
            Graphs.addEdgeWithVertices(graphA, 2, 3);   //graph is now a triangle
            assertEquals(2, func.apply(graphA, new ArrayList<>()));
        }

        @Test
        void minDegree_Test_Big() throws IOException {

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(912, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx");
            assertEquals(1, func.apply(g, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//coPapersCiteseer.mtx");
            assertEquals(1, func.apply(g, new ArrayList<>()));

        }
    }

    @Nested
            //Tests both ObjectiveFunctions maxDegree and negativeMaxDegree at once
    class maxDegree_Plus_And_Minus_Tests {
        final GraphFunction maxDegree = new MaxDegreeFunction();
        final GraphFunction negativeMaxDegree = new NegMaxDegreeFunction();

        @Test
        void maxDegree_Test_Small() {
            assertTrue(maxDegree.isEdgeMonotone());
            assertFalse(negativeMaxDegree.isEdgeMonotone());

            Graphs.addEdgeWithVertices(graphA, 1, 2);
            assertEquals(1, maxDegree.apply(graphA, new ArrayList<>()));
            assertEquals(3, maxDegree.apply(graphB, new ArrayList<>()));
            assertEquals(-1, negativeMaxDegree.apply(graphA, new ArrayList<>()));
            assertEquals(-3, negativeMaxDegree.apply(graphB, new ArrayList<>()));
        }

        @Test
        void maxDegree_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(19, maxDegree.apply(g, new ArrayList<>()));
            assertEquals(-19, negativeMaxDegree.apply(g, new ArrayList<>()));
        }
    }

//    @Nested
//    class isTree_Tests {
//
//        @BeforeEach
//        void setObjective() {
//            func = new IsTreeFunction();
//        }
//
//        @Test
//        void isTree_exception_on_empty_graph() {
//            assertFalse(func.isEdgeMonotone());
//            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, new ArrayList<>()));
//        }
//
//        @Test
//        void isTree() throws IOException {
//            graphA.putEdge(1, 2);
//            assertEquals(1, func.apply(graphA, new ArrayList<>()));
//            graphA.putEdge(3, 4);
//            assertEquals(0, func.apply(graphA, new ArrayList<>()));
//
//            assertEquals(0, func.apply(graphB, new ArrayList<>()));
//
//            assertEquals(1, func.apply(graphFromNetworkRepo(".//graph_files//CustomTree.txt"), new ArrayList<>()));
//        }
//    }
//
    @Nested
    class isDegreeConstrained_Tests {
        @BeforeEach
        void setObjective() {
            func = new IsDegreeConstrainedFunction();
        }

        @Test
        void isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone());
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, List.of(2, 1)));
        }

        @Test
        void isDegreeConstrained_Test_Small() throws IOException {
            assertEquals(1, func.apply(graphA, List.of(123, 999)));     //graph is empty

            Graphs.addEdgeWithVertices(graphA, 1, 2);
            Graphs.addEdgeWithVertices(graphA, 1, 3);
            Graphs.addEdgeWithVertices(graphA, 1, 4);
            assertEquals(0, func.apply(graphA, List.of(1, 2)));
            assertEquals(1, func.apply(graphA, List.of(1, 3)));

            assertEquals(1, func.apply(graphB, List.of(1, 3)));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            System.out.println(func.apply(g, List.of(912, 11111)));
            assertEquals(0, func.apply(g, List.of(913, 11111)));
            assertEquals(1, func.apply(g, List.of(912, 11111)));
        }

        @Test
        void isDegreeConstrained_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(1, func.apply(g, List.of(912,1330)));
            assertEquals(0, func.apply(g, List.of(913, 1330)));
            assertEquals(0, func.apply(g, List.of(912, 1329)));

            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx");
            assertEquals(1, func.apply(g, List.of(1, 19)));
            assertEquals(0, func.apply(g, List.of(2, 19)));
            assertEquals(0, func.apply(g, List.of(1, 18)));
        }
    }

    @Nested
    class is_N_regular_Tests {
        @BeforeEach
        void setObjective() {
            func = new IsNRegularFunction();
        }

        @Test
        void is_N_regular_exception_Test() {
            assertThrows(IllegalArgumentException.class, () -> func.apply(graphA, List.of(-10)));
        }

        @Test
        void is_N_regular_Test_Small() {
            Graphs.addEdgeWithVertices(graphA, 1, 2);
            Graphs.addEdgeWithVertices(graphA, 1, 3);
            Graphs.addEdgeWithVertices(graphA, 2, 3);
            assertEquals(1, func.apply(graphA, List.of(2)));
        }

        @Test
        void is_N_regular_Test_Big() throws IOException {
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx");
            assertEquals(1, func.apply(g, List.of(848)));
        }
    }

    @Nested
    class hasNoTriangles_Tests {
        @BeforeEach
        void setObjective() {
            func = new HasNoTrianglesFunction();
        }

        @Test
        void test_small_graphs() throws IOException {
            assertEquals(1, func.apply(graphA, new ArrayList<>()));
            Graphs.addEdgeWithVertices(graphA, 1, 2);
            assertEquals(1, func.apply(graphA, new ArrayList<>()));
            Graphs.addEdgeWithVertices(graphA, 1, 3);
            assertEquals(1, func.apply(graphA, new ArrayList<>()));
            Graphs.addEdgeWithVertices(graphA, 2, 3);
            assertEquals(0, func.apply(graphA, new ArrayList<>()));

            assertEquals(0, func.apply(graphB, new ArrayList<>()));

            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx");
            assertEquals(0, func.apply(g, new ArrayList<>()));
        }
    }

}