package de.umr.fixcon.heuristic

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.expandSubgraph
import de.umr.core.extensions.openNB
import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.random.inv
import de.umr.core.random.takeRandom
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution

class Heuristic<V>(private val p: Problem<V>) {

    private val optimum = p.function.globalOptimum()
    private val vertexDegreeMap = p.verticesByDegree()
    private val runs = 400

    fun get(): Solution<V> {
        val currBest = Solution<V>()
        var ctr = 0

        val vIterDesc = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
        val vIterAsc = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

        while (ctr++ < runs && currBest.value < optimum) {

            fun solutionByStartAndExtension(p: Problem<V>,
                                            startVertex: V,
                                            extPicker: (extension: MutableMap<V, Int>) -> V): Solution<V> {

                val sub = VertexOrderedGraph.fromVertices(startVertex)

                /**Tracks the vertices the subgraph can be extended by, associated with the amount of edges they have to the current subgraph. */
                val extension: MutableMap<V, Int> = p.g.openNB(startVertex).associateWithTo(HashMap()) { 1 }

                while (sub.vertexCount < this.p.function.k) {

                    if (p.cantBeatOther(sub, currBest)) {
                        //println("HEURISTIC STOPPED")
                        return Solution()
                    }

                    val next: V = extPicker(extension)
                    (p.g.openNB(next) - sub.vertexSet()).forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
                    extension.remove(next)
                    sub.expandSubgraph(p.g, next)
                }
                return Solution(sub, p.eval(sub))
            }

            fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
                val heuristicSolution = solutionByStartAndExtension(p, start, f2)
                if (ctr > 0.7 * runs) fullLocalSearch(p, heuristicSolution)
                currBest.updateIfBetter(heuristicSolution)
            }

            if (vIterDesc.hasNext()) helper(vIterDesc.next(), { it.maxBy { entry -> entry.value }!!.key })              //Greedy Dense
            if (vIterAsc.hasNext()) helper(vIterAsc.next(), { it.minBy { entry -> entry.value }!!.key })                //Greedy Sparse
            helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                                     //Random Dense
            helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                                           //Random Sparse
            helper(vertexDegreeMap.keys.random(), { it.keys.random() })                                                 //Laplace
        }

        if (currBest.value == optimum) println("##############!!!!!!!!!OPTIMAL!!!!!!!!!##############")

        return currBest.also { println("Heuristic:".padEnd(pad) + it) }
    }
}