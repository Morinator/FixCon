package de.umr.fixcon.heuristics

import de.umr.core.GraphFile
import de.umr.core.extensions.vertexCount
import de.umr.core.io.graphFromFile
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.Heuristic
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.EdgeCountFunction
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
            val g = graphFromFile(path).also { removeSmallComponents(it, size) }
            val p = Problem(g, EdgeCountFunction(size))

            val sol = Heuristic(p).get()
            assertTrue(ConnectivityInspector(sol.subgraph).isConnected)
            assertTrue(ConnectivityInspector(AsSubgraph(p.g, sol.subgraph.vertexSet())).isConnected)
            assertEquals(size, sol.subgraph.vertexCount)
        }

    }
}