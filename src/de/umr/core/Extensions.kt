package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

val <V> Graph<V, DefaultEdge>.edgeCount get() = degreeList.sum() / 2

val <V> Graph<V, DefaultEdge>.vertexCount: Int get() = vertexSet().size

/**@return Set of all neighbours of [vertex], but this set does NOT contain [vertex] itself.*/
fun <V> Graph<V, DefaultEdge>.openNB(vertex: V): Set<V> = neighborSetOf(this, vertex)

/**@return Set of all neighbours of [vertex], INCLUDING vertex itself.*/
fun <V> Graph<V, DefaultEdge>.closedNB(vertex: V): Set<V> = openNB(vertex) union setOf(vertex)

/**@return *True* iff the object changed as a result of the call <=> the edge wasn't already present.*/
fun <V> Graph<V, DefaultEdge>.addEdgeWithVertices(v1: V, v2: V) = (!containsEdge(v1, v2))
        .also { if (it) addEdgeWithVertices(this, v1, v2) }

/**@throws [IllegalStateException] if the requested edge is not present in the graph.
 * @return The weight of the edge between [v1] and [v2], if the edge exists.*/
fun <V> Graph<V, DefaultEdge>.weightOfEdge(v1: V, v2: V): Double {
    if (!containsEdge(v1, v2)) throw IllegalStateException("These vertices aren't connected by an edge")
    return getEdgeWeight(getEdge(v1, v2))
}

/**Adds an edge between [v1] and [v2] with weight [weight]
 *
 * @return The resulting [Graph] after the call of this method*/
fun <V> Graph<V, DefaultEdge>.addWeightedEdge(v1: V, v2: V, weight: Double): Graph<V, DefaultEdge> =
        this.also { addEdgeWithVertices(this, v1, v2);setEdgeWeight(v1, v2, weight) }

/**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
 * It's only guaranteed to work with primitives as vertices, because if the method was totally generic it could not be
 * guaranteed that the vertices are cloneable.
 *
 * @return A new Integer-valued graph, which is a copy of [this] graph.*/
fun <V> Graph<V, DefaultEdge>.getCopy() = VertexOrderedGraph.fromEdges(edgeSet()
        .map { Triple(getEdgeSource(it), getEdgeTarget(it), weightOfEdge(getEdgeSource(it), getEdgeTarget(it))) }
)

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 *
 * @param V The type of the vertices.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
val <V> Graph<V, DefaultEdge>.hasTriangle get() = edgeSet().any { (openNB(getEdgeSource(it)) intersect openNB(getEdgeTarget(it))).isNotEmpty() }

val <V> Graph<V, DefaultEdge>.degreeList get() = vertexSet().map { degreeOf(it) }