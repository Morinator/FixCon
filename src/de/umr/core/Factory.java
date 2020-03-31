package de.umr.core;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.stream.IntStream;

/**
 * This class provides only static factory methods for common forms of undirected graphs.
 * It uses the LinkedHashGraph implementation for the interface UndirectedGraph.
 */
final class Factory {

    /**@param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    static MutableGraph<Integer> createClique(final int size) {
        MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        IntStream.range(0, size).boxed()
                .forEach(i -> IntStream.range(0, i).boxed().forEach(j -> result_graph.putEdge(i, j)));
        return result_graph;
    }

    /**@param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    static MutableGraph<Integer> createCircle(final int size) {
        MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        IntStream.range(0, size).boxed().forEach(i -> {if (size > 1) result_graph.putEdge(i, (i + 1) % size);});
        return result_graph;
    }

    /**@param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    static MutableGraph<Integer> createPath(final int size) {
        MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        IntStream.range(0, size-1).boxed().forEach(i -> result_graph.putEdge(i, i+1));
        return result_graph;
    }

    /**@param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    static MutableGraph<Integer> createStar(final int size) {
        MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        IntStream.range(1, size).boxed().forEach(i -> result_graph.putEdge(0, i));
        return result_graph;
    }
}