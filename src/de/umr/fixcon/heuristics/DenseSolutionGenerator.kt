package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.core.random.randomElement
import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph
import kotlin.math.log2
import kotlin.math.roundToInt

fun <V> getDenseSolution(problem: Problem<V>): Solution<V> {
    val v = mutableSetOf(randomElement(problem.g.vertexSet().associateWith { problem.g.degreeOf(it).toDouble() }))
    val extension = problem.g.openNB(v).toMutableSet()

    while (v.size < problem.k && extension.isNotEmpty()) {
        val nextVertex: V = extension.maxBy { (problem.g.openNB(it) intersect v).size }!!
        v += nextVertex
        extension -= nextVertex
        extension.addAll(problem.g.openNB(nextVertex) - v)
    }

    val resultSubgraph = AsSubgraph(problem.g, v)

    println("size: ${v.size} \t value: ${problem.eval(resultSubgraph)} ")

    return if (v.size < problem.k) Solution()
    else Solution(resultSubgraph, problem.eval(resultSubgraph))
}

fun <V> getBestSolution(problem: Problem<V>, runs: Int = (log2(problem.g.vertexCount.toDouble()) * problem.k * 0.1).roundToInt()): Solution<V> {

    val bestSolution = Solution<V>()
    println("runs $runs")
    repeat(runs) {
        bestSolution.updateIfBetter(getDenseSolution(problem))
        if (bestSolution.value == problem.globalOptimum)
            return bestSolution
    }
    return bestSolution
}