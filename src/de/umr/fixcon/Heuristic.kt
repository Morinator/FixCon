package de.umr.fixcon

import de.umr.core.dataStructures.*
import de.umr.core.fromVertices
import de.umr.core.inv
import de.umr.core.takeRandom
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

    fun singleRun(startVertex: V, extensionPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)
        val extension: MutableMap<V, Int> = neighborListOf(g, startVertex).associateWithTo(HashMap()) { 1 } //TODO hässlich??

        while (subgraph.vertexCount < f.k) {
            if (vertexAdditionRule(subgraph, currentBestSolution, f)) return Solution()

            val nextVertex: V = extensionPicker(extension)
            neighborListOf(g, nextVertex).forEach { if (it !in subgraph.vertexSet()) extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(nextVertex)
            subgraph.expandSubgraph(g, nextVertex)
        }
        return Solution(subgraph, f.eval(subgraph))
    }


    val vertexDegreeMap: Map<V, Int> = g.vertexSet().associateWith { g.degreeOf(it) }

    fun fullHeuristic(start: V, vertexPicker: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(start, vertexPicker)
        while (true) {
            val oldVal = heuristicSolution.value
            localSearchStep(g, f, heuristicSolution)
            if (oldVal == heuristicSolution.value) break
        }
        currentBestSolution.updateIfBetter(heuristicSolution.subgraph, heuristicSolution.value)
    }

    var runCounter = 0
    while (runCounter++ < heuristicRuns && currentBestSolution.value < f.globalOptimum()) {
        fullHeuristic(takeRandom(vertexDegreeMap), { takeRandom(it) })                                 //Random Dense
        fullHeuristic(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })                       //Random Sparse
        fullHeuristic(vertexDegreeMap.keys.random(), { map -> map.keys.random() })                     //Laplace
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