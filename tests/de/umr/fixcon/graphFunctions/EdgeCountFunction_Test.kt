package de.umr.fixcon.graphFunctions

import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.dataStructures.GraphFile.*
import de.umr.core.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EdgeCountFunction_Test {
    private val f = EdgeCountFunction()
    private lateinit var g: Graph<Int, DefaultEdge>

    @Test
    fun optimumTest() {
        for ((k, opt) in listOf(0, 0, 1, 3, 6, 10, 15, 21).withIndex())
            assertEquals(opt, EdgeCountFunction(k).globalOptimum())
    }

    @Nested
    internal inner class Eval {
        @Test
        fun clique() = assertEquals(20 * 19 / 2, f.eval(createClique(20)))

        @Test
        fun path() = assertEquals(42, f.eval(createPath(43)))

        @Test
        fun circle() = assertEquals(37, f.eval(createCircle(37)))

        @Test
        fun customTree() = assertEquals(18, f.eval(graphFromFile(CustomTree)))

        @Test
        fun star() = assertEquals(45, f.eval(createStar(46)))

        @Test
        fun big() {
            g = graphFromFile(InfPower)
            assertEquals(6594, f.eval(g))

            g = graphFromFile(BioDmela)
            assertEquals(25569, f.eval(g))

            g = graphFromFile(InfOpenFlights)
            assertEquals(15677, f.eval(g))
        }
    }

    @Nested
    internal inner class AdditionBound {

        @Test
        fun f5_5() = assertEquals(0, EdgeCountFunction(5).completeBound(createStar(5)))

        @Test
        fun f3_4() = assertEquals(3, EdgeCountFunction(4).completeBound(createPath(3)))

        @Test
        fun f20_25() = assertEquals(110, EdgeCountFunction(25).completeBound(createCircle(20)))

        @Test
        fun f10_30() = assertEquals(390, EdgeCountFunction(30).completeBound(createCircle(10)))
    }
}