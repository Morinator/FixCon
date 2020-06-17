package de.umr.fixcon.heuristics

import de.umr.GraphFile
import de.umr.core.io.graphFromFile
import de.umr.core.vertexCount
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.wrappers.Problem
import de.umr.removeSmallComponents
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.AsSubgraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class SomeSolutionGeneratorTest {

    @Test
    fun someSolution_test() {
        val numTestedGraphs = 8
        val size = 7
        for (path in GraphFile.values().take(numTestedGraphs)) {
            println("$size $path")
            val g = graphFromFile(path).also { removeSmallComponents(it, size) }
            val p = Problem(g, size, EdgeCountFunction)

            val sol = someSolution(p)
            assertTrue(ConnectivityInspector(sol.subgraph).isConnected)
            assertTrue(ConnectivityInspector(AsSubgraph(p.g, sol.subgraph.vertexSet())).isConnected)
            assertEquals(size, sol.subgraph.vertexCount)
        }

    }
}