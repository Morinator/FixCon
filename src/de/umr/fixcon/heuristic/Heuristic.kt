package de.umr.fixcon.heuristic

import de.umr.core.*
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import de.umr.fixcon.localSearchChance
import de.umr.fixcon.useHeuristic
import org.jgrapht.Graphs.neighborListOf
import kotlin.math.log2
import kotlin.random.Random

fun <V> getHeuristic(p: Problem<V>): Solution<V> {
    val heuristicRuns: Int = (1 + log2(p.g.vertexCount.toDouble()) * p.f.k).toInt()
    val currSol = Solution<V>()

    fun singleRun(p: Problem<V>, startVertex: V, extPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = neighborListOf(p.g, startVertex).associateWithTo(HashMap()) { 1 }
        while (subgraph.vertexCount < p.f.k) {

            if (p.cantBeatOther(subgraph, currSol)) return Solution()

            val next: V = extPicker(extension)

            (neighborListOf(p.g, next).apply { removeAll(subgraph.vertexSet()) })
                    .forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            subgraph.expandSubgraph(p.g, next)
        }
        return Solution(subgraph, p.eval(subgraph))
    }

    fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(p, start, f2)
        if (Random.nextDouble() < localSearchChance) localSearch(p, heuristicSolution)
        currSol.updateIfBetter(heuristicSolution)
    }

    val vertexDegreeMap = p.g.vertexSet().associateWith { p.g.degreeOf(it) }
    val vIteratorDesc = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
    val vIteratorAsc = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

    var runCounter = 0
    while (useHeuristic && runCounter++ < heuristicRuns && currSol.value < p.f.globalOptimum()) {

        if (vIteratorDesc.hasNext()) helper(vIteratorDesc.next(), { it.maxBy { entry -> entry.value }!!.key })  //Greedy Dense
        if (vIteratorAsc.hasNext()) helper(vIteratorAsc.next(), { it.minBy { entry -> entry.value }!!.key })    //Greedy Sparse
        helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                                 //Random Dense
        helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                                       //Random Sparse
        helper(vertexDegreeMap.keys.random(), { it.keys.random() })                                             //Laplace
    }

    return currSol
}

