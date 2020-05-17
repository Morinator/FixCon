package de.umr.fixcon.heuristics

import de.umr.core.GraphAlgorithms.inducedSubgraph
import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graphs.neighborSetOf
import kotlin.math.log2
import kotlin.math.max
import kotlin.math.roundToInt

object LowerBoundGenerator {

    private const val runCountMultiplicand = 100

    fun getBound(problem: CFCO_Problem, runs: Int = runCountMultiplicand * log2(problem.originalGraph.size.toDouble()).roundToInt()): Int {
        require(problem.originalGraph.size >= problem.targetSize) { "Target-size may not be smaller than graph" }
        var bestLowerBound = Int.MIN_VALUE
        repeat(runs) { bestLowerBound = max(bestLowerBound, singleRun(problem)) }
        return bestLowerBound.also { println("generated the lower bound is $bestLowerBound in $runs runs") }
    }

    private fun singleRun(problem: CFCO_Problem): Int {
        val startVertex = problem.originalGraph.vertexSet().random()
        val subgraphSet = HashSet<Int>(setOf(startVertex))
        val extensionSet = HashSet<Int>(neighborSetOf(problem.originalGraph, startVertex))

        repeat(problem.targetSize - 1) {
            if (extensionSet.isEmpty()) return Int.MIN_VALUE
            val nextVertex = extensionSet.random()
            subgraphSet += nextVertex
            extensionSet -= nextVertex
            neighborSetOf(problem.originalGraph, startVertex)
                    .filter { !subgraphSet.contains(it) }
                    .forEach { extensionSet.add(it) }
            //couldn't generate a subgraph of right size
        }

        return problem.function.eval(inducedSubgraph(problem.originalGraph, subgraphSet), problem.parameters)
    }
}