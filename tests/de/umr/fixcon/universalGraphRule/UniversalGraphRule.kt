package de.umr.fixcon.universalGraphRule

import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.fixcon.graphFunctions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UniversalGraphRule {

    @Nested
    internal inner class Size1 {

        @Nested
        inner class Applicable {

            @Test
            fun triangleFree() = assertTrue(universalGraphRule(createStar(10), 1, TriangleFreeFunction(), 1))

            @Test
            fun minDegree() = assertTrue(universalGraphRule(createStar(5), 1, MinDegreeFunction(), 3))

            @Test
            fun acyclic() = assertTrue(universalGraphRule(createCircle(8), 1, AcyclicFunction(), 0))

            @Test
            fun edgeCount() = assertTrue(universalGraphRule(createClique(4), 1, EdgeCountFunction(), 11))

            @Test
            fun degreeConstrained() = assertTrue(universalGraphRule(createCircle(7), 1, DegreeConstrainedFunction(listOf(1,2)), 1))

            @Test
            fun regular() = assertTrue(universalGraphRule(createCircle(7), 1, RRegularFunction(listOf(2)), 0))

            @Test
            fun diameter() = assertTrue(universalGraphRule(createPath(7), 1, DiameterFunction(), 7))
        }

        @Nested
        inner class NotApplicable {

            @Test
            fun triangleFree() = assertTrue(universalGraphRule(createStar(10), 1, TriangleFreeFunction(), 1))

            @Test
            fun minDegree() = assertTrue(universalGraphRule(createStar(5), 1, MinDegreeFunction(), 3))

            @Test
            fun acyclic() = assertTrue(universalGraphRule(createCircle(8), 1, AcyclicFunction(), 0))

            @Test
            fun edgeCount() = assertTrue(universalGraphRule(createClique(4), 1, EdgeCountFunction(), 11))

            @Test
            fun degreeConstrained() = assertTrue(universalGraphRule(createCircle(7), 1, DegreeConstrainedFunction(listOf(1,2)), 1))

            @Test
            fun regular() = assertTrue(universalGraphRule(createCircle(7), 1, RRegularFunction(listOf(2)), 0))

            @Test
            fun diameter() = assertTrue(universalGraphRule(createPath(7), 1, DiameterFunction(), 7))
        }

    }
}