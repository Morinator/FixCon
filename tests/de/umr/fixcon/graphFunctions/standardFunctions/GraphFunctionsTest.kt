//package de.umr.fixcon.graphFunctions.standardFunctions
//
//import de.umr.GraphFile.*
//import de.umr.core.createCircle
//import de.umr.core.createClique
//import de.umr.core.dataStructures.VertexOrderedGraph
//import de.umr.core.io.graphFromFile
//import de.umr.fixcon.graphFunctions.GraphFunction
//import org.jgrapht.Graphs.addEdgeWithVertices
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Nested
//import org.junit.jupiter.api.Test
//
//internal class GraphFunctionsTest {
//    private var emptyGraph = VertexOrderedGraph<Int>()
//    private var size5Graph = VertexOrderedGraph<Int>()
//    private var g = VertexOrderedGraph<Int>()
//    private lateinit var func: GraphFunction
//
//    @BeforeEach
//    fun setup() {  //rebuilds them every time to make sure the inner tests are independent from another
//        addEdgeWithVertices(size5Graph, 1, 2)
//        addEdgeWithVertices(size5Graph, 1, 3)
//        addEdgeWithVertices(size5Graph, 2, 3)
//        addEdgeWithVertices(size5Graph, 3, 4)
//        addEdgeWithVertices(size5Graph, 4, 5)
//    }
//
//    @Nested
//    internal inner class edgeCount_Tests {
//
//        @BeforeEach
//        fun setObjective() {
//            func = EdgeCountFunction()
//        }
//
//        @Test
//        fun optimum() {
//            assertEquals(0, func.globalOptimum(0))
//            assertEquals(0, func.globalOptimum(1))
//            assertEquals(1, func.globalOptimum(2))
//            assertEquals(3, func.globalOptimum(3))
//            assertEquals(6, func.globalOptimum(4))
//            assertEquals(10, func.globalOptimum(5))
//            assertEquals(15, func.globalOptimum(6))
//            assertEquals(21, func.globalOptimum(7))
//        }
//
//        @Test
//        fun small() {
//
//            assertEquals(0, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 1, 3)
//            assertEquals(2, func.eval(emptyGraph))
//            assertEquals(5, func.eval(size5Graph))
//        }
//
//        @Test
//        fun big() {
//            g = graphFromFile(InfPower)
//            assertEquals(6594, func.eval(g))
//
//            g = graphFromFile(SocBrightkite)
//            assertEquals(212945, func.eval(g))
//
//            g = graphFromFile(Hamming10_4)
//            assertEquals(434176, func.eval(g))
//
//            g = graphFromFile(BioDmela)
//            assertEquals(25569, func.eval(g))
//
//            g = graphFromFile(InfOpenFlights)
//            assertEquals(15677, func.eval(g))
//        }
//
//        @Test
//        fun additionBound() {   // arithmetic series: 20 + 21 + 22 + 23 + 24 = 110
//            g = createCircle(20)
//            assertEquals(110, EdgeCountFunction().completeAdditionBound(g, 25))
//        }
//    }
//
//    @Nested
//    internal inner class minDegreeTest {
//
//        @BeforeEach
//        fun setObjective() {
//            func = MinDegreeFunction()
//        }
//
//        @Test
//        fun minDegree_Test_Small() {
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 1, 3)
//            addEdgeWithVertices(emptyGraph, 2, 3)   //graph is now a triangle
//            assertEquals(2, func.eval(emptyGraph))
//        }
//
//        @Test
//        fun minDegree_Test_Big() {
//            assertEquals(2, func.eval(createCircle(5)))
//            assertEquals(4, func.eval(createClique(5)))
//            assertEquals(1, func.eval(graphFromFile(BioDmela)))
//            assertEquals(1, func.eval(graphFromFile(Sample)))
//        }
//    }
//
//    @Nested
//    internal inner class isAcyclic_Tests {
//        @BeforeEach
//        fun setObjective() {
//            func = AcyclicFunction()
//        }
//
//        @Test
//        fun isAcyclic() {
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 3, 4)
//            assertEquals(0, func.eval(emptyGraph))
//            assertEquals(0, func.eval(size5Graph))
//            assertEquals(1, func.eval(graphFromFile(CustomTree)))
//        }
//    }
//
//    @Nested
//    internal inner class isDegreeConstrained_Tests {
//
//        @BeforeEach
//        fun setObjective() {
//            func = DegreeConstrainedFunction()
//        }
//
//        //graph is empty
//        @Test
//        fun isDegreeConstrained_Test_Small() {
//            assertEquals(1, DegreeConstrainedFunction(listOf(123, 999)).eval(emptyGraph)) //graph is empty
//
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            addEdgeWithVertices(emptyGraph, 1, 3)
//            addEdgeWithVertices(emptyGraph, 1, 4)
//            assertEquals(0, DegreeConstrainedFunction(listOf(1, 2)).eval(emptyGraph))
//            assertEquals(1, DegreeConstrainedFunction(listOf(1, 3)).eval(emptyGraph ))
//
//            assertEquals(1, DegreeConstrainedFunction(listOf(1, 3)).eval(size5Graph))
//        }
//
//        @Test
//        fun isDegreeConstrained_Test_Big() {
//            g = graphFromFile(InfPower)
//            assertEquals(1, DegreeConstrainedFunction(listOf(1, 19)).eval(g))
//            assertEquals(0, DegreeConstrainedFunction(listOf(2, 19)).eval(g))
//            assertEquals(0, DegreeConstrainedFunction(listOf(1, 18)).eval(g))
//        }
//    }
//
//    @Nested
//    internal inner class is_N_regular_Tests {
//        @BeforeEach
//        fun setObjective() {
//            func = NRegularFunction()
//        }
//
//        @Test
//        fun is_N_regular_Test_Small() {
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            addEdgeWithVertices(emptyGraph, 1, 3)
//            addEdgeWithVertices(emptyGraph, 2, 3)
//            assertEquals(1, NRegularFunction(listOf(2)).eval(emptyGraph))
//        }
//
//        @Test
//        fun is_N_regular_Test_Big() {
//            g = graphFromFile(Hamming10_4)
//            assertEquals(1, NRegularFunction(listOf(848)).eval(g ))
//        }
//    }
//
//    @Nested
//    internal inner class hasNoTriangles_Tests {
//        @BeforeEach
//        fun setObjective() {
//            func = TriangleFreeFunction()
//        }
//
//        @Test
//        fun test_small_graphs() {
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 1, 2)
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 1, 3)
//            assertEquals(1, func.eval(emptyGraph))
//            addEdgeWithVertices(emptyGraph, 2, 3)
//            assertEquals(0, func.eval(emptyGraph))
//            assertEquals(0, func.eval(size5Graph))
//        }
//    }
//}