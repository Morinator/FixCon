package de.umr.fixcon

import de.umr.core.dataStructures.copy
import de.umr.core.dataStructures.openNB
import de.umr.core.pad
import de.umr.core.random.inv
import de.umr.core.random.takeRandom
import org.jgrapht.graph.AsSubgraph

class Heuristic<V>(private val p: Problem<V>) {

    private val optimum = p.function.globalOptimum()
    private val vertexDegreeMap = p.verticesByDegree()

    fun get(): Solution<V> {
        val sol = Solution<V>()
        val runs = 100
        var ctr = 0

        val vIterDesc = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
        val vIterAsc = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

        while (ctr++ < runs && sol.value < optimum) {

            fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
                val heuristicSolution = solutionByStartAndExtension(p, start, f2)
                if (ctr > 0.7 * runs) fullLocalSearch(p, heuristicSolution)
                sol.updateIfBetter(heuristicSolution)
            }

            if (vIterDesc.hasNext()) helper(vIterDesc.next(), { it.maxBy { entry -> entry.value }!!.key })              //Greedy Dense
            if (vIterAsc.hasNext()) helper(vIterAsc.next(), { it.minBy { entry -> entry.value }!!.key })                //Greedy Sparse
            helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                                     //Random Dense
            helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                                           //Random Sparse
            helper(vertexDegreeMap.keys.random(), { it.keys.random() })                                                 //Laplace
        }

        if (sol.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return sol.also { println("Heuristic:".padEnd(pad) + it) }
    }

    private fun solutionByStartAndExtension(p: Problem<V>, startVertex: V, extPicker: (extension: MutableMap<V, Int>) -> V): Solution<V> {

        val sub: MutableSet<V> = mutableSetOf(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWithTo(HashMap()) { 1 }

        while (sub.size < this.p.function.k) {
            val next: V = extPicker(extension)
            (p.g.openNB(next) - sub).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            sub.add(next)
        }
        return AsSubgraph(p.g, sub).copy().let { Solution(it, p.eval(it)) }
    }
}