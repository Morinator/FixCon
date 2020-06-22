package de.umr.fixcon

import de.umr.core.getCopy
import de.umr.core.openNB
import de.umr.core.random.inv
import de.umr.core.random.random
import org.jgrapht.graph.AsSubgraph

class Heuristic<V>(private val problem: Problem<V>) {

    private val optimum = problem.function.globalOptimum()  //for caching the value I guess
    private val verticesByDegree = problem.verticesByDegree()

    fun get(): Solution<V> {
        val sol = solutionByPickers(problem, { it.random() }, { random(it) })
        var runs = 20

        while (runs-- > 0 && sol.value < optimum) {
            fun helper(f1: (vertices: Set<V>) -> V, f2: (extension: MutableMap<V, Int>) -> V) {
                sol.updateIfBetter(solutionByPickers(problem, f1, f2))
            }

            helper({ it.random() }, { it.keys.random() })   //Laplace

            helper({ random(verticesByDegree) }, { random(it) })  //RandomDense

            helper({ random(verticesByDegree) }, { m-> m.maxBy { entry -> entry.value }!!.key }) //GreedyDense

            helper({ random(verticesByDegree, inv) }, { random(it, inv) })  //RandomSparse

            helper({ random(verticesByDegree, inv) }, { m -> m.minBy { it.value }!!.key })  //GreedySparse
        }

        if (sol.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return sol.also { println("Heuristic solution: $it") }
    }

    private fun solutionByPickers(p: Problem<V>, start: (vertices: Set<V>) -> V, ext: (extension: MutableMap<V, Int>) -> V): Solution<V> {

        val startVertex = start(p.g.vertexSet())
        val sub: MutableSet<V> = mutableSetOf(startVertex)

        /**Tracks all the vertices the subgraph can be extended by. Each vertex is associated with the amount
         * of edges it has to the current subgraph. */
        val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWith { 1 }.toMutableMap()

        repeat(p.function.k - 1) {
            val next: V = ext(extension)
            (p.g.openNB(next) - sub).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            sub.add(next)
        }
        return AsSubgraph(p.g, sub).getCopy().let { Solution(p.eval(it), it) }
    }
}