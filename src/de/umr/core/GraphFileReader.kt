package de.umr.core

import com.google.common.graph.EndpointPair
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph
import java.io.IOException
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */

/*An edge is represented by a line with two integers separated by whitespace*/
private val lineDataFormat_NetworkRepo = Regex("\\d+\\s+\\d+")
private val separator_NetworkRepo = Regex("\\s+")
/**
 * @param edgeList String of the path of the file. For the most files,
 * you can use ".//data.fileName" (at least in the default project-structure).
 * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
 */
fun graphByEdges(edgeList: List<EndpointPair<Int>>): MutableGraph<Int> {
    require(edgeList.isNotEmpty())
    val resultGraph = GraphBuilder.undirected().build<Int>()
    edgeList.forEach { x -> resultGraph.putEdge(x.nodeU(), x.nodeV()) }
    return resultGraph
}

@Throws(IOException::class)
fun edgesFromNetworkRepo(filePath: String): List<EndpointPair<Int>> {
    return Files.lines(Paths.get(filePath)).toList()
            .filter { str -> str.matches(lineDataFormat_NetworkRepo) }
            .map { str-> str.split(separator_NetworkRepo).toTypedArray() }
            .map { arr -> EndpointPair.unordered(parseInt(arr[0]), parseInt(arr[1])) }
}

/**
 * Reading in graphs in NetworkRepositories format is so common that this function combines the needed methods.
 */
@Throws(IOException::class)
fun graphFromNetworkRepo(filePath: String): MutableGraph<Int> {
    return graphByEdges(edgesFromNetworkRepo(filePath))
}
