package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.core.random.randomElement
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

fun <V> getDenseSolution(problem: Problem<V>): Solution<V> {
    val v = mutableSetOf(randomElement(problem.g.vertexSet().associateWith { problem.g.degreeOf(it).toDouble() }))
    val extension = problem.g.openNB(v).toMutableSet()

    while (v.size < problem.targetSize && extension.isNotEmpty()) {
        val nextVertex: V = extension.maxBy { (problem.g.openNB(it) intersect v).size }!!
        v += nextVertex
        extension -= nextVertex
        extension.addAll(problem.g.openNB(nextVertex) - v)
    }

    val resultSubgraph = AsSubgraph(problem.g, v)

    println("size: ${v.size} \t value: ${problem.function.eval(resultSubgraph, problem.parameters)} ")

    return if (v.size < problem.targetSize) Solution()
    else Solution(resultSubgraph, problem.function.eval(resultSubgraph, problem.parameters))
}

fun <V> getBestSolution(problem: Problem<V>, runs: Int): Solution<V> =
        generateSequence { getDenseSolution(problem) }.take(runs).maxBy { it.value }!!