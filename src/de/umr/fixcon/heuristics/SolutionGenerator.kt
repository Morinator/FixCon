package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

class SolutionGenerator<V>(private val problem: Problem<V>) {

    fun get(): Solution<V> {
        val sol = randomSolution(problem)
        return sol.also { println("Heuristic solution: $it") }
    }

    private fun randomSolution(problem: Problem<V>): Solution<V> {
        val subgraphSet = mutableSetOf(problem.g.vertexSet().random())
        repeat(problem.function.k - 1) { subgraphSet.add(problem.g.openNB(subgraphSet).random()) }
        val resultSubgraph = AsSubgraph(problem.g, subgraphSet)

        return Solution(problem.eval(resultSubgraph), resultSubgraph)
    }
}