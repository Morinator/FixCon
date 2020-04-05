package de.umr.core;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
public final class Factory {

    private final static String lineDataFormat_NetworkRepo = "\\d+\\s+\\d+";
    private final static String separator_NetworkRepo = "\\s+";


    /**
     * @param edgeList String of the path of the file. For the most files,
     *                 you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static MutableGraph<Integer> graph_from_edgeList(final List<EndpointPair<Integer>> edgeList) {
        //throw_error_if_edgeList_invalid(edgeList);
        final MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        edgeList.forEach(x -> result_graph.putEdge(x.nodeU(), x.nodeV()));
        return result_graph;
    }

    public static List<EndpointPair<Integer>> edgeList_from_NetworkRepo(final String filePath) throws IOException {
        //captures only lines that consist of two positive integer numbers separated by whitespace (each line encodes an edge)
        return Files.lines(Paths.get(filePath))
                .filter(str -> str.matches(lineDataFormat_NetworkRepo))
                .map(str -> str.split(separator_NetworkRepo))
                .map(arr -> EndpointPair.unordered(Integer.valueOf(arr[0]), Integer.valueOf(arr[1])))
                .collect(toList());
    }
}