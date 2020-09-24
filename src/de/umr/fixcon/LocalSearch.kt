package de.umr.fixcon

import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.intersectAll
import de.umr.core.dataStructures.neighbours
import org.jgrapht.alg.connectivity.ConnectivityInspector

fun <V> localSearchStep(p: Instance<V>, solution: Solution<V>) {
    for (badVertex: V in solution.subgraph.vertexSet().toList()) { //needs to copy bc of ConcurrentModifierException
        solution.subgraph.removeVertex(badVertex)

        for (newVertex: V in intersectAll(ConnectivityInspector(solution.subgraph).connectedSets().map { p.g.neighbours(it) })) {
            solution.subgraph.expandSubgraph(p.g, newVertex)

            val newValue = p.f.eval(solution.subgraph)
            if (newValue > solution.value) {
                solution.value = newValue
                return
            }
            solution.subgraph.removeVertex(newVertex)

        }
        solution.subgraph.expandSubgraph(p.g, badVertex)
    }
}

fun <V> localSearch(p: Instance<V>, solution: Solution<V>) {
    while (true) {
        val oldVal = solution.value
        localSearchStep(p, solution)
        if (oldVal == solution.value) return
    }
}