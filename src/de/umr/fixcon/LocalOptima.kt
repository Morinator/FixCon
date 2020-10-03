package de.umr.fixcon

import de.umr.core.dataStructures.Partitioning
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.graph.DefaultEdge

fun <V> removeShittyVertices(g: Graph<V, DefaultEdge>, f: AbstractGraphFunction, p: Partitioning<V>, currBest: Int) {
    var counter = 0
    val queue = HashSet(g.vertexSet())

    while (queue.isNotEmpty()) {
        val v: V = queue.first()
        queue.remove(v)

        if (f.localOptimum(g, v) <= currBest) {
            queue.addAll(neighborListOf(g, v))
            g.removeVertex(v)
            p -= v
            counter++
        }
    }

    println("Local optima pruned: $counter")
}