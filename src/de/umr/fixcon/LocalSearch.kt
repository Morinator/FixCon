package de.umr.fixcon

import de.umr.core.dataStructures.expandSubgraph
import de.umr.core.dataStructures.openNB
import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.BiconnectivityInspector

fun <V> localSearchOneStep(p: Problem<V>, solution: Solution<V>) {
    for (badVertex in nonCutPoints(solution.subgraph)) {

        solution.subgraph.removeVertex(badVertex)

        for (newVertex in p.g.openNB(solution.subgraph.vertexSet())) {

            solution.subgraph.expandSubgraph(p.g, newVertex)

            val newValue = p.function.eval(solution.subgraph)
            if (newValue > solution.value) {
                solution.value = newValue
                return
            } else {
                solution.subgraph.removeVertex(newVertex)
            }
        }
        solution.subgraph.expandSubgraph(p.g, badVertex)

    }

}


fun <V, E> nonCutPoints(g: Graph<V, E>) = g.vertexSet() - BiconnectivityInspector(g).cutpoints