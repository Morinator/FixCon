package de.umr.fixcon

import de.umr.core.dataStructures.*
import de.umr.core.fromVertices
import de.umr.core.inv
import de.umr.core.takeRandom
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import kotlin.math.log2

fun <V> getHeuristic(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction): Solution<V> {
    val runs: Int = (5 + log2(g.vertexCount.toDouble()) * f.k).toInt().also { println("Number of heuristic runs: $it") }
    val sol = Solution<V>()

    fun singleRun(startVertex: V, extensionPicker: (MutableMap<V, Int>) -> V): Solution<V> {
        val subgraph = fromVertices(startVertex)
        val extension: MutableMap<V, Int> = neighborListOf(g, startVertex).associateWithTo(HashMap()) { 1 } //TODO hässlich??
        while (subgraph.vertexCount < f.k) {
            if (vertexAdditionRule(subgraph, sol, f)) return Solution()
            val nextVertex: V = extensionPicker(extension)
            neighborListOf(g, nextVertex).forEach { if (it !in subgraph.vertexSet()) extension[it] = extension.getOrDefault(it, 0) + 1 }
            extension.remove(nextVertex)
            subgraph.expandSubgraph(g, nextVertex)
        }
        return Solution(subgraph, f.eval(subgraph))
    }


    val vertexDegreeMap: Map<V, Int> = g.vertexSet().associateWith { g.degreeOf(it) }

    fun fullRun(start: V, vertexPicker: (extension: MutableMap<V, Int>) -> V) {
        val heuristicSolution = singleRun(start, vertexPicker)
        while (true) {
            val oldVal = heuristicSolution.value
            localSearchStep(g, f, heuristicSolution)
            if (oldVal == heuristicSolution.value) break
        }
        sol.updateIfBetter(heuristicSolution.subgraph, heuristicSolution.value)
    }

    repeat(runs) {
        if (sol.value >= f.globalOptimum()) return@repeat
        else {
            fullRun(takeRandom(vertexDegreeMap), { takeRandom(it) })                        //Random Dense
            fullRun(takeRandom(vertexDegreeMap, inv), { takeRandom(it, inv) })              //Random Sparse
            fullRun(vertexDegreeMap.keys.random(), { map -> map.keys.random() })            //Laplace
        }
    }
    return sol
}

fun <V> localSearchStep(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction, solution: Solution<V>) {
    for (badVertex: V in solution.subgraph.vertexSet().toList()) { //needs to copy bc of ConcurrentModifierException
        solution.subgraph.removeVertex(badVertex)
        for (newVertex: V in intersectAll(ConnectivityInspector(solution.subgraph).connectedSets().map { g.neighbours(it) })) {
            solution.subgraph.expandSubgraph(g, newVertex)
            if (f.eval(solution.subgraph) > solution.value) {
                solution.value = f.eval(solution.subgraph)
                return
            }
            solution.subgraph.removeVertex(newVertex)
        }
        solution.subgraph.expandSubgraph(g, badVertex)
    }
}