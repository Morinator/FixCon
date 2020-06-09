package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import java.io.File
import java.lang.Integer.parseInt

private val unweightedEdgePattern = Regex("""\d+\s+\d+""")          //2 separated Ints
private val weightedEdgePattern = Regex("""\d+\s+\d+\s+\d+""")      //3 separated Ints
private val separator = Regex("""\s+""")                            //some whitespace

private fun edgesFromFile(filePath: String, weighted: Boolean = false) = File(filePath).readLines()
        .filter { it.matches(if (weighted) weightedEdgePattern else unweightedEdgePattern) }
        .map { it.split(separator) }
        .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }

/**Returns a [VertexOrderedGraph] based off the text-file that's present at [filePath]. It uses the adjacency-list
 * format from NetworkRepository. If [weighted] is *false*, all edges have a default weight of 1.0*/
fun graphFromFile(filePath: String, weighted: Boolean = false) = VertexOrderedGraph(edgesFromFile(filePath, weighted))