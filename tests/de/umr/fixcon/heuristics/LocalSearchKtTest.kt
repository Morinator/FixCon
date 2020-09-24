package de.umr.fixcon.heuristics

import de.umr.core.createClique
import de.umr.core.fromUnweightedEdges
import de.umr.fixcon.Instance
import de.umr.fixcon.Solution
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import de.umr.fixcon.graphFunctions.MinDegreeFunction
import de.umr.fixcon.heuristic.localSearch
import de.umr.fixcon.heuristic.localSearchStep
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LocalSearchKtTest {

    @Nested
    internal inner class localSearchOneStep_test {

        @Test
        fun triangleWithOneExtraEdge() {
            val p = Instance(fromUnweightedEdges(listOf(1 to 2, 1 to 3, 2 to 3, 3 to 4)), EdgeCountFunction(3))
            val sub = fromUnweightedEdges(listOf(1 to 3, 3 to 4))
            val sol = Solution(sub, p.eval(sub))

            assertEquals(2, sol.value)
            localSearchStep(p, sol)
            assertEquals(3, sol.value)
        }

        @Test
        fun cantBeImproved() {
            val k = 10
            val p = Instance(createClique(20), MinDegreeFunction(k))
            val sub = createClique(k)
            val sol = Solution(sub, p.eval(sub))

            assertEquals(9, sol.value)
            localSearchStep(p, sol)
            assertEquals(9, sol.value)
            assertEquals(setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), sol.subgraph.vertexSet())
        }

        @Test
        fun replacesCutPoint() {
            val k = 3
            val g = fromUnweightedEdges(listOf(1 to 2, 1 to 3, 1 to 4, 1 to 6, 2 to 4, 2 to 6, 3 to 5, 3 to 6, 4 to 5))
            val p = Instance(g, EdgeCountFunction(k))
            val sub = fromUnweightedEdges(listOf(1 to 2, 1 to 3, 3 to 5))
            val sol = Solution(sub, p.eval(sub))

            assertEquals(3, sol.value)
            localSearchStep(p, sol)  //replace cutVertex in sol with a better vertex that reconnects the graph
            assertEquals(4, sol.value)

        }
    }

    @Nested
    internal inner class fullLocalSearch_test {
        @Test
        fun twoImprovementsPossible() {
            val p = Instance(fromUnweightedEdges(listOf(1 to 2, 1 to 3, 1 to 4, 2 to 3, 2 to 4, 3 to 4, 3 to 5, 5 to 6)), EdgeCountFunction(3))
            val sub = fromUnweightedEdges(listOf(1 to 3, 3 to 5, 5 to 6))
            val sol = Solution(sub, p.eval(sub))

            assertEquals(3, sol.value)
            localSearch(p, sol)
            assertEquals(6, sol.value)
        }
    }
}