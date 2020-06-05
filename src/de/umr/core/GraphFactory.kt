package de.umr.core


import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph

/**The vertices of the returned graphs are Int-IDs. Generic vertices would need further info for constructors.
 * Each method has a Int-parameter which is the number of vertices in the returned graph.*/

/**All edges in the returning graphs have the default weight 1.0*/
private const val weight: Double = 1.0

/**@param size The number of vertices in the resulting [Graph].
 * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).*/
fun createClique(size: Int) = VertexOrderedGraph((0 until size).flatMap { x -> (0 until x).map { y -> Triple(x, y, weight) } })

/**@param size The number of vertices in the resulting [Graph].
 * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).*/
fun createCircle(size: Int) = VertexOrderedGraph((0 until size).map { Triple(it, (it + 1) % size, weight) })

/**@param size The number of vertices in the resulting [Graph].
 * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.*/
fun createPath(size: Int) = VertexOrderedGraph((0 until size - 1).map { Triple(it, it + 1, weight) })

/**@param size The number of vertices in the resulting [Graph].
 * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
 * The graph therefore forms a star-like figure with vertex 0 in its center.*/
fun createStar(size: Int) = VertexOrderedGraph((1 until size).map { Triple(0, it, weight) })
