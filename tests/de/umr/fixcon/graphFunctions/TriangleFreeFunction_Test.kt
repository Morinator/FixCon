package de.umr.fixcon.graphFunctions

import de.umr.core.*
import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.io.graphFromFile
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TriangleFreeFunction_Test {
    private val f = TriangleFreeFunction()

    @Nested
    internal inner class Eval {
        @Test
        fun clique() = assertEquals(0, f.eval(createClique(20)))

        @Test
        fun path() = assertEquals(1, f.eval(createPath(43)))

        @Test
        fun circle() = assertEquals(1, f.eval(createCircle(37)))

        @Test
        fun customTree() = assertEquals(1, f.eval(graphFromFile(GraphFile.CustomTree)))

        @Test
        fun star() = assertEquals(1, f.eval(createStar(46)))

        @Test
        fun dmela() = assertEquals(0, f.eval(graphFromFile(GraphFile.BioDmela)))

        @Test
        fun openFlights() = assertEquals(0, f.eval(graphFromFile(GraphFile.InfOpenFlights)))

        @Test
        fun bipartite_5_6() = assertEquals(1, f.eval(createBipartite(5, 6)))
    }

    @Test
    fun smallCustomGraphs() {
        val emptyGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        assertEquals(1, f.eval(emptyGraph))

        emptyGraph.addEdgeWithVertices(1,2)
        assertEquals(1, f.eval(emptyGraph))

        emptyGraph.addEdgeWithVertices(1,3)
        assertEquals(1, f.eval(emptyGraph))

        emptyGraph.addEdgeWithVertices(2,3)
        assertEquals(0, f.eval(emptyGraph))
    }

    @Test
    fun additionBound() = assertEquals(0, f.vertexAdditionBound)
}