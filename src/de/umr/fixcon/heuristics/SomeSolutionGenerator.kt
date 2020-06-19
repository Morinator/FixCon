package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

fun <V> someSolution(problem: Problem<V>): Solution<V> {
    val subgraphSet = mutableSetOf(problem.g.vertexSet().random())
    repeat(problem.function.k - 1) { subgraphSet.add(problem.g.openNB(subgraphSet).random()) }
    val resultSubgraph = AsSubgraph(problem.g, subgraphSet)

    println("Heuristic: size: ${(subgraphSet.size).toString().padEnd(4)} value: ${problem.eval(resultSubgraph)} ")
    return Solution(resultSubgraph, problem.eval(resultSubgraph))
}