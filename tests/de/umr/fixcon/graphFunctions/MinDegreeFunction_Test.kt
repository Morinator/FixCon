package de.umr.fixcon.graphFunctions

import de.umr.core.*
import de.umr.core.dataStructures.GraphFile
import de.umr.core.io.graphFromFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MinDegreeFunction_Test {
    private val f = MinDegreeFunction()

    @Test
    fun optimumTest() {
        val optima = listOf(0, 1, 2, 33, 64, 99)
        val kValues = listOf(1, 2, 3, 34, 65, 100)
        (optima.indices).forEach { assertEquals(optima[it], MinDegreeFunction(kValues[it]).globalOptimum()) }
    }

    @Nested
    internal inner class Eval {
        @Test
        fun clique() = assertEquals(19, f.eval(createClique(20)))

        @Test
        fun path() = assertEquals(1, f.eval(createPath(43)))

        @Test
        fun circle() = assertEquals(2, f.eval(createCircle(37)))

        @Test
        fun customTree() = assertEquals(1, f.eval(graphFromFile(GraphFile.CustomTree)))

        @Test
        fun star() = assertEquals(1, f.eval(createStar(46)))

        @Test
        fun dmela() = assertEquals(1, f.eval(graphFromFile(GraphFile.BioDmela)))

        @Test
        fun openFlights() = assertEquals(1, f.eval(graphFromFile(GraphFile.InfOpenFlights)))

        @Test
        fun bipartite_5_6() = assertEquals(5, f.eval(createBipartite(5,6)))
    }

    @Nested
    internal inner class AdditionBound {

        @Test
        fun f5_5() = assertEquals(0, MinDegreeFunction(5).completeBound(createStar(5)))

        @Test
        fun f3_4() = assertEquals(1, MinDegreeFunction(4).completeBound(createPath(3)))

        @Test
        fun f20_25() = assertEquals(5, MinDegreeFunction(25).completeBound(createCircle(20)))

        @Test
        fun f10_30() = assertEquals(20, MinDegreeFunction(30).completeBound(createCircle(10)))
    }

}