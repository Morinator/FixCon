package de.umr.core

import de.umr.core.dataStructures.Partitioning
import de.umr.core.dataStructures.unorderedPairs
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

/**Removes all maximally connected components of [g], which have fewer than [lowerBound] vertices.*/
fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, lowerBound: Int) {
    ConnectivityInspector(g).connectedSets().filter { it.size < lowerBound }.forEach { g.removeAllVertices(it) }
}

/**
 * [partitioning] must contain subsets of the vertices in [g].
 * This method removes as many vertices in the graph from each subset, until the size of
 * the subset is not greater than the value of [k].
 */
fun <V> prunePartsGreaterK(g: Graph<V, DefaultEdge>, k: Int, partitioning: Partitioning<V>) {
    partitioning.subsets.toList().forEach {
        while (it.size > k) {
            val badVertex: V = it.first()
            partitioning -= badVertex
            g.removeVertex(badVertex)
        }
    }
}


/**Adds an edge from each vertex of [vCol1] to each vertex of [vCol2].
 */
fun <V> connectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol1) for (v2 in vCol2) g.addEdge(v1, v2)
}

/**Adds all vertices from [newCliqueVertices] and creates an edge between all pairs of two vertices from [newCliqueVertices].
 */
fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Set<V>) {
    Graphs.addAllVertices(g, newCliqueVertices)
    unorderedPairs(newCliqueVertices).forEach { g.addEdge(it.first, it.second) }
}