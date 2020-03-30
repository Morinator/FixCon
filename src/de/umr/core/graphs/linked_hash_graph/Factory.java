package de.umr.core.graphs.linked_hash_graph;

import de.umr.core.graphs.UndirectedGraph;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This class provides only static factory methods for common forms of undirected graphs.
 * It uses the LinkedHashGraph implementation for the interface UndirectedGraph.
 */
final class Factory {

    /**@param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    static UndirectedGraph createClique(final int size) {
        return graphByStream(IntStream.range(0, size).boxed()     //[0, 1, ..., size-2, size-1]
                .flatMap(i -> IntStream.range(0, i).boxed().map(j -> new Integer[]{i, j})));
    }

    /**@param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    static UndirectedGraph createCircle(final int size) {
        return graphByStream(IntStream.range(0, size).boxed().map(i -> new Integer[]{i, (i+1)%size}));
    }

    /**@param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    static UndirectedGraph createPath(final int size) {
        return graphByStream(IntStream.range(0, size-1).boxed().map(i -> new Integer[]{i, i+1}));
    }

    /**@param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    static UndirectedGraph createStar(final int size) {
        return graphByStream(IntStream.range(1, size).boxed().map(i -> new Integer[]{0, i}));
    }


    private static UndirectedGraph graphByStream(Stream<Integer[]> stream) {
        return new LinkedHashGraph(stream.collect(toList()));
    }
}
