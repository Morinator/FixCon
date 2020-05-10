package de.umr.core


import de.umr.core.GraphFileReader.graphByEdges
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import java.util.stream.Collectors.toList
import java.util.stream.IntStream
import kotlin.streams.toList

object StandardGraphFactory {

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    fun createClique(size: Int): Graph<Int, DefaultEdge> =
            graphByEdges(IntStream.range(0, size).boxed() //[0, 1, ..., size-2, size-1]
                    .flatMap { IntStream.range(0, it).boxed().map { x -> it to x } }
                    .collect(toList()))

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    fun createCircle(size: Int): Graph<Int, DefaultEdge> {
        require(size > 1)
        return graphByEdges(IntStream.range(0, size).toList()
                .map { it to (it + 1) % size })
    }

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    fun createPath(size: Int): Graph<Int, DefaultEdge> =
            graphByEdges(IntStream.range(0, size - 1).toList()
            .map { it to it + 1 })

    /**
     * @param size The number of vertices in the resulting [Graph].
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    fun createStar(size: Int): Graph<Int, DefaultEdge> =
            graphByEdges(IntStream.range(1, size).toList()
            .map { 0 to it })
}