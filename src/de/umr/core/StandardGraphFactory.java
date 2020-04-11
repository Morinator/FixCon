package de.umr.core;

import com.google.common.base.Preconditions;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StandardGraphFactory {
    /**
     * @param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    static MutableGraph<Integer> createClique(int size) {
        return GraphFileReader.graphByEdges(IntStream.range(0, size).boxed()     //[0, 1, ..., size-2, size-1]
                .flatMap(i -> IntStream.range(0, i).boxed()
                        .map(j -> EndpointPair.ordered(i, j)))
                .collect(Collectors.toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    static MutableGraph<Integer> createCircle(int size) {
        Preconditions.checkArgument(size > 1);
        return GraphFileReader.graphByEdges(IntStream.range(0, size).boxed()
                .map(i -> EndpointPair.ordered(i, (i + 1) % size))
                .collect(Collectors.toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    static MutableGraph<Integer> createPath(int size) {
        return GraphFileReader.graphByEdges(IntStream.range(0, size - 1).boxed()
                .map(i -> EndpointPair.ordered(i, i + 1))
                .collect(Collectors.toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    static MutableGraph<Integer> createStar(int size) {
        return GraphFileReader.graphByEdges(IntStream.range(1, size).boxed().map(i -> EndpointPair.ordered(0, i))
                .collect(Collectors.toList()));
    }
}