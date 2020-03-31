package de.umr.core;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
public class FileReader {

    /**
     * @param filePath String of the path of the file. For the most files,
     *                 you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static MutableGraph<Integer> graph_from_NetworkRepo(final String filePath) {

        //captures only lines that consist of two positive integer numbers separated by spaces (each line encodes an edge)
        final String LINE_DATA_FORMAT = "\\d+\\s+\\d+";
        final String SEPARATOR = "\\s+";

        final MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        try {
            Files.lines(Paths.get(filePath))
                    .filter(str -> str.matches(LINE_DATA_FORMAT))
                    .map(str -> Arrays.stream(str.split(SEPARATOR)).map(Integer::parseInt).toArray(Integer[]::new))
                    .forEach(x -> result_graph.putEdge(x[0], x[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result_graph;
    }

}