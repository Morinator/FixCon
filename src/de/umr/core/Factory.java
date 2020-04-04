package de.umr.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
public class Factory {

    /**
     * @param edgeList String of the path of the file. For the most files,
     *                 you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static MutableGraph<Integer> graph_from_NetworkRepo(final List<Integer[]> edgeList) {
        final MutableGraph<Integer> result_graph = GraphBuilder.undirected().build();
        edgeList.forEach(x -> result_graph.putEdge(x[0], x[1]));
        return result_graph;
    }

    public static List<Integer[]> edges_from_path(final String filePath) {
        //captures only lines that consist of two positive integer numbers separated by whitespace (each line encodes an edge)
        final String LINE_DATA_FORMAT = "\\d+\\s+\\d+";
        final String SEPARATOR = "\\s+";
        try {
            return Files.lines(Paths.get(filePath))
                    .filter(str -> str.matches(LINE_DATA_FORMAT))
                    .map(str -> Arrays.stream(str.split(SEPARATOR)).map(Integer::parseInt).toArray(Integer[]::new))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static BiMap<String, Integer> vertex_to_ID(final List<String[]> edgeList) {
        final BiMap<String, Integer> biMap = HashBiMap.create(edgeList.size());   //estimates capacity
        for (String[] edge : edgeList) {
            for (int i = 0; i <= 1; i++)
                biMap.putIfAbsent(edge[i], biMap.size());
        }
        return biMap;
    }
}