package de.umr.core

import com.google.common.graph.EndpointPair
import com.google.common.graph.MutableGraph
import java.util.stream.Collectors.toList
import java.util.stream.IntStream
import kotlin.streams.toList


/**
     * @param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    fun createClique(size: Int): MutableGraph<Int> {
        return graphByEdges(IntStream.range(0, size).boxed() //[0, 1, ..., size-2, size-1]
                .flatMap { i-> IntStream.range(0, i).boxed().map { j -> EndpointPair.ordered(i, j) }}
                .collect(toList()))
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    fun createCircle(size: Int): MutableGraph<Int> {
        require(size > 1)
        return graphByEdges(IntStream.range(0, size).toList()
                .map { i: Int -> EndpointPair.ordered(i, (i + 1) % size) })
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    fun createPath(size: Int): MutableGraph<Int> {
        return graphByEdges(IntStream.range(0, size - 1).toList()
                .map { i: Int -> EndpointPair.ordered(i, i + 1) })
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    fun createStar(size: Int): MutableGraph<Int> {
        return graphByEdges(IntStream.range(1, size).toList()
                .map { i: Int -> EndpointPair.ordered(0, i) })
    }
