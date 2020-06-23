package de.umr.fixcon

import de.umr.GraphFile.CustomTree
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import de.umr.fixcon.graphFunctions.MinDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LocalSearchKtTest {

    @Nested
    internal inner class localSearchOneStep_test {

        @Test
        fun triangleWithOneExtraEdge() {
            val p = Problem(VertexOrderedGraph.fromUnweightedEdges(listOf(1 to 2, 1 to 3, 2 to 3, 3 to 4)), EdgeCountFunction(3))
            val sub = VertexOrderedGraph.fromUnweightedEdges(listOf(1 to 3, 3 to 4))
            val sol = Solution(sub, p.eval(sub))

            assertEquals(2, sol.value)
            localSearchOneStep(p, sol)
            assertEquals(3, sol.value)
        }

        @Test
        fun cantBeImproved() {
            val k = 10
            val p = Problem(createClique(20), MinDegreeFunction(k))
            val sub = createClique(k)
            val sol = Solution(sub, p.eval(sub))

            assertEquals(9, sol.value)
            localSearchOneStep(p, sol)
            assertEquals(9, sol.value)
            assertEquals(setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), sol.subgraph.vertexSet())
        }
    }

    @Nested
    internal inner class nonCutPoints {

        @Test
        fun path() = assertEquals(setOf(0, 4), nonCutPoints(createPath(5)))

        @Test
        fun star() = assertEquals(setOf(1, 2, 3, 4), nonCutPoints(createStar(5)))

        @Test
        fun customTree() = assertEquals(setOf(8, 13, 16, 17, 18, 19, 20), nonCutPoints(graphFromFile(CustomTree)))
    }
}