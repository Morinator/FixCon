package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.core.random.randomElement
import de.umr.core.random.randomElementInverted
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph

class SolutionGenerator<V>(private val problem: Problem<V>) {

    private val optimum = problem.function.globalOptimum()  //for caching the value I guess
    private val verticesByDegree = problem.g.vertexSet().associateWith { problem.g.degreeOf(it) }

    fun get(): Solution<V> {
        val sol = solutionByPickers(problem, { it.random() }, { randomElement(it) })
        var runs = 20

        while (runs-- > 0 && sol.value < optimum) {

            //Laplace
            sol.updateIfBetter(solutionByPickers(problem, { it.random() }, { it.keys.random() }))

            //Random Dense
            sol.updateIfBetter(solutionByPickers(problem, { randomElement(verticesByDegree) }, { randomElement(it) }))

            //Greedy Dense
            sol.updateIfBetter(solutionByPickers(problem, { randomElement(verticesByDegree) }, { it.maxBy { e -> e.value }!!.key }))

            //Random Sparse
            sol.updateIfBetter(solutionByPickers(problem, { randomElementInverted(verticesByDegree) }, { randomElementInverted(it) }))

            //Greedy Sparse
            sol.updateIfBetter(solutionByPickers(problem, { randomElementInverted(verticesByDegree) }, { it.minBy { e -> e.value }!!.key }))
        }

        if (sol.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return sol.also { println("Heuristic solution: $it") }
    }

    private fun solutionByPickers(problem: Problem<V>,
                                  startSelector: (vertices: Set<V>) -> V,
                                  extSelector: (extension: MutableMap<V, Int>) -> V)
            : Solution<V> {
        /**Tracks the set of vertices that induces the subgraph*/
        val sub: MutableSet<V> = mutableSetOf(startSelector(problem.g.vertexSet()))

        /**Tracks all the vertices the subgraph can be extended by. Each vertex is associated with the amount
         * of edges it has to the current subgraph. */
        val extension: MutableMap<V, Int> = problem.g.openNB(sub).associateWith { 1 }.toMutableMap()

        repeat(problem.function.k - 1) {
            val next: V = extSelector(extension)
            extension.remove(next)
            (problem.g.openNB(next) - sub).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            sub.add(next)
        }
        return AsSubgraph(problem.g, sub).let { Solution(problem.eval(it), it) }
    }
}