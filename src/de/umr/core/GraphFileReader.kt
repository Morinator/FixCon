package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import java.io.File
import java.lang.Integer.parseInt

private val separator = Regex("""\s+""")                                                //some whitespace
private val vertex = Regex("""\d+""")                                                   //a positive integer number

private val unweightedEdgePattern = Regex("""$vertex$separator$vertex""")               //2 separated Ints
private val weightedEdgePattern = Regex("""$unweightedEdgePattern$separator$vertex""")  //3 separated Ints

/**
 * @param filePath The path of the graph file that is read in.
 * @param weighted True iff it should read in the third number in each line as the weight of the edge.
 * @param allowLoops If true: Allows lines in which the first and second number is the same,
 * as this would encode a self-loop in the graph.
 *
 * @return A [List] of [Triple]. Each triple corresponds to one edge. The first and second entry in the triple is
 * the IDs of the vertices the edge connects. The
 */
fun edgesFromFile(filePath: String, weighted: Boolean = false, allowLoops: Boolean = false)
        = File(filePath).readLines()
        .filter { it.matches(if (weighted) weightedEdgePattern else unweightedEdgePattern) }
        .map { it.split(separator) }
        .filter { if (allowLoops) true else it[0] != it[1] }
        .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }

/**Returns a [VertexOrderedGraph] based off the text-file that's present at [filePath]. It uses the adjacency-list
 * format from NetworkRepository. If [weighted] is *false*, all edges have a default weight of 1.0*/
fun graphFromFile(filePath: String, weighted: Boolean = false) =
        VertexOrderedGraph(edgesFromFile(filePath, weighted))