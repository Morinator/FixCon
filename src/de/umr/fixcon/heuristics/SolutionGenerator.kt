package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

class SolutionGenerator<V>(private val problem: Problem<V>) {

    private val optimum = problem.function.globalOptimum()  //for caching the value I guess

    fun get(): Solution<V> {
        val sol = randomSolution(problem)
        var runs = 1000

        while(runs-- > 0 && sol.value < optimum) {
            sol.updateIfBetter(randomSolution(problem))
        }

        return sol.also { println("Heuristic solution: $it") }
    }

    private fun randomSolution(problem: Problem<V>): Solution<V> {
        val sub: MutableSet<V> = mutableSetOf(problem.g.vertexSet().random())
        val extension: MutableSet<V> = problem.g.openNB(sub).toMutableSet()

        repeat(problem.function.k - 1) {
            val next: V = extension.random()
            extension.remove(next)
            extension.addAll(problem.g.openNB(next) - sub)
            sub.add(next)
        }

        return AsSubgraph(problem.g, sub).let { Solution(problem.eval(it), it) }
    }
}