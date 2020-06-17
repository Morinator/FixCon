package de.umr

import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, threshold: Int) {
    ConnectivityInspector(g).connectedSets().forEach {
        if (it.size < threshold)
            g.removeAllVertices(it)
    }
}