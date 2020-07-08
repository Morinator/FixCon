package de.umr.fixcon.graphFunctions

import de.umr.core.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NegMaxDegreeFunction_Test {

    private val f = NegMaxDegreeFunction()

    @Nested
    internal inner class Eval {

        @Test
        fun clique18() = assertEquals(-17, f.eval(createClique(18)))

        @Test
        fun path63() = assertEquals(-2, f.eval(createPath(63)))

        @Test
        fun star24() = assertEquals(-23, f.eval(createStar(24)))

        @Test
        fun circle115() = assertEquals(-2, f.eval(createCircle(115)))

        @Test
        fun completeBipartite_8_12() = assertEquals(-12, f.eval(createBipartite(8, 12)))
    }
}