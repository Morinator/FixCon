package de.umr.core

import de.umr.core.dataStructures.Partitioning
import de.umr.paddingRight
import org.jgrapht.Graph
import org.jgrapht.Graphs.addAllVertices
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.paukov.combinatorics3.Generator.combination

/**Removes all maximally connected components of [g], which have fewer than [threshold] vertices.*/
fun <V> removeComponentsSmallerThreshold(g: Graph<V, DefaultEdge>, threshold: Int) {
    ConnectivityInspector(g).connectedSets().filter { it.size < threshold }.forEach { g.removeAllVertices(it) }
}

fun <V> trimTwinSets(g: Graph<V, DefaultEdge>, p: Partitioning<V>, openTwins: Set<V>, k: Int) {
    var counter = 0
    for (sub in p.subsets) {
        val reps = (sub.size - k) + if (sub.first() in openTwins) 1 else 0
        repeat(reps) {
            val v = sub.first()
            sub.remove(v)
            g.removeVertex(v)
            counter++
        }
    }
    println("twin-vertices pruned:".padEnd(paddingRight) + counter)
}

/**Adds an edge from each vertex of [vCol1] to each vertex of [vCol2].*/
fun <V> connectVertices(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1) for (v2 in vCol2) g.addEdge(v1, v2)
}

/**Removes the edge from each vertex of [vCol1] to each vertex of [vCol2] in case it exists.*/
fun <V> disconnectVertices(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1) for (v2 in vCol2) g.removeEdge(v1, v2)
}

/**Adds all vertices from [newCliqueVertices] and creates an edge between all pairs of two vertices from [newCliqueVertices].*/
fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Collection<V>) {
    addAllVertices(g, newCliqueVertices)
    combination(newCliqueVertices).simple(2).map { it[0] to it[1] }.forEach { g.addEdge(it.first, it.second) }
}