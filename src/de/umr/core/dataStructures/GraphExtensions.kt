package de.umr.core.dataStructures

import de.umr.core.graphFromWeightedEdges
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge

val <V> Graph<V, DefaultEdge>.vCount: Int get() = vertexSet().size

val <V> Graph<V, DefaultEdge>.edgeCount get() = edgeSet().size

val <V> Graph<V, DefaultEdge>.degrees get() = vertexSet().map { degreeOf(it) }

fun <V> Graph<V, DefaultEdge>.neighbours(vertices: Collection<V>): Set<V> = vertices.flatMapTo(HashSet(), { neighborSetOf(this, it) }).apply { removeAll(vertices) }

fun <V> Graph<V, DefaultEdge>.closedNB(v: V): Set<V> = neighborSetOf(this, v).apply { add(v) }

fun <V> Graph<V, DefaultEdge>.openNBEqualsFast(v1: V, v2: V) =
        degreeOf(v1) == degreeOf(v2) && !containsEdge(v1, v2) && neighborSetOf(this, v1) == neighborSetOf(this, v2)

fun <V> Graph<V, DefaultEdge>.closedNBEqualsFast(v1: V, v2: V) =
        degreeOf(v1) == degreeOf(v2) && containsEdge(v1, v2) && closedNB(v1) == closedNB(v2)

/**Adds an edge between [v1] and [v2] with weight [weight]
 * @return The resulting [Graph] after the call of this method*/
fun <V> Graph<V, DefaultEdge>.addWeightedEdge(v1: V, v2: V, weight: Double): Graph<V, DefaultEdge> =
        also { addEdgeWithVertices(this, v1, v2);setEdgeWeight(v1, v2, weight) }

/**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
 * It's only guaranteed to work with primitives as vertices, because if the method was totally generic it could not be
 * guaranteed that the vertices are cloneable.
 * @return A new Integer-valued graph, which is a copy of [this] graph.*/
fun <V> Graph<V, DefaultEdge>.copy() = graphFromWeightedEdges(edgeSet().map { getEdgeSource(it) to getEdgeTarget(it) }
        .map { (s, t) -> Triple(s, t, getEdgeWeight(getEdge(s, t))) })


/**Assumes that [this] is logically a subgraph of [original] <=> the vertices and edges of [this] are subsets of the
 * vertices and edge of [original].
 * This method adds [newVertex] to [this] and also adds every edge that exists in [original] between
 * the previous vertices of [this] and [newVertex].*/
fun <V> Graph<V, DefaultEdge>.expandSubgraph(original: Graph<V, DefaultEdge>, newVertex: V) {
    intersectAll(listOf(neighborSetOf(original, newVertex), vertexSet())).forEach { addEdgeWithVertices(this, newVertex, it) }
}

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
fun <V> Graph<V, DefaultEdge>.hasTriangle() =
        edgeSet().any { (neighborSetOf(this, getEdgeSource(it)) intersect neighborSetOf(this, getEdgeTarget(it))).isNotEmpty() }