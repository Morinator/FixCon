package de.umr.fixcon.heuristics

import de.umr.core.GraphAlgorithms.inducedSubgraph
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import kotlin.math.log2
import kotlin.math.roundToInt

object LowerBoundGenerator {

    private const val runCountMultiplicand = 100

    fun getBound(problem: CFCO_Problem, runs: Int = runCountMultiplicand * log2(problem.originalGraph.size.toDouble()).roundToInt()): Solution {
        require(problem.originalGraph.size >= problem.targetSize) { "Target-size may not be smaller than graph" }
        val currentBestSolution = Solution(SimpleGraph(DefaultEdge::class.java), Int.MIN_VALUE)
        repeat(runs) {
            currentBestSolution.updateIfBetter(singleRun(problem))
        }
        return currentBestSolution.also { println("generated the lower bound is $currentBestSolution in $runs runs") }
    }

    private fun singleRun(problem: CFCO_Problem): Solution {
        val startVertex = problem.originalGraph.vertexSet().random()
        val subgraphSet = HashSet<Int>(setOf(startVertex))
        val extensionSet = HashSet<Int>(neighborSetOf(problem.originalGraph, startVertex))

        repeat(problem.targetSize - 1) {
            if (extensionSet.isEmpty()) return Solution(SimpleGraph(DefaultEdge::class.java), Int.MIN_VALUE)
            val nextVertex = extensionSet.random()
            subgraphSet += nextVertex
            extensionSet -= nextVertex
            extensionSet.addAll(neighborSetOf(problem.originalGraph, startVertex) - subgraphSet)
        }

        val subGraph = inducedSubgraph(problem.originalGraph, subgraphSet)
        return Solution(subGraph, problem.function.eval(subGraph, problem.parameters))
    }
}