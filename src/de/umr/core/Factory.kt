package de.umr.core

import de.umr.core.dataStructures.addWeightedEdge
import de.umr.defaultEdgeWeight
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.paukov.combinatorics3.Generator
import org.paukov.combinatorics3.Generator.combination

/**The vertices of the returned graphs are Integers. Generic vertices would need further info for constructors.
 * Each method has a Int-parameter which is the number of vertices in the returned graph.*/


fun <V> graphFromWeightedEdges(edges: List<Triple<V, V, Double>>): SimpleWeightedGraph<V, DefaultEdge> {
    require(edges.isNotEmpty())
    return SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java)
            .apply { for ((v1, v2, weight) in edges) addWeightedEdge(v1, v2, weight) }
}


fun <V> fromUnweightedEdges(edges: List<Pair<V, V>>) = graphFromWeightedEdges(edges.map { (v1, v2) -> Triple(v1, v2, defaultEdgeWeight) })


/** Creates the graph and adds the vertices in [vertices]*/
fun <V> fromVertices(vararg vertices: V) = SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java).apply { vertices.forEach { addVertex(it) } }


/**@return A clique of requested size (a graph, in which any two vertices are connected by an edge).*/
fun createClique(numVertices: Int) =
        fromUnweightedEdges(combination((0 until numVertices).toList()).simple(2).map { it[0] to it[1] }.toList())


/**@return The graph consists exclusively of one circle (a path where the first and last vertex are equal).*/
fun createCircle(numVertices: Int) =
        fromUnweightedEdges((0 until numVertices).map { Pair(it, ((it + 1) % numVertices)) })


/**@return The graph consists exclusively of one path. Thus it has exactly size-1 edges.*/
fun createPath(numVertices: Int) = fromUnweightedEdges((0 until numVertices - 1).map { Pair(it, (it + 1)) })


/**@return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
 * The graph therefore forms a star-like figure with vertex 0 in its center.*/
fun createStar(numVertices: Int) = fromUnweightedEdges((1 until numVertices).map { Pair(0, it) })


/**@return A bipartite graph, whose vertex partition has sizes [sizeLeft] and [sizeRight]. Any two vertices of from
 * different sides in the graph are connected, thus there are [sizeLeft] * [sizeRight] edges in total.*/
fun createBipartite(sizeLeft: Int, sizeRight: Int) =
        fromUnweightedEdges((0 until sizeLeft).flatMap { x -> (sizeLeft until sizeLeft + sizeRight).map { y -> Pair(x, y) } })
