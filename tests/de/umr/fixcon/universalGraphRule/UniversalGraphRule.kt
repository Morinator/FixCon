package de.umr.fixcon.universalGraphRule

import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createStar
import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.fromUnweightedEdges
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UniversalGraphRule {

    @Nested
    inner class Evaluation {

        @Test
        fun applicableA() {
            val p = Problem(createClique(3), EdgeCountFunction())
            assertTrue(universalGraphRule(p, 1, 4))
        }
    }
}