package unitTests.fixcon.graphFunctions.standardFunctions

import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.graphFunctions.standardFunctions.*
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import java.util.*

internal class GraphFunctionsTest {
    private var graphA: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
    private var graphB: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
    private var g: Graph<Int, DefaultEdge> = SimpleGraph(DefaultEdge::class.java)
    private lateinit var func: GraphFunction

    @BeforeEach
    fun setup() {  //rebuilds them every time to make sure the inner tests are independent from another
        graphA = SimpleGraph(DefaultEdge::class.java)
        graphB = SimpleGraph(DefaultEdge::class.java)
        addEdgeWithVertices(graphB, 1, 2)
        addEdgeWithVertices(graphB, 1, 3)
        addEdgeWithVertices(graphB, 2, 3)
        addEdgeWithVertices(graphB, 3, 4)
        addEdgeWithVertices(graphB, 4, 5)
    }

    @Nested
    internal inner class edgeCount_Tests {
        @BeforeEach
        fun setObjective() {
            func = EdgeCountFunction()
        }

        @Test
        fun optimum() {
            assertThrows(IllegalArgumentException::class.java) { func.optimum(-1) }
            assertThrows(IllegalArgumentException::class.java) { func.optimum(-999) }
            assertEquals(0.0, func.optimum(0))
            assertEquals(0.0, func.optimum(1))
            assertEquals(1.0, func.optimum(2))
            assertEquals(3.0, func.optimum(3))
            assertEquals(6.0, func.optimum(4))
            assertEquals(10.0, func.optimum(5))
            assertEquals(15.0, func.optimum(6))
            assertEquals(21.0, func.optimum(7))
        }

        @Test
        fun small() {
            assertTrue(func.isEdgeMonotone)
            assertEquals(0.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            assertEquals(2.0, func.apply(graphA, ArrayList()))
            assertEquals(5.0, func.apply(graphB, ArrayList()))
        }

        @Test
        fun big() {
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx")
            assertEquals(6594.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//soc-brightkite.mtx")
            assertEquals(212945.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx")
            assertEquals(434176.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(847244.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx")
            assertEquals(25569.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//inf-openflights.edges")
            assertEquals(15677.0, func.apply(g, ArrayList()))
        }
    }

    @Nested
    internal inner class minDegreeTest {
        @BeforeEach
        fun setObjective() {
            func = MinDegreeFunction()
        }

        @Test
        fun minDegree_Test_Small() {
            assertTrue(func.isEdgeMonotone)
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 2, 3) //graph is now a triangle
            assertEquals(2.0, func.apply(graphA, ArrayList()))
        }

        @Test
        fun minDegree_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(912.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//bio-dmela.mtx")
            assertEquals(1.0, func.apply(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//coPapersCiteseer.mtx")
            assertEquals(1.0, func.apply(g, ArrayList()))
        }
    }

    @Nested
    internal inner class isTree_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsTreeFunction()
        }

        @Test
        fun isTree() {
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 3, 4)
            assertEquals(0.0, func.apply(graphA, ArrayList()))
            assertEquals(0.0, func.apply(graphB, ArrayList()))
            assertEquals(1.0, func.apply(graphFromNetworkRepo(".//graph_files//CustomTree.txt"), ArrayList()))
        }
    }

    @Nested
    internal inner class isDegreeConstrained_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsDegreeConstrainedFunction()
        }

        @Test
        fun isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone)
            assertThrows(IllegalArgumentException::class.java) { func.apply(graphA, listOf(2, 1)) }
        }

        //graph is empty
        @Test
        fun isDegreeConstrained_Test_Small() {
            assertEquals(1.0, func.apply(graphA, listOf(123, 999))) //graph is empty
            addEdgeWithVertices(graphA, 1, 2)
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 1, 4)
            assertEquals(0.0, func.apply(graphA, listOf(1, 2)))
            assertEquals(1.0, func.apply(graphA, listOf(1, 3)))
            assertEquals(1.0, func.apply(graphB, listOf(1, 3)))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            println(func.apply(g, listOf(912, 11111)))
            assertEquals(0.0, func.apply(g, listOf(913, 11111)))
            assertEquals(1.0, func.apply(g, listOf(912, 11111)))
        }

        @Test
        fun isDegreeConstrained_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(1.0, func.apply(g, listOf(912, 1330)))
            assertEquals(0.0, func.apply(g, listOf(913, 1330)))
            assertEquals(0.0, func.apply(g, listOf(912, 1329)))
            g = graphFromNetworkRepo(".//graph_files//inf-power.mtx")
            assertEquals(1.0, func.apply(g, listOf(1, 19)))
            assertEquals(0.0, func.apply(g, listOf(2, 19)))
            assertEquals(0.0, func.apply(g, listOf(1, 18)))
        }
    }

    @Nested
    internal inner class is_N_regular_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsNRegularFunction()
        }

        @Test
        fun is_N_regular_exception_Test() {
            assertThrows(IllegalArgumentException::class.java) { func.apply(graphA, listOf(-10)) }
        }

        @Test
        fun is_N_regular_Test_Small() {
            addEdgeWithVertices(graphA, 1, 2)
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 2, 3)
            assertEquals(1.0, func.apply(graphA, listOf(2)))
        }

        @Test
        fun is_N_regular_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx")
            assertEquals(1.0, func.apply(g, listOf(848)))
        }
    }

    @Nested
    internal inner class hasNoTriangles_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsTriangleFreeFunction()
        }

        @Test @Disabled
        fun test_small_graphs() {
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            assertEquals(1.0, func.apply(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 2, 3)
            assertEquals(0.0, func.apply(graphA, ArrayList()))
            assertEquals(0.0, func.apply(graphB, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(0.0, func.apply(g, ArrayList()))
        }
    }
}