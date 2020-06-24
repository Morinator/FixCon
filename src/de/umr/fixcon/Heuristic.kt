package de.umr.fixcon

import de.umr.core.dataStructures.copy
import de.umr.core.dataStructures.openNB
import de.umr.core.random.inv
import de.umr.core.random.random
import org.jgrapht.graph.AsSubgraph

class Heuristic<V>(private val problem: Problem<V>) {

    private val optimum = problem.function.globalOptimum()
    private val verticesByDegree = problem.verticesByDegree()

    fun get(): Solution<V> {
        val sol = Solution<V>()
        val runs = 30
        var ctr = 0

        while (ctr++ < runs && sol.value < optimum) {
            fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
                val heuristicSolution = solutionByStartAndExtension(problem, start, f2)

                //localSearch takes long, so it is tried without it in the first half
                if (ctr > runs / 2) fullLocalSearch(problem, heuristicSolution)

                sol.updateIfBetter(heuristicSolution)
            }
            helper(verticesByDegree.keys.random(), { it.keys.random() })                        //Laplace
            helper(random(verticesByDegree), { random(it) })                                    //Random Dense
            helper(random(verticesByDegree), { m -> m.maxBy { entry -> entry.value }!!.key })   //Greedy Dense
            helper(random(verticesByDegree, inv), { x -> random(x, inv) })                      //Random Sparse
            helper(random(verticesByDegree, inv), { m -> m.minBy { it.value }!!.key })          //Greedy Sparse
        }

        if (sol.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return sol.also { println("Heuristic solution: $it") }
    }

    private fun solutionByStartAndExtension(p: Problem<V>, startVertex: V, extPicker: (extension: MutableMap<V, Int>) -> V): Solution<V> {

        val sub: MutableSet<V> = mutableSetOf(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWith { 1 }.toMutableMap()

        while (sub.size < problem.function.k) {
            val next: V = extPicker(extension)
            (p.g.openNB(next) - sub).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            sub.add(next)
        }
        return AsSubgraph(p.g, sub).copy().let { Solution(it, p.eval(it)) }
    }
}