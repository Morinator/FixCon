package de.umr.fixcon

import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.intersectAll
import de.umr.core.dataStructures.openNB
import org.jgrapht.alg.connectivity.ConnectivityInspector

fun <V> localSearchOneStep(p: Problem<V>, solution: Solution<V>) {
    for (badVertex in HashSet(solution.subgraph.vertexSet())) { //needs to copy bc of ConcurrentModifierException
        solution.subgraph.removeVertex(badVertex)

        val allowed =ConnectivityInspector(solution.subgraph).connectedSets().map { p.g.openNB(it) }.intersectAll()

        for (newVertex in allowed) {
            solution.subgraph.expandSubgraph(p.g, newVertex)

            val newValue = p.function.eval(solution.subgraph)
            if (newValue > solution.value) {
                solution.value = newValue
                println("singleLocalSearch successful")
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