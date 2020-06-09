package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph as graph

/**The vertices of the returned graphs are Int-IDs. Generic vertices would need further info for constructors.
 * Each method has a Int-parameter which is the number of vertices in the returned graph.*/

/**All edges in the returning graphs have the default weight 1.0*/
private const val defaultWeight: Double = 1.0

/**@return A clique of requested size (a graph, in which any two vertices are connected by an edge).*/
fun createClique(numVertices: Int) = graph((0 until numVertices).flatMap { x -> (0 until x).map { y -> Triple(x, y, defaultWeight) } })

/**@return The graph consists exclusively of one circle (a path where the first and last vertex are equal).*/
fun createCircle(numVertices: Int) = graph((0 until numVertices).map { Triple(it, (it + 1) % numVertices, defaultWeight) })

/**@return The graph consists exclusively of one path. Thus it has exactly size-1 edges.*/
fun createPath(numVertices: Int) = graph((0 until numVertices - 1).map { Triple(it, it + 1, defaultWeight) })

/**@return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
 * The graph therefore forms a star-like figure with vertex 0 in its center.*/
fun createStar(numVertices: Int) = graph((1 until numVertices).map { Triple(0, it, defaultWeight) })
