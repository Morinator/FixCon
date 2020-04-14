package de.umr.core;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;

import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static de.umr.core.GraphFileReader.graphByEdges;
import static java.util.stream.Collectors.toList;

public class StandardGraphFactory {
    /**
     * @param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    public static MutableGraph<Integer> createClique(int size) {
        return graphByEdges(IntStream.range(0, size).boxed()     //[0, 1, ..., size-2, size-1]
                .flatMap(i -> IntStream.range(0, i).boxed()
                        .map(j -> EndpointPair.ordered(i, j)))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    public static MutableGraph<Integer> createCircle(int size) {
        checkArgument(size > 1);
        return graphByEdges(IntStream.range(0, size).boxed()
                .map(i -> EndpointPair.ordered(i, (i + 1) % size))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    public static MutableGraph<Integer> createPath(int size) {
        return graphByEdges(IntStream.range(0, size - 1).boxed()
                .map(i -> EndpointPair.ordered(i, i + 1))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    public static MutableGraph<Integer> createStar(int size) {
        return graphByEdges(IntStream.range(1, size).boxed().map(i -> EndpointPair.ordered(0, i))
                .collect(toList()));
    }
}