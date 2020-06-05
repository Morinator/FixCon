package de.umr.core

import de.umr.core.GraphFileReader.edgesFromFile
import de.umr.core.dataStructures.VertexOrderedGraph
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

/**This class supports reading in graphs and lists of edges from a text-file, specified in the edge-list-format from
 * the website NetworkRepository.
 *
 * The class [VertexOrderedGraph] has a constructor that takes a list of edges,
 * which can be obtained from [edgesFromFile].*/
object GraphFileReader {

    private val weightedEdgePattern = Regex("""\d+\s+\d+\s+\d+""")      //3 separated Ints
    private val unweightedEdgePattern = Regex("""\d+\s+\d+""")          //2 separated Ints
    private val separator = Regex("""\s+""")                            //some whitespace

    private fun edgesFromFile(filePath: String, weighted: Boolean = false): List<Triple<Int, Int, Double>> =
            Files.lines(Paths.get(filePath)).toList()
                    .filter { it.matches(if (weighted) weightedEdgePattern else unweightedEdgePattern) }
                    .map { it.split(separator) }
                    .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }

    /**Returns a [VertexOrderedGraph] based off the text-file that's present at [filePath]. It uses the adjacency-list
     * format from NetworkRepository. If [weighted] is *false*, all edges have a default weight of 1.0*/
    fun graphFromFile(filePath: String, weighted: Boolean = false) = VertexOrderedGraph(edgesFromFile(filePath, weighted))
}