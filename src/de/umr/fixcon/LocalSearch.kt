package de.umr.fixcon

import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.intersectAll
import de.umr.core.dataStructures.neighbours
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

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