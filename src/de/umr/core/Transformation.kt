package de.umr.core

import de.umr.core.dataStructures.unorderedPairs
import org.jgrapht.Graph
import org.jgrapht.Graphs.addAllVertices
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

/**Removes all maximally connected components of [g], which have fewer than [lowerBound] vertices.*/
fun <V> removeComponentsSmallerThreshold(g: Graph<V, DefaultEdge>, lowerBound: Int) {
    ConnectivityInspector(g).connectedSets().filter { it.size < lowerBound }.forEach { g.removeAllVertices(it) }
}

/**Adds an edge from each vertex of [vCol1] to each vertex of [vCol2].
 */
fun <V> connectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1) for (v2 in vCol2) g.addEdge(v1, v2)
}

/**Removes the edge from each vertex of [vCol1] to each vertex of [vCol2] in case it exists.
 */
fun <V> disconnectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1) for (v2 in vCol2) g.removeEdge(v1, v2)
}

/**Adds all vertices from [newCliqueVertices] and creates an edge between all pairs of two vertices from [newCliqueVertices].
 */
fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Collection<V>) {
    addAllVertices(g, newCliqueVertices)
    unorderedPairs(newCliqueVertices).forEach { g.addEdge(it.first, it.second) }
}