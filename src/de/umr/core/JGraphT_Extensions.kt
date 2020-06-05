package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge

val <V> Graph<V, DefaultEdge>.edgeCount get() = vertexSet().map { degreeOf(it) }.sum() / 2

val <V> Graph<V, DefaultEdge>.vertexCount: Int get() = vertexSet().size

/**@throws [IllegalStateException] if the requested edge is not present in the graph.
 * @return The weight of the edge between [vertexA] and [vertexB], if the edge exists.*/
fun <V> Graph<V, DefaultEdge>.weightOfEdge(vertexA: V, vertexB: V): Double {
    if (!containsEdge(vertexA, vertexB)) throw IllegalStateException("These vertices aren't connected by an edge")
    return getEdgeWeight(getEdge(vertexA, vertexB))
}

/**Adds an edge between [vertexA] and [vertexB] with weight [weight]
 *
 * @return The resulting [Graph] after the call of this method*/
fun <V> Graph<V, DefaultEdge>.addWeightedEdge(vertexA: V, vertexB: V, weight: Double): Graph<V, DefaultEdge> {
    addEdgeWithVertices(this, vertexA, vertexB)
    setEdgeWeight(vertexA, vertexB, weight)
    return this
}