package de.umr.fixcon.heuristics

import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.util.MathUtil.log2
import kotlin.math.max

object LowerBoundGenerator {

    fun getBound(problem: CFCO_Problem, runs: Int = log2(problem.originalGraph.size)): Int {
        require(problem.originalGraph.size >= problem.targetSize) { "Target-size may not be smaller than graph" }
        var bestLowerBound = Int.MIN_VALUE
        repeat(runs) { bestLowerBound = max(bestLowerBound, singleRun(problem)) }
        return bestLowerBound
    }

    private fun singleRun(problem: CFCO_Problem): Int {
        val startVertex = problem.originalGraph.vertexSet().random()
        val subgraphSet = HashSet<Int>(setOf(startVertex))
        val extensionSet = HashSet<Int>(neighborSetOf(problem.originalGraph, startVertex))

        repeat(problem.targetSize - 1) {
            if (extensionSet.isNotEmpty()) return Int.MIN_VALUE    //couldn't generate a subgraph of right size
            val nextVertex = extensionSet.random()
            subgraphSet += nextVertex
            extensionSet -= nextVertex
            neighborSetOf(problem.originalGraph, startVertex)
                    .filter { !subgraphSet.contains(it) }
                    .forEach { extensionSet.add(it) }
        }

        return 123456
    }
}