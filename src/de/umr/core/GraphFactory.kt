package de.umr.core


import de.umr.core.GraphFileReader.defaultWeightedGraphByEdges
import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph

/**
 * This object class provides factory methods for 4 common types of graphs: {clique, circle, path, star}.
 * Each method has a Int-parameter which is the number of vertices in the returned graph.
 */
object GraphFactory {

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    fun createClique(size: Int): VertexOrderedGraph<Int> =
            defaultWeightedGraphByEdges((0 until size).flatMap { (0 until it).map { x -> Pair(it, x) } })

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    fun createCircle(size: Int): VertexOrderedGraph<Int> {
        require(size > 1)
        return defaultWeightedGraphByEdges((0 until size).map { Pair(it, (it + 1) % size) })
    }

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    fun createPath(size: Int): VertexOrderedGraph<Int> = defaultWeightedGraphByEdges((0 until size - 1).map { it to it + 1 })

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    fun createStar(size: Int): VertexOrderedGraph<Int> = defaultWeightedGraphByEdges((1 until size).map { Pair(0, it) })
}