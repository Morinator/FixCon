package de.umr.core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GraphFile_Readers {

    //captures only lines that consist of two positive integer numbers separated by spaces (each line encodes an edge)
    private static final String LINE_DATA_FORMAT = "\\d+\\s+\\d+";
    private static final String SEPARATOR = "\\s+";
    
    /**@param filePath String of the path of the file. For the most files,
     * you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    public static List<Integer[]> readFile_NetworkRepository_Format(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .filter(str -> str.matches(LINE_DATA_FORMAT))
                .map(str -> Arrays.stream(str.split(SEPARATOR)).map(Integer::parseInt).toArray(Integer[]::new))
                .collect(toList());
    }
}