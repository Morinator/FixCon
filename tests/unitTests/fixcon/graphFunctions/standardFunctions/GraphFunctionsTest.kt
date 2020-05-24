package unitTests.fixcon.graphFunctions.standardFunctions

import de.umr.FilePaths
import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.core.StandardGraphFactory
import de.umr.core.dataStructures.VertexOrderedGraph
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
    private var graphA = VertexOrderedGraph<Int>()
    private var graphB = VertexOrderedGraph<Int>()
    private var g = VertexOrderedGraph<Int>()
    private lateinit var func: GraphFunction

    @BeforeEach
    fun setup() {  //rebuilds them every time to make sure the inner tests are independent from another
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
            func = EdgeCountFunction
        }

        @Test
        fun optimum() {
            assertThrows(IllegalArgumentException::class.java) { func.globalUpperBound(-1) }
            assertThrows(IllegalArgumentException::class.java) { func.globalUpperBound(-999) }
            assertEquals(0, func.globalUpperBound(0))
            assertEquals(0, func.globalUpperBound(1))
            assertEquals(1, func.globalUpperBound(2))
            assertEquals(3, func.globalUpperBound(3))
            assertEquals(6, func.globalUpperBound(4))
            assertEquals(10, func.globalUpperBound(5))
            assertEquals(15, func.globalUpperBound(6))
            assertEquals(21, func.globalUpperBound(7))
        }

        @Test
        fun small() {
            assertTrue(func.isEdgeMonotone)
            assertEquals(0, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            assertEquals(2, func.eval(graphA, ArrayList()))
            assertEquals(5, func.eval(graphB, ArrayList()))
        }

        @Test
        fun big() {
            g = graphFromNetworkRepo("data/network repository/infrastructure/inf-power.mtx")
            assertEquals(6594, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo("data/network repository/social/soc-brightkite/soc-brightkite.txt")
            assertEquals(212945, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx")
            assertEquals(434176, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(847244, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo("data/network repository/bio/bio-dmela.mtx")
            assertEquals(25569, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//inf-openflights.edges")
            assertEquals(15677, func.eval(g, ArrayList()))
        }

        @Test
        fun additionBound() {
            g = StandardGraphFactory.createCircle(20)
            assertEquals(110, EdgeCountFunction.additionBound(g, 25))
        }
    }

    @Nested
    internal inner class minDegreeTest {
        @BeforeEach
        fun setObjective() {
            func = MinDegreeFunction
        }

        @Test
        fun minDegree_Test_Small() {
            assertTrue(func.isEdgeMonotone)
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 2, 3) //graph is now a triangle
            assertEquals(2, func.eval(graphA, ArrayList()))
        }

        @Test
        fun minDegree_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(912, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo("data/network repository/bio/bio-dmela.mtx")
            assertEquals(1, func.eval(g, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//coPapersCiteseer.mtx")
            assertEquals(1, func.eval(g, ArrayList()))
        }
    }

    @Nested
    internal inner class isAcyclic_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsAcyclicFunction
        }

        @Test
        fun isAcyclic() {
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 3, 4)
            assertEquals(0, func.eval(graphA, ArrayList()))
            assertEquals(0, func.eval(graphB, ArrayList()))
            assertEquals(1, func.eval(graphFromNetworkRepo(".//graph_files//CustomTree.txt"), ArrayList()))
        }
    }

    @Nested
    internal inner class isDegreeConstrained_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsDegreeConstrainedFunction
        }

        @Test
        fun isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone)
            assertThrows(IllegalArgumentException::class.java) { func.eval(graphA, listOf(2, 1)) }
        }

        //graph is empty
        @Test
        fun isDegreeConstrained_Test_Small() {
            assertEquals(1, func.eval(graphA, listOf(123, 999))) //graph is empty
            addEdgeWithVertices(graphA, 1, 2)
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 1, 4)
            assertEquals(0, func.eval(graphA, listOf(1, 2)))
            assertEquals(1, func.eval(graphA, listOf(1, 3)))
            assertEquals(1, func.eval(graphB, listOf(1, 3)))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            println(func.eval(g, listOf(912, 11111)))
            assertEquals(0, func.eval(g, listOf(913, 11111)))
            assertEquals(1, func.eval(g, listOf(912, 11111)))
        }

        @Test
        fun isDegreeConstrained_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(1, func.eval(g, listOf(912, 1330)))
            assertEquals(0, func.eval(g, listOf(913, 1330)))
            assertEquals(0, func.eval(g, listOf(912, 1329)))
            g = graphFromNetworkRepo(FilePaths.infPower)
            assertEquals(1, func.eval(g, listOf(1, 19)))
            assertEquals(0, func.eval(g, listOf(2, 19)))
            assertEquals(0, func.eval(g, listOf(1, 18)))
        }
    }

    @Nested
    internal inner class is_N_regular_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsNRegularFunction
        }

        @Test
        fun is_N_regular_exception_Test() {
            assertThrows(IllegalArgumentException::class.java) { func.eval(graphA, listOf(-10)) }
        }

        @Test
        fun is_N_regular_Test_Small() {
            addEdgeWithVertices(graphA, 1, 2)
            addEdgeWithVertices(graphA, 1, 3)
            addEdgeWithVertices(graphA, 2, 3)
            assertEquals(1, func.eval(graphA, listOf(2)))
        }

        @Test
        fun is_N_regular_Test_Big() {
            g = graphFromNetworkRepo(".//graph_files//hamming10-4.mtx")
            assertEquals(1, func.eval(g, listOf(848)))
        }
    }

    @Nested
    internal inner class hasNoTriangles_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsTriangleFreeFunction
        }

        @Test @Disabled
        fun test_small_graphs() {
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 2)
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 1, 3)
            assertEquals(1, func.eval(graphA, ArrayList()))
            addEdgeWithVertices(graphA, 2, 3)
            assertEquals(0, func.eval(graphA, ArrayList()))
            assertEquals(0, func.eval(graphB, ArrayList()))
            g = graphFromNetworkRepo(".//graph_files//p-hat1500-3.mtx")
            assertEquals(0, func.eval(g, ArrayList()))
        }
    }
}