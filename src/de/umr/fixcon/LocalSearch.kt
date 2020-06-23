package de.umr.fixcon

import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.multiIntersect
import de.umr.core.dataStructures.openNB
import org.jgrapht.alg.connectivity.ConnectivityInspector

fun <V> localSearchOneStep(p: Problem<V>, solution: Solution<V>) {
    for (badVertex in HashSet(solution.subgraph.vertexSet())) { //needs to copy bc of ConcurrentModifierException

        solution.subgraph.removeVertex(badVertex)

        val components = ConnectivityInspector(solution.subgraph).connectedSets()
        val allowed = multiIntersect(components.map { p.g.openNB(it) })

        for (newVertex in allowed) {

            solution.subgraph.expandSubgraph(p.g, newVertex)

            val newValue = p.function.eval(solution.subgraph)
            if (newValue > solution.value) {
                solution.value = newValue
                println("localSearch successful")
                return
            }
            solution.subgraph.removeVertex(newVertex)

        }
        solution.subgraph.expandSubgraph(p.g, badVertex)
    }
}

fun <V> fullLocalSearch(p: Problem<V>, solution: Solution<V>) {
    while (true) {
        val oldVal = solution.value
        localSearchOneStep(p, solution)
        if (oldVal == solution.value) return
    }
}