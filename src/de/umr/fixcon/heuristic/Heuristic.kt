package de.umr.fixcon.heuristic

import de.umr.core.*
import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.Instance
import de.umr.fixcon.Solution
import de.umr.useHeuristic
import org.jgrapht.Graphs.neighborListOf
import kotlin.math.log2

fun <V> getHeuristic(p: Instance<V>): Solution<V> {
    if (!useHeuristic) return Solution()

    val heuristicRuns: Int = (1 + log2(p.g.vertexCount.toDouble()) * p.f.k).toInt()
    val currentBestSolution = Solution<V>()

    fun singleRun(p: Instance<V>, startVertex: V, extensionPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the number of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = neighborListOf(p.g, startVertex).associateWithTo(HashMap()) { 1 }
        while (subgraph.vertexCount < p.f.k) {

            if (p.vertexAdditionRule(subgraph, currentBestSolution)) return Solution()

            val nextVertex: V = extensionPicker(extension)

            (neighborListOf(p.g, nextVertex).apply { removeAll(subgraph.vertexSet()) })
                    .forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(nextVertex)
            subgraph.expandSubgraph(p.g, nextVertex)
        }
        return Solution(subgraph, p.eval(subgraph))
    }

    fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(p, start, f2)
        localSearch(p, heuristicSolution)
        currentBestSolution.updateIfBetter(heuristicSolution)
    }

    val vertexDegreeMap: Map<V, Int> = p.g.vertexSet().associateWith { p.g.degreeOf(it) }
    val vertexIteratorDegDesc: Iterator<V> = p.g.vertexSet().sortedByDescending { p.g.degreeOf(it) }.iterator()
    val vIteratorDegAsc: Iterator<V> = p.g.vertexSet().sortedBy { p.g.degreeOf(it) }.iterator()

    var runCounter = 0
    while (runCounter++ < heuristicRuns && currentBestSolution.value < p.f.globalOptimum()) {
        if (vertexIteratorDegDesc.hasNext()) helper(vertexIteratorDegDesc.next(), { it.maxByOrNull { entry -> entry.value }!!.key })    //Greedy Dense
        if (vIteratorDegAsc.hasNext()) helper(vIteratorDegAsc.next(), { it.minByOrNull { entry -> entry.value }!!.key })                //Greedy Sparse
        helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                                                         //Random Dense
        helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                                                               //Random Sparse
        helper(vertexDegreeMap.keys.random(), { map -> map.keys.random() })                                                             //Laplace
    }
    return currentBestSolution
}