package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

fun <V> someSolution(problem: Problem<V>): Solution<V> {
    val vertices = mutableSetOf(problem.g.vertexSet().random())
    repeat(problem.k - 1) { vertices.add(problem.g.openNB(vertices).random()) }
    val resultSubgraph = AsSubgraph(problem.g, vertices)

    println("size: ${vertices.size} \t value: ${problem.eval(resultSubgraph)} ")
    return Solution(resultSubgraph, problem.eval(resultSubgraph))
}