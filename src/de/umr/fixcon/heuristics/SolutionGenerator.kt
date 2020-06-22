package de.umr.fixcon.heuristics

import de.umr.core.getCopy
import de.umr.core.openNB
import de.umr.core.random.randomElement
import de.umr.core.random.reciprocal
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.graph.AsSubgraph

class SolutionGenerator<V>(private val problem: Problem<V>) {

    private val optimum = problem.function.globalOptimum()  //for caching the value I guess
    private val verticesByDegree = problem.g.vertexSet().associateWith { problem.g.degreeOf(it) }

    fun get(): Solution<V> {
        val sol = solutionByPickers(problem, { it.random() }, { randomElement(it) })
        var runs = 20

        while (runs-- > 0 && sol.value < optimum) {
            fun heuristicByPickers(f1: (vertices: Set<V>) -> V, f2: (extension: MutableMap<V, Int>) -> V) {
                sol.updateIfBetter(solutionByPickers(problem, f1, f2))
            }

            //Laplace
            heuristicByPickers({ it.random() }, { it.keys.random() })

            //Random Dense
            heuristicByPickers({ randomElement(verticesByDegree) }, { randomElement(it) })

            //Greedy Dense
            heuristicByPickers({ randomElement(verticesByDegree) }, { it.maxBy { entry -> entry.value }!!.key })

            //Random Sparse
            heuristicByPickers({ randomElement(verticesByDegree, reciprocal) }, { randomElement(it, reciprocal) })

            //Greedy Sparse
            heuristicByPickers({ randomElement(verticesByDegree, reciprocal) }, { it.minBy { entry -> entry.value }!!.key })
        }

        if (sol.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return sol.also { println("Heuristic solution: $it") }
    }

    private fun solutionByPickers(problem: Problem<V>,
                                  startSelector: (vertices: Set<V>) -> V,
                                  extSelector: (extension: MutableMap<V, Int>) -> V)
            : Solution<V> {

        val startVertex = startSelector(problem.g.vertexSet())
        val sub: MutableSet<V> = mutableSetOf(startVertex)

        /**Tracks all the vertices the subgraph can be extended by. Each vertex is associated with the amount
         * of edges it has to the current subgraph. */
        val extension: MutableMap<V, Int> = problem.g.openNB(startVertex).associateWith { 1 }.toMutableMap()

        repeat(problem.function.k - 1) {
            val next: V = extSelector(extension)
            (problem.g.openNB(next) - sub).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            sub.add(next)
        }
        return AsSubgraph(problem.g, sub).getCopy().let { Solution(problem.eval(it), it) }
    }
}