package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge


//##################################-----Properties-----##################################

val <V> Graph<V, DefaultEdge>.degreeSequence get() = vertexSet().map { degreeOf(it) }.asSequence()

val <V> Graph<V, DefaultEdge>.edgeCount get() = degreeSequence.sum() / 2

val <V> Graph<V, DefaultEdge>.vertexCount: Int get() = vertexSet().size

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 *
 * @param V The type of the vertices.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
val <V> Graph<V, DefaultEdge>.hasTriangle get() = edgeSet().any { (openNB(getEdgeSource(it)) intersect openNB(getEdgeTarget(it))).isNotEmpty() }


//##################################-----Neighbour-related functions-----##################################

private fun <V> Graph<V, DefaultEdge>.allNeighbours(vararg vertices: V) = HashSet<V>().apply {
    for (v in vertices) addAll(neighborSetOf(this@allNeighbours, v))
}

/**@return Set of all neighbours of [vertices], but this set does NOT contain [vertices] itself.*/
fun <V> Graph<V, DefaultEdge>.openNB(vararg vertices: V): Set<V> = allNeighbours(*vertices) - vertices

/**@return Set of all neighbours of [vertices] and [vertices] itself too.*/
fun <V> Graph<V, DefaultEdge>.closedNB(vararg vertices: V) = allNeighbours(*vertices) + vertices


//##################################-----Graph-Manipulation-----##################################

/**@return *True* iff the object changed as a result of the call <=> the edge wasn't already present.*/
fun <V> Graph<V, DefaultEdge>.addEdgeWithVertices(v1: V, v2: V) = (!containsEdge(v1, v2))
        .also { if (it) addEdgeWithVertices(this, v1, v2) }

/**@return The weight of the edge between [v1] and [v2], if the edge exists.*/
fun <V> Graph<V, DefaultEdge>.weightOfEdge(v1: V, v2: V) = getEdgeWeight(getEdge(v1, v2))

/**Adds an edge between [v1] and [v2] with weight [weight]
 *
 * @return The resulting [Graph] after the call of this method*/
fun <V> Graph<V, DefaultEdge>.addWeightedEdge(v1: V, v2: V, weight: Double): Graph<V, DefaultEdge> =
        also { addEdgeWithVertices(this, v1, v2);setEdgeWeight(v1, v2, weight) }

/**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
 * It's only guaranteed to work with primitives as vertices, because if the method was totally generic it could not be
 * guaranteed that the vertices are cloneable.
 *
 * @return A new Integer-valued graph, which is a copy of [this] graph.*/
fun <V> Graph<V, DefaultEdge>.getCopy() = VertexOrderedGraph.fromEdges(edgeSet()
        .map { Triple(getEdgeSource(it), getEdgeTarget(it), weightOfEdge(getEdgeSource(it), getEdgeTarget(it))) }
)