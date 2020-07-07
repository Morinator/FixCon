package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

/**
 * Removes all maximally connected components of [g], which have fewer than [minVertices] vertices.
 */
fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, minVertices: Int) {
    var verticesRemoved = 0
    ConnectivityInspector(g).connectedSets().filter { it.size < minVertices }.forEach {
        g.removeAllVertices(it)
        verticesRemoved += it.size
    }
    println("Clearing components:".padEnd(pad) + "$verticesRemoved vertices")
}