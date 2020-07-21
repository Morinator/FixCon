package de.umr.core.extensions

import de.umr.core.dataStructures.intersectAll
import de.umr.core.fromWeightedEdges
import org.jgrapht.Graph
import org.jgrapht.Graphs
import org.jgrapht.Graphs.*
import org.jgrapht.graph.DefaultEdge


//##################################-----Properties-----##################################

val <V> Graph<V, DefaultEdge>.vertexCount: Int get() = vertexSet().size

val <V> Graph<V, DefaultEdge>.edgeCount get() = edgeSet().size

val <V> Graph<V, DefaultEdge>.degreeSequence get() = vertexSet().map { degreeOf(it) }.asSequence()


//##################################-----Neighbour-related functions-----##################################

/** @return A new [HashSet] that contains the union of the neighbourhood for every vertex from [vertices].*/
private fun <V> Graph<V, DefaultEdge>.allNeighbours(vertices: Collection<V>): MutableSet<V> =
        vertices.flatMapTo(HashSet(), { neighborSetOf(this, it) })

fun <V> Graph<V, DefaultEdge>.neighbours(vertices: Collection<V>): Set<V> = when (vertices.size) {
    0 -> HashSet()
    1 -> neighborSetOf(this, vertices.first())
    else -> allNeighbours(vertices).apply { removeAll(vertices) }
}

fun <V> Graph<V, DefaultEdge>.closedNB(vertices: Collection<V>): Set<V> = when (vertices.size) {
    0 -> HashSet()
    1 -> neighborSetOf(this, vertices.first()).apply { add(vertices.first()) }
    else -> allNeighbours(vertices).apply { addAll(vertices) }
}

fun <V> Graph<V, DefaultEdge>.closedNB(v: V): Set<V> = neighborSetOf(this, v).apply { add(v) }

fun <V> Graph<V, DefaultEdge>.openNBEqualsFast(v1: V, v2: V) =
        degreeOf(v1) == degreeOf(v2) && !containsEdge(v1, v2) && neighborSetOf(this, v1) == neighborSetOf(this, v2)

fun <V> Graph<V, DefaultEdge>.closedNBEqualsFast(v1: V, v2: V) =
        degreeOf(v1) == degreeOf(v2) && containsEdge(v1, v2) && closedNB(v1) == closedNB(v2)


//##################################-----Graph-Manipulation-----##################################

/**@return *True* iff the object changed as a result of the call <=> the edge wasn't already present.*/
fun <V> Graph<V, DefaultEdge>.addEdgeWithVertices(v1: V, v2: V) = (!containsEdge(v1, v2))
        .also { if (it) addEdgeWithVertices(this, v1, v2) }

/**Adds an edge between [v1] and [v2] with weight [weight]
 * @return The resulting [Graph] after the call of this method*/
fun <V> Graph<V, DefaultEdge>.addWeightedEdge(v1: V, v2: V, weight: Double): Graph<V, DefaultEdge> =
        also { addEdgeWithVertices(this, v1, v2);setEdgeWeight(v1, v2, weight) }

fun <V> Graph<V, DefaultEdge>.toggleEdge(v1: V, v2: V) {
    if (containsEdge(v1, v2)) removeEdge(v1, v2) else addEdge(v1, v2)
}

/**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
 * It's only guaranteed to work with primitives as vertices, because if the method was totally generic it could not be
 * guaranteed that the vertices are cloneable.
 *
 * @return A new Integer-valued graph, which is a copy of [this] graph.*/
fun <V> Graph<V, DefaultEdge>.copy() = fromWeightedEdges(
        edgeSet().map {
            val s = getEdgeSource(it)
            val t = getEdgeTarget(it)
            Triple(s, t, getEdgeWeight(getEdge(s, t)))
        })

/**Assumes that [this] is logically a subgraph of [original] <=> the vertices and edges of [this] are subsets of the
 * vertices and edge of [original].
 * This method adds [newVertex] to [this] and also adds every edge that exists in [original] between
 * the previous vertices of [this] and [newVertex].
 */
fun <V> Graph<V, DefaultEdge>.expandSubgraph(original: Graph<V, DefaultEdge>, newVertex: V) {
    require(newVertex in original.vertexSet())
    intersectAll(listOf(neighborSetOf(original, newVertex), vertexSet())).forEach{addEdgeWithVertices(newVertex,it)}
}


//##################################-----Trivia-----##################################

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 *
 * @param V The type of the vertices.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
fun <V> Graph<V, DefaultEdge>.hasTriangle() =
        edgeSet().any { (neighborSetOf(this, getEdgeSource(it)) intersect neighborSetOf(this, getEdgeTarget(it))).isNotEmpty() }