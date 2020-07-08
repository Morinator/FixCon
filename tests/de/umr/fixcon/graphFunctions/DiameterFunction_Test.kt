package de.umr.fixcon.graphFunctions

import de.umr.core.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DiameterFunction_Test {
    private val f = DiameterFunction()

    @Test
    fun optimum() {
        assertEquals(0, DiameterFunction(1).globalOptimum())
        assertEquals(5, DiameterFunction(6).globalOptimum())
        assertEquals(99, DiameterFunction(100).globalOptimum())
    }

    @Test
    fun vertexAdditionBound() = assertEquals(1, f.vertexAdditionBound)

    @Nested
    internal inner class Eval {

        @Test
        fun clique15() = assertEquals(1, f.eval(createClique(15)))

        @Test
        fun clique79() = assertEquals(1, f.eval(createClique(79)))

        @Test
        fun path10() = assertEquals(9, f.eval(createPath(10)))

        @Test
        fun path145() = assertEquals(144, f.eval(createPath(145)))

        @Test
        fun star30() = assertEquals(2, f.eval(createStar(30)))

        @Test
        fun star394() = assertEquals(2, f.eval(createStar(394)))

        @Test
        fun circle40() = assertEquals(20, f.eval(createCircle(40)))

        @Test
        fun circle15() = assertEquals(7, f.eval(createCircle(15)))

        @Test
        fun completeBipartite_3_3() = assertEquals(2, f.eval(createBipartite(3, 3)))

        @Test
        fun completeBipartite_10_15() = assertEquals(2, f.eval(createBipartite(10, 15)))
    }
}