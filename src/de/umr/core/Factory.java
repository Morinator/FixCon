package de.umr.core;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
public final class Factory {

    /*An edge is represented by a line with two integers separated by whitespace*/
    private final static String lineDataFormat_NetworkRepo = "\\d+\\s+\\d+";
    private final static String separator_NetworkRepo = "\\s+";

    /**
     * @param edgeList String of the path of the file. For the most files,
     *                 you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static MutableGraph<Integer> graphByEdges(final List<EndpointPair<Integer>> edgeList) {
        final MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        edgeList.forEach(x -> result_graph.putEdge(x.nodeU(), x.nodeV()));
        return result_graph;
    }

    public static List<EndpointPair<Integer>> edgesFromNetworkRepo(final String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .filter(str -> str.matches(lineDataFormat_NetworkRepo))
                .map(str -> str.split(separator_NetworkRepo))
                .map(arr -> EndpointPair.unordered(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])))
                .collect(toList());
    }

    /**
     * Reading in graphs in NetworkRepositories format is so common that this function combines the needed methods.
     */
    public static MutableGraph<Integer> graphFromNetworkRepo(final String filePath) throws IOException {
        return graphByEdges(edgesFromNetworkRepo(filePath));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return A clique of requested size (a graph, in which any two vertices are connected by an edge).
     */
    static MutableGraph<Integer> createClique(int size) {
        return graphByEdges(IntStream.range(0, size).boxed()     //[0, 1, ..., size-2, size-1]
                .flatMap(i -> IntStream.range(0, i).boxed()
                        .map(j -> EndpointPair.ordered(i, j)))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one circle (a path where the first and last vertex are equal).
     */
    static MutableGraph<Integer> createCircle(int size) {
        if (size <= 1) throw new IllegalArgumentException();
        return graphByEdges(IntStream.range(0, size).boxed()
                .map(i -> EndpointPair.ordered(i, (i + 1) % size))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return The graph consists exclusively of one path. Thus it has exactly size-1 edges.
     */
    static MutableGraph<Integer> createPath(int size) {
        return graphByEdges(IntStream.range(0, size - 1).boxed()
                .map(i -> EndpointPair.ordered(i, i + 1))
                .collect(toList()));
    }

    /**
     * @param size The number of vertices in the resulting graph.
     * @return In the graph the vertex with ID 0 is connected to any other vertex, but no other edges exist.
     * The graph therefore forms a star-like figure with vertex 0 in the center.
     */
    static MutableGraph<Integer> createStar(int size) {
        return graphByEdges(IntStream.range(1, size).boxed().map(i -> EndpointPair.ordered(0, i))
                .collect(toList()));
    }
}