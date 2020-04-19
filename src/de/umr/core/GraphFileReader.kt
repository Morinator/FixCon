package de.umr.core;


import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
public final class GraphFileReader {

    /*An edge is represented by a line with two integers separated by whitespace*/
    private final static String lineDataFormat_NetworkRepo = "\\d+\\s+\\d+", separator_NetworkRepo = "\\s+";

    /**
     * @param edgeList String of the path of the file. For the most files,
     *                 you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static MutableGraph<Integer> graphByEdges(final List<EndpointPair<Integer>> edgeList) {
        checkArgument(!edgeList.isEmpty(), "Cannot create empty graph from file.");
        final MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        edgeList.forEach(x -> result_graph.putEdge(x.nodeU(), x.nodeV()));
        return result_graph;
    }

    public static List<EndpointPair<Integer>> edgesFromNetworkRepo(final String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .filter(str -> str.matches(lineDataFormat_NetworkRepo))
                .map(str -> str.split(separator_NetworkRepo))
                .map(arr -> EndpointPair.unordered(valueOf(arr[0]), valueOf(arr[1])))
                .collect(toList());
    }

    /**
     * Reading in graphs in NetworkRepositories format is so common that this function combines the needed methods.
     */
    public static MutableGraph<Integer> graphFromNetworkRepo(final String filePath) throws IOException {
        return graphByEdges(edgesFromNetworkRepo(filePath));
    }
}