package de.umr.fixcon.universalGraphRule

import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import de.umr.fixcon.graphFunctions.MinDegreeFunction
import de.umr.fixcon.graphFunctions.TriangleFreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UniversalGraphRule {

    @Nested
    inner class Applicable {

        @Test
        fun applicableA() = assertTrue(universalGraphRule(createStar(10), 1, TriangleFreeFunction(), 1))
    }

    @Nested
    inner class NotApplicable {

        @Test
        fun notApplicableA() = assertFalse(universalGraphRule(createPath(3), 1, MinDegreeFunction(), 1))
    }
}