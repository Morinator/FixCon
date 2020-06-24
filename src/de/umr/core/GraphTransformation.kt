package de.umr.core

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