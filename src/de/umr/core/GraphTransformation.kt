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
    println("Clearing components:".padEnd(pad) + "$verticesRemoved vertices")
}

fun <V> pruneBigSubsets(partitioning: SetPartitioning<V>, problem: Problem<V>) {
    partitioning.subsets.toList().forEach {
        while (it.size > problem.f.k) {
            val badVertex: V = it.first()
            partitioning.removeElem(badVertex)
            problem.g.removeVertex(badVertex)
            println("CRITICAL")
        }
    }
}