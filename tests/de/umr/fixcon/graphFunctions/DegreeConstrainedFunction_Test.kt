package de.umr.fixcon.graphFunctions

import de.umr.core.GraphFile
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.graphFromFile
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import de.umr.fixcon.graphFunctions.DegreeConstrainedFunction as dcf

internal class DegreeConstrainedFunction_Test {
    private val emptyGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)

    @Nested
    internal inner class Eval {
        @Test
        fun isDegreeConstrained_Test_Small() {
            assertEquals(0, dcf(args = listOf(123, 999)).eval(emptyGraph)) //graph is empty

            val g = createStar(4)
            assertTrue(dcf(args = listOf(1, 2)).eval(g) < 0)
            assertEquals(0, dcf(args = listOf(1, 3)).eval(emptyGraph))
        }

        @Test
        fun isDegreeConstrained_Test_Big() {
            val g = graphFromFile(GraphFile.InfPower)
            assertEquals(0, dcf(dummyK, listOf(1, 19)).eval(g)) //info from NetworkRepo
            assertTrue(dcf(dummyK, listOf(2, 19)).eval(g) < 0)
            assertTrue(dcf(dummyK, listOf(1, 18)).eval(g) < 0)
        }
    }

    @Nested
    internal inner class CompleteBound {

        @Test
        fun allVertices_OK() = assertEquals(3, dcf( 5, listOf(3, 4)).completeBound(createPath(3)))

        @Test
        fun only1Vertex_OK() = assertEquals(1, dcf(4,listOf(3, 4)).completeBound(createPath(3)))

        @Test
        fun noVertex_OK() = assertEquals(0, dcf(15, listOf(10, 12)).completeBound(createPath(10)))
    }
}