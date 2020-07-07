package de.umr.fixcon.heuristic

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.openNB
import de.umr.core.extensions.randBoolean
import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.random.inv
import de.umr.core.random.takeRandom
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution

class Heuristic<V>(private val p: Problem<V>) {

    private var currSol = Solution<V>()

    fun get(): Solution<V> {
        val vertexDegreeMap = p.g.vertexSet().associateWith { p.g.degreeOf(it) }
        val vIterDesc = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
        val vIterAsc = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

        var runCounter = 0
        while (runCounter++ < 200 && currSol.value < p.f.globalOptimum()) {

            if (vIterDesc.hasNext()) helper(vIterDesc.next(), { it.maxBy { entry -> entry.value }!!.key })  //Greedy Dense
            if (vIterAsc.hasNext()) helper(vIterAsc.next(), { it.minBy { entry -> entry.value }!!.key })    //Greedy Sparse
            helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                         //Random Dense
            helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                               //Random Sparse
            helper(vertexDegreeMap.keys.random(), { it.keys.random() })                                     //Laplace
        }

        if (currSol.value == p.f.globalOptimum()) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")
        return currSol.also { println("Heuristic:".padEnd(pad) + it) }
    }

    private fun singleSolution(p: Problem<V>, startVertex: V, extPicker: (extension: MutableMap<V, Int>) -> V): Solution<V> {

        val sub = VertexOrderedGraph.fromVertices(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWithTo(HashMap()) { 1 }
        while (sub.vertexCount < this.p.f.k) {

            if (p.cantBeatOther(sub, currSol)) {
                println("HEURISTIC STOPPED")
                return Solution()
            }

            val next: V = extPicker(extension)

            (p.g.openNB(next) - sub.vertexSet()).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(next)
            sub.expandSubgraph(p.g, next)
        }
        return Solution(sub, p.eval(sub))
    }

    private fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
        val localSearchChance = 0.333
        val heuristicSolution = singleSolution(p, start, f2)
        if (randBoolean(localSearchChance)) localSearch(p, heuristicSolution)
        currSol.updateIfBetter(heuristicSolution)
    }

}