package de.umr.fixcon.heuristics

import de.umr.core.GraphFile
import de.umr.core.dataStructures.vCount
import de.umr.core.graphFromFile
import de.umr.core.removeComponentsSmallerThreshold
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import de.umr.fixcon.getHeuristic
import de.umr.useHeuristic
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.AsSubgraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class SomeSolutionGeneratorTest {

    @Test
    fun heuristic_test() {
        val numTestedGraphs = 8
        val size = 7
        for (path in GraphFile.values().take(numTestedGraphs)) {
            val g = graphFromFile(path).also { removeComponentsSmallerThreshold(it, size) }
            val f = EdgeCountFunction(size)

            val sol = getHeuristic(g, f)
            if (useHeuristic) {
                assertTrue(ConnectivityInspector(sol.subgraph).isConnected)
                assertTrue(ConnectivityInspector(AsSubgraph(g, sol.subgraph.vertexSet())).isConnected)
                assertEquals(size, sol.subgraph.vCount)
            }
        }

    }
}