package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, threshold: Int) {
    ConnectivityInspector(g).connectedSets().forEach {
        if (it.size < threshold)
            g.removeAllVertices(it)
    }
}

fun <V> removeVerticesByPredicate(g: Graph<V, DefaultEdge>, predicate: (V) -> Boolean) {
    val badVertices: MutableSet<V> = g.vertexSet().filter { predicate(it) }.toMutableSet()
    while (badVertices.isNotEmpty()) {
        println("removed")
        val next = badVertices.random()
        val neighbours = g.openNB(next)

        g.removeVertex(next)
        badVertices.remove(next)

        badVertices.addAll(neighbours.filter { predicate(it) })
        badVertices.removeAll(neighbours.filter { !predicate(it) })
    }
}