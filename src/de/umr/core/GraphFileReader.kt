package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
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

    /*An unweighted edge is represented by a line with two integers separated by whitespace*/
    private val unweightedEdgeFormat_NetworkRepo = Regex("""\d+\s+\d+""")

    /*A weighted edge is represented by a line with three integers separated by whitespace */
    private val weightedEdgeFormat_NetworkRepo = Regex("""\d+\s+\d+\s+\d+""")

    private val separator_NetworkRepo = Regex("""\s+""")

    /**
     *@param edgeList A list of Int-Pairs which specifies the wanted graph.
     * @return An undirected simple graph built with the edge received from [edgeList]
     */
    fun simpleGraphByEdges(edgeList: List<Pair<Int, Int>>): VertexOrderedGraph<Int> {
        require(edgeList.isNotEmpty())
        val resultGraph = VertexOrderedGraph<Int>()
        edgeList.forEach { addEdgeWithVertices(resultGraph, it.first, it.second) }
        return resultGraph
    }

    /**
     * @param filePath is the String that provides the path to the resource the graph is generated from
     * @return A list of Int-Pairs: Every pair specifies 1 edge in the graph by providing the IDs of the
     * vertices it connects.
     */
    @Throws(IOException::class)
    fun unweightedEdgesFromNetworkRepo(filePath: String): List<Pair<Int, Int>> {
        return Files.lines(Paths.get(filePath)).toList()
                .filter { it.matches(unweightedEdgeFormat_NetworkRepo) }
                .map { it.split(separator_NetworkRepo) }
                .map { Pair(parseInt(it[0]), parseInt(it[1])) }
    }

    /**
     * Reading in graphs in NetworkRepositories format is so common that this function combines the needed methods.
     */
    @Throws(IOException::class)
    fun simpleGraphFromNetworkRepo(filePath: String): VertexOrderedGraph<Int> {
        return simpleGraphByEdges(unweightedEdgesFromNetworkRepo(filePath))
    }


}
