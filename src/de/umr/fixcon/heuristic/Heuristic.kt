package de.umr.fixcon.heuristic

import de.umr.core.dataStructures.inv
import de.umr.core.dataStructures.randBoolean
import de.umr.core.dataStructures.takeRandom
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.openNB
import de.umr.core.extensions.vertexCount
import de.umr.core.fromVertices
import de.umr.core.pad
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution


fun <V> getHeuristic(p: Problem<V>): Solution<V> {
    val heuristicRuns = 10
    val currSol = Solution<V>()

    fun singleRun(p: Problem<V>, startVertex: V, extPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWithTo(HashMap()) { 1 }
        while (subgraph.vertexCount < p.f.k) {

            if (p.cantBeatOther(subgraph, currSol)) return Solution()

            val next: V = extPicker(extension)

            (p.g.openNB(next) - subgraph.vertexSet()).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            subgraph.expandSubgraph(p.g, next)
        }
        return Solution(subgraph, p.eval(subgraph))
    }

    fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(p, start, f2)
        if (randBoolean(0.333)) localSearch(p, heuristicSolution)
        currSol.updateIfBetter(heuristicSolution)
    }

    val vertexDegreeMap = p.g.vertexSet().associateWith { p.g.degreeOf(it) }
    val vIterDesc = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
    val vIterAsc = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

    var runCounter = 0
    while (runCounter++ < heuristicRuns && currSol.value < p.f.globalOptimum()) {

        if (vIterDesc.hasNext()) helper(vIterDesc.next(), { it.maxBy { entry -> entry.value }!!.key })  //Greedy Dense
        if (vIterAsc.hasNext()) helper(vIterAsc.next(), { it.minBy { entry -> entry.value }!!.key })    //Greedy Sparse
        helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                         //Random Dense
        helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                               //Random Sparse
        helper(vertexDegreeMap.keys.random(), { it.keys.random() })                                     //Laplace
    }

    if (currSol.value == p.f.globalOptimum()) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")
    return currSol.also { println("Heuristic solution:".padEnd(pad) + it) }
}

