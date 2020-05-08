package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.IOException
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList


/**
 * This class exclusively contains static methods for reading in graph-files in different formats.
 */
object GraphFileReader {

    /*An edge is represented by a line with two integers separated by whitespace*/
    private val lineDataFormat_NetworkRepo = Regex("""\d+\s+\d+""")
    private val separator_NetworkRepo = Regex("""\s+""")

    /**
     * @param edgeList String of the path of the file. For the most files,
     * you can use ".//data.fileName" (at least in the default project-structure).
     * @return Each edge is an Integer-Array of size 2. A collection of these edges specifies the whole graph.
     */
    fun graphByEdges(edgeList: List<Pair<Int, Int>>): Graph<Int, DefaultEdge> {
        require(edgeList.isNotEmpty())
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        edgeList.forEach { addEdgeWithVertices(resultGraph, it.first, it.second) }
        return resultGraph
    }

    @Throws(IOException::class)
    fun edgesFromNetworkRepo(filePath: String): List<Pair<Int, Int>> {
        return Files.lines(Paths.get(filePath)).toList()
                .filter { it.matches(lineDataFormat_NetworkRepo) }
                .map { it.split(separator_NetworkRepo) }
                .map { Pair(parseInt(it[0]), parseInt(it[1])) }
    }

    /**
     * Reading in graphs in NetworkRepositories format is so common that this function combines the needed methods.
     */
    @Throws(IOException::class)
    fun graphFromNetworkRepo(filePath: String): Graph<Int, DefaultEdge> {
        return graphByEdges(edgesFromNetworkRepo(filePath))
    }
}
