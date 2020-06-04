package unitTests.fixcon.graphFunctions.standardFunctions

import de.umr.FilePaths
import de.umr.core.GraphFactory
import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.graphFunctions.standardFunctions.*
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphFunctionsTest {
    private var emptyGraph = VertexOrderedGraph<Int>()
    private var size5Graph = VertexOrderedGraph<Int>()
    private var g = VertexOrderedGraph<Int>()
    private lateinit var func: GraphFunction

    @BeforeEach
    fun setup() {  //rebuilds them every time to make sure the inner tests are independent from another
        addEdgeWithVertices(size5Graph, 1, 2)
        addEdgeWithVertices(size5Graph, 1, 3)
        addEdgeWithVertices(size5Graph, 2, 3)
        addEdgeWithVertices(size5Graph, 3, 4)
        addEdgeWithVertices(size5Graph, 4, 5)
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

            assertEquals(0, func.eval(emptyGraph, emptyList()))
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, func.eval(emptyGraph, emptyList()))
            addEdgeWithVertices(emptyGraph, 1, 3)
            assertEquals(2, func.eval(emptyGraph, emptyList()))
            assertEquals(5, func.eval(size5Graph, emptyList()))
        }

        @Test
        fun big() {
            g = graphFromNetworkRepo(FilePaths.infPower)
            assertEquals(6594, func.eval(g, emptyList()))

            g = graphFromNetworkRepo(FilePaths.socBrightkite)
            assertEquals(212945, func.eval(g, emptyList()))

            g = graphFromNetworkRepo(FilePaths.hamming10_4)
            assertEquals(434176, func.eval(g, emptyList()))

            g = graphFromNetworkRepo(FilePaths.pHat_1500_3)
            assertEquals(847244, func.eval(g, emptyList()))

            g = graphFromNetworkRepo(FilePaths.bioDmela)
            assertEquals(25569, func.eval(g, emptyList()))

            g = graphFromNetworkRepo(FilePaths.infOpenFlights)
            assertEquals(15677, func.eval(g, emptyList()))
        }

        @Test
        fun additionBound() {   // arithmetic series: 20 + 21 + 22 + 23 + 24 = 110
            g = GraphFactory.createCircle(20)
            assertEquals(110, EdgeCountFunction.completeAdditionBound(g, 25, emptyList()))
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

            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, func.eval(emptyGraph, emptyList()))
            addEdgeWithVertices(emptyGraph, 1, 3)
            addEdgeWithVertices(emptyGraph, 2, 3)   //graph is now a triangle
            assertEquals(2, func.eval(emptyGraph, emptyList()))
        }

        @Test
        fun minDegree_Test_Big() {
            g = graphFromNetworkRepo(FilePaths.pHat_1500_3)
            assertEquals(912, func.eval(g, ArrayList()))

            g = graphFromNetworkRepo(FilePaths.bioDmela)
            assertEquals(1, func.eval(g, ArrayList()))

            g = graphFromNetworkRepo(FilePaths.coPapersCiteseer)
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
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, func.eval(emptyGraph, ArrayList()))
            addEdgeWithVertices(emptyGraph, 3, 4)
            assertEquals(0, func.eval(emptyGraph, ArrayList()))
            assertEquals(0, func.eval(size5Graph, ArrayList()))
            assertEquals(1, func.eval(graphFromNetworkRepo(".//graph_files//CustomTree.txt"), ArrayList()))
        }
    }

    @Nested
    internal inner class isDegreeConstrained_Tests {

        @BeforeEach
        fun setObjective() {
            func = IsDegreeConstrainedFunction
        }

        @Test   //first argument can't be bigger than second. The interval [1, 2] is specified by listOf(1, 2)
        fun isDegreeConstrained_exception_on_wrong_borders() {
            assertFalse(func.isEdgeMonotone)
            assertThrows(IllegalArgumentException::class.java) { func.eval(emptyGraph, listOf(2, 1)) }
        }

        //graph is empty
        @Test
        fun isDegreeConstrained_Test_Small() {
            assertEquals(1, func.eval(emptyGraph, listOf(123, 999))) //graph is empty

            addEdgeWithVertices(emptyGraph, 1, 2)
            addEdgeWithVertices(emptyGraph, 1, 3)
            addEdgeWithVertices(emptyGraph, 1, 4)
            assertEquals(0, func.eval(emptyGraph, listOf(1, 2)))
            assertEquals(1, func.eval(emptyGraph, listOf(1, 3)))

            assertEquals(1, func.eval(size5Graph, listOf(1, 3)))
        }

        @Test
        fun isDegreeConstrained_Test_Big() {
            g = graphFromNetworkRepo(FilePaths.pHat_1500_3)
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
            assertThrows(IllegalArgumentException::class.java) { func.eval(emptyGraph, listOf(-10)) }
        }

        @Test
        fun is_N_regular_Test_Small() {
            addEdgeWithVertices(emptyGraph, 1, 2)
            addEdgeWithVertices(emptyGraph, 1, 3)
            addEdgeWithVertices(emptyGraph, 2, 3)
            assertEquals(1, func.eval(emptyGraph, listOf(2)))
        }

        @Test
        fun is_N_regular_Test_Big() {
            g = graphFromNetworkRepo(FilePaths.hamming10_4)
            assertEquals(1, func.eval(g, listOf(848)))
        }
    }

    @Nested
    internal inner class hasNoTriangles_Tests {
        @BeforeEach
        fun setObjective() {
            func = IsTriangleFreeFunction
        }

        @Test
        @Disabled
        fun test_small_graphs() {
            assertEquals(1, func.eval(emptyGraph, ArrayList()))
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, func.eval(emptyGraph, ArrayList()))
            addEdgeWithVertices(emptyGraph, 1, 3)
            assertEquals(1, func.eval(emptyGraph, ArrayList()))
            addEdgeWithVertices(emptyGraph, 2, 3)
            assertEquals(0, func.eval(emptyGraph, ArrayList()))
            assertEquals(0, func.eval(size5Graph, ArrayList()))
            g = graphFromNetworkRepo(FilePaths.pHat_1500_3)
            assertEquals(0, func.eval(g, ArrayList()))
        }
    }
}