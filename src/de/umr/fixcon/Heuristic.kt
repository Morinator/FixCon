package de.umr.fixcon

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.useHeuristic
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import kotlin.math.log2

fun <V> getHeuristic(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction): Solution<V> {
    if (!useHeuristic) return Solution()

    val heuristicRuns: Int = (1 + log2(g.vertexCount.toDouble()) * f.k).toInt()
    val currentBestSolution = Solution<V>()

    fun singleRun(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction, startVertex: V, extensionPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)

        /**Tracks the vertices the subgraph can be extended by, associated with the number of edges they have to the current subgraph. */
        val extension: MutableMap<V, Int> = neighborListOf(g, startVertex).associateWithTo(HashMap()) { 1 } //TODO hässlich??
        while (subgraph.vertexCount < f.k) {
            if (vertexAdditionRule(subgraph, currentBestSolution, f)) return Solution()

            val nextVertex: V = extensionPicker(extension)

            (neighborListOf(g, nextVertex).apply { removeAll(subgraph.vertexSet()) })
                    .forEach { extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(nextVertex)
            subgraph.expandSubgraph(g, nextVertex)
        }
        return Solution(subgraph, f.eval(subgraph))
    }

    fun helper(start: V, f2: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(g, f, start, f2)
        localSearch(g, f, heuristicSolution)
        currentBestSolution.updateIfBetter(heuristicSolution.subgraph, heuristicSolution.value)
    }

    val vertexDegreeMap: Map<V, Int> = g.vertexSet().associateWith { g.degreeOf(it) }
    val vertexIteratorDegDesc: Iterator<V> = g.vertexSet().sortedByDescending { g.degreeOf(it) }.iterator()
    val vIteratorDegAsc: Iterator<V> = g.vertexSet().sortedBy { g.degreeOf(it) }.iterator()

    var runCounter = 0
    while (runCounter++ < heuristicRuns && currentBestSolution.value < f.globalOptimum()) {
        if (vertexIteratorDegDesc.hasNext()) helper(vertexIteratorDegDesc.next(), { it.maxByOrNull { entry -> entry.value }!!.key })    //Greedy Dense
        if (vIteratorDegAsc.hasNext()) helper(vIteratorDegAsc.next(), { it.minByOrNull { entry -> entry.value }!!.key })                //Greedy Sparse
        helper(takeRandom(vertexDegreeMap), { takeRandom(it) })                                                                         //Random Dense
        helper(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                                                               //Random Sparse
        helper(vertexDegreeMap.keys.random(), { map -> map.keys.random() })                                                             //Laplace
    }
    return currentBestSolution
}

fun <V> localSearchStep(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction, solution: Solution<V>) {
    for (badVertex: V in solution.subgraph.vertexSet().toList()) { //needs to copy bc of ConcurrentModifierException
        solution.subgraph.removeVertex(badVertex)

        for (newVertex: V in intersectAll(ConnectivityInspector(solution.subgraph).connectedSets().map { g.neighbours(it) })) {
            solution.subgraph.expandSubgraph(g, newVertex)

            val newValue = f.eval(solution.subgraph)
            if (newValue > solution.value) {
                solution.value = newValue
                return
            }
            solution.subgraph.removeVertex(newVertex)

        }
        solution.subgraph.expandSubgraph(g, badVertex)
    }
}

fun <V> localSearch(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction, solution: Solution<V>) {
    while (true) {
        val oldVal = solution.value
        localSearchStep(g, f, solution)
        if (oldVal == solution.value) return
    }
}