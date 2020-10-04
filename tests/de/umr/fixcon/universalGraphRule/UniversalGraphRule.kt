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
import de.umr.core.dataStructures.OrderedGraph as og
import de.umr.fixcon.universalGraphRule as ugr

class UniversalGraphRule {

    @Nested
    internal inner class Size1 {

        @Nested
        inner class Applicable {

            @Test
            fun triangleFree() = assertTrue(ugr(og(createStar(10)), TriangleFreeFunction(11), 1))

            @Test
            fun minDegree() = assertTrue(ugr(og(createStar(10)), MinDegreeFunction(11), 3))

            @Test
            fun acyclic() = assertTrue(ugr(og(createCircle(8)), AcyclicFunction(9), 0))

            @Test
            fun edgeCount() = assertTrue(ugr(og(createClique(4)), EdgeCountFunction(5), 11))

            @Test
            fun degreeConstrained() = assertTrue(ugr(og(createCircle(7)), DegreeConstrainedFunction(listOf(1, 2), 8), 1))

            @Test
            fun regular() = assertTrue(ugr(og(createCircle(7)), RRegularFunction(listOf(2), 8), 0))

            @Test
            fun diameter() = assertTrue(ugr(og(createPath(7)), DiameterFunction(8), 7))
        }

        @Nested
        inner class NotApplicable {

            @Test
            fun triangleFree() = assertFalse(ugr(og(createStar(10)), TriangleFreeFunction(11), 0))

            @Test
            fun minDegree() = assertFalse(ugr(og(createStar(5)), MinDegreeFunction(6), 1))

            @Test
            fun minDegree2() = assertFalse(ugr(og(createClique(5)), MinDegreeFunction(6), 4))

            @Test
            fun acyclic() = assertFalse(ugr(og(createPath(8)), AcyclicFunction(9), 0))

            @Test
            fun edgeCount() = assertFalse(ugr(og(createClique(4)), EdgeCountFunction(5), 9))

            @Test
            fun degreeConstrained() = assertFalse(ugr(og(createPath(7)), DegreeConstrainedFunction(listOf(1, 2),8), -2))

            @Test
            fun regular() = assertFalse(ugr(og(createPath(3)), RRegularFunction(listOf(2),4), -1))

            @Test
            fun diameter() = assertFalse(ugr(og(createPath(7)), DiameterFunction(8), 6))
        }


    }
}