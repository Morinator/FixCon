package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.GraphFile.*
import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.dataStructures.addEdgeWithVertices
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.*
import org.jgrapht.Graphs.addEdgeWithVertices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import de.umr.fixcon.graphFunctions.DegreeConstrainedFunction as dcf

internal class GraphFunctionsTest {
    private var emptyGraph = VertexOrderedGraph<Int>()
    private var size5Graph = VertexOrderedGraph<Int>()
    private var g = VertexOrderedGraph<Int>()

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
        private val fu = EdgeCountFunction()

        @Test
        fun optimum() {
            for ((k, opt) in listOf(0, 0, 1, 3, 6, 10, 15, 21).withIndex())
                assertEquals(opt, EdgeCountFunction(k).globalOptimum())
        }

        @Test
        fun small() {
            assertEquals(0, EdgeCountFunction(0).eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, EdgeCountFunction(2).eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 1, 3)
            assertEquals(2, EdgeCountFunction(3).eval(emptyGraph))
            assertEquals(5, EdgeCountFunction(5).eval(size5Graph))
        }

        @Test
        fun big() {
            g = graphFromFile(InfPower)
            assertEquals(6594, fu.eval(g))

            g = graphFromFile(SocBrightkite)
            assertEquals(212945, fu.eval(g))

            g = graphFromFile(BioDmela)
            assertEquals(25569, fu.eval(g))

            g = graphFromFile(InfOpenFlights)
            assertEquals(15677, fu.eval(g))
        }

        @Test   // arithmetic series: 20 + 21 + 22 + 23 + 24 = 110
        fun additionBound() = assertEquals(110, EdgeCountFunction(25).completeBound(createCircle(20)))
    }

    @Nested
    internal inner class minDegreeTest {
        private val fu = MinDegreeFunction()

        @Test
        fun minDegree_Test_Small() {
            emptyGraph.addEdgeWithVertices(1, 2)
            assertEquals(1, fu.eval(emptyGraph))
            emptyGraph.addEdgeWithVertices(1, 3)
            emptyGraph.addEdgeWithVertices(2, 3)   //graph is now a triangle
            assertEquals(2, fu.eval(emptyGraph))
        }

        @Test
        fun minDegree_Test_Big() {
            assertEquals(2, fu.eval(createCircle(5)))
            assertEquals(4, fu.eval(createClique(5)))
            assertEquals(1, fu.eval(graphFromFile(BioDmela)))
            assertEquals(1, fu.eval(graphFromFile(Sample)))
        }
    }

    @Nested
    internal inner class isAcyclic_Tests {
        private val fu = AcyclicFunction()

        @Test
        fun isAcyclic() {
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, fu.eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 3, 4)
            assertEquals(0, fu.eval(emptyGraph))
            assertEquals(0, fu.eval(size5Graph))
            assertEquals(1, fu.eval(graphFromFile(CustomTree)))
        }
    }

    @Nested
    internal inner class isDegreeConstrained_Tests {
        @Test
        fun isDegreeConstrained_Test_Small() {
            assertEquals(0, dcf(listOf(123, 999)).eval(emptyGraph)) //graph is empty

            addEdgeWithVertices(emptyGraph, 1, 2)
            addEdgeWithVertices(emptyGraph, 1, 3)
            addEdgeWithVertices(emptyGraph, 1, 4)
            assertTrue(dcf(listOf(1, 2)).eval(emptyGraph) < 0)
            assertEquals(0, dcf(listOf(1, 3)).eval(emptyGraph))

            assertEquals(0, dcf(listOf(1, 3), dummyK).eval(size5Graph))
        }

        @Test
        fun isDegreeConstrained_Test_Big() {
            g = graphFromFile(InfPower)
            assertEquals(0, dcf(listOf(1, 19), dummyK).eval(g))
            assertTrue(dcf(listOf(2, 19), dummyK).eval(g) < 0)
            assertTrue(dcf(listOf(1, 18), dummyK).eval(g) < 0)
        }
    }

    @Nested
    internal inner class is_N_regular_Tests {

        @Test
        fun is_N_regular_Test_Small() {
            addEdgeWithVertices(emptyGraph, 1, 2)
            addEdgeWithVertices(emptyGraph, 1, 3)
            addEdgeWithVertices(emptyGraph, 2, 3)
            assertEquals(0, RRegularFunction(listOf(2), dummyK).eval(emptyGraph))
        }

        @Test
        fun is_N_regular_Test_Big() {
            g = graphFromFile(Hamming10_4)
            assertEquals(0, RRegularFunction(listOf(848), dummyK).eval(g))
        }

        @Test
        fun infPower() = assertTrue(RRegularFunction(listOf(3), dummyK).eval(graphFromFile(InfPower)) < 0)
    }

    @Nested
    internal inner class hasNoTriangles_Tests {
        private val fu = TriangleFreeFunction(dummyK)

        @Test
        fun test_small_graphs() {
            assertEquals(1, fu.eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 1, 2)
            assertEquals(1, fu.eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 1, 3)
            assertEquals(1, fu.eval(emptyGraph))
            addEdgeWithVertices(emptyGraph, 2, 3)
            assertEquals(0, fu.eval(emptyGraph))
            assertEquals(0, fu.eval(size5Graph))
        }
    }
}