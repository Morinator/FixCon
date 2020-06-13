package de.umr.core

import de.umr.FilePaths
import java.io.File
import java.lang.Integer.parseInt
import de.umr.core.dataStructures.VertexOrderedGraph as vog

private val separator = Regex("""\s+""")        //some whitespace
private val vertex = Regex("""\w+""")           //at least one alphanumeric character

private val unweightedEdge = Regex("""$vertex$separator$vertex""")
private val weightedEdge = Regex("""$unweightedEdge$separator$vertex""")

/**
 * @param filePath The path of the graph file that is read in.
 * @param weighted True iff it should read in the third number in each line as the weight of the edge.
 * @param allowLoops If true: Allows lines in which the first and second number is the same,
 * as this would encode a self-loop in the graph.
 *
 * @return A [List] of [Triple]. Each triple corresponds to one edge. The first and second entry in the triple is
 * the IDs of the vertices the edge connects. The
 */
fun edgesFromFile(filePath: String, weighted: Boolean = false, allowLoops: Boolean = false) = File(filePath).readLines()
        .filter { it.matches(if (weighted) weightedEdge else unweightedEdge) }
        .map { it.split(separator) }
        .filter { allowLoops || it[0] != it[1] }
        .map { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else 1.0) }

fun edgesFromFile(filePath: FilePaths, weighted: Boolean = false, allowLoops: Boolean = false) =
        edgesFromFile(filePath.path, weighted, allowLoops)

/**Returns a [vog] based off the text-file that's present at [filePath]. It uses the adjacency-list
 * format from NetworkRepository. If [weighted] is *false*, all edges have a default weight of 1.0*/
fun graphFromFile(filePath: FilePaths, weighted: Boolean = false) = vog.fromWeightedEdges(edgesFromFile(filePath, weighted))