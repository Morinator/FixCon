package de.umr.core

import de.umr.core.dataStructures.openNB
import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, threshold: Int) {
    var verticesRemoved = 0
    ConnectivityInspector(g).connectedSets().filter { it.size < threshold }.forEach {
        g.removeAllVertices(it)
        verticesRemoved += it.size
    }
    println("Clearing components:".padEnd(pad) + "$verticesRemoved vertices")
}

fun <V> removeVerticesByPredicate(g: Graph<V, DefaultEdge>, predicate: (V) -> Boolean) {
    val badVertices: MutableSet<V> = g.vertexSet().filter(predicate).toMutableSet()

    while (badVertices.isNotEmpty()) {
        val next = badVertices.first().also { badVertices.remove(it) }
        val neighbours = g.openNB(next)
        g.removeVertex(next)
        neighbours.forEach { if (predicate(it)) badVertices.add(it) else badVertices.remove(it) }
    }
}