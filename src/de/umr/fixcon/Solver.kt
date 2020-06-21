package de.umr.fixcon

import de.umr.core.removeSmallComponents
import de.umr.core.vertexCount
import de.umr.fixcon.heuristics.SolutionGenerator
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution

fun <V> solve(problem: Problem<V>): Solution<V> {
    removeSmallComponents(problem.g, problem.function.k)
    val sol = SolutionGenerator(problem).get()

    var subIterators = 0
    while (sol.value < problem.globalOptimum && problem.g.vertexCount >= problem.function.k) {
        val iter = SubIterator(problem, problem.g.vertexSet().first(), sol)
        while (iter.isValid)
            iter.mutate()
        problem.g.removeVertex(iter.startVertex)
        subIterators++
    }
    return sol.also { println("Iterators used: $subIterators") }
}
