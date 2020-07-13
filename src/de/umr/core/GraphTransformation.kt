package de.umr.core

import de.umr.core.dataStructures.SetPartitioning
import de.umr.fixcon.Problem
import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge

/**Removes all maximally connected components of [g], which have fewer than [minVertices] vertices.*/
fun <V> removeSmallComponents(g: Graph<V, DefaultEdge>, minVertices: Int) {
    var verticesRemoved = 0
    ConnectivityInspector(g).connectedSets().filter { it.size < minVertices }.forEach {
        g.removeAllVertices(it)
        verticesRemoved += it.size
    }
    println("Clearing small components:".padEnd(pad) + "$verticesRemoved vertices")
}

/**
 * [partitioning] must contain subsets of the graph in [problem].
 * This method removes as many vertices in the graph from each subset, until the size of
 * the subset is not greater than the value of *k* in [problem].
 */
fun <V> pruneBigSubsets(partitioning: SetPartitioning<V>, problem: Problem<V>) {
    partitioning.subsets.toList().forEach {
        while (it.size > problem.f.k) {
            val badVertex: V = it.first()
            partitioning -= badVertex
            problem.g.removeVertex(badVertex)
            println("Vertex removed from critical part")
        }
    }
}