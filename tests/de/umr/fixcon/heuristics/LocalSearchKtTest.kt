package de.umr.fixcon.heuristics

import de.umr.core.createClique
import de.umr.core.fromUnweightedEdges
import de.umr.core.dataStructures.Solution
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import de.umr.fixcon.graphFunctions.MinDegreeFunction
import de.umr.fixcon.localSearchStep
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LocalSearchKtTest {

    @Nested
    internal inner class localSearchOneStep_test {

        @Test
        fun triangleWithOneExtraEdge() {
            val f = EdgeCountFunction(3)
            val sub = fromUnweightedEdges(listOf(1 to 3, 3 to 4))
            val sol = Solution(sub, f.eval(sub))

            assertEquals(2, sol.value)
            localSearchStep(fromUnweightedEdges(listOf(1 to 2, 1 to 3, 2 to 3, 3 to 4)), f, sol)
            assertEquals(3, sol.value)
        }

        @Test
        fun cantBeImproved() {
            val k = 10
            val g = createClique(20)
            val f = MinDegreeFunction(k)
            val sub = createClique(k)
            val sol = Solution(sub, f.eval(sub))

            assertEquals(9, sol.value)
            localSearchStep(g, f, sol)
            assertEquals(9, sol.value)
            assertEquals(setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), sol.subgraph.vertexSet())
        }

        @Test
        fun replacesCutPoint() {
            val k = 3
            val g = fromUnweightedEdges(listOf(1 to 2, 1 to 3, 1 to 4, 1 to 6, 2 to 4, 2 to 6, 3 to 5, 3 to 6, 4 to 5))
            val f = EdgeCountFunction(k)
            val sub = fromUnweightedEdges(listOf(1 to 2, 1 to 3, 3 to 5))
            val sol = Solution(sub, f.eval(sub))

            assertEquals(3, sol.value)
            localSearchStep(g, f, sol)  //replace cutVertex in sol with a better vertex that reconnects the graph
            assertEquals(4, sol.value)

        }
    }
}