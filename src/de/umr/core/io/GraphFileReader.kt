package de.umr.core.io

import de.umr.GraphFile
import de.umr.core.dataStructures.VertexOrderedGraph.Factory.fromWeightedEdges
import java.io.File
import java.lang.Integer.parseInt

private val separator = Regex("""\s+""")        //some whitespace
private val vertex = Regex("""\w+""")           //at least one alphanumeric character

private val unweightedEdge = Regex("""$vertex$separator$vertex""")
private val weightedEdge = Regex("""$unweightedEdge$separator$vertex""")

fun edgesFromFile(f: GraphFile, allowLoops: Boolean = false) = File(f.path).readLines().asSequence().drop(f.dropLines)
        .filter { it.matches(if (f.weighted) weightedEdge else unweightedEdge) }
        .map { it.split(separator) }
        .filter { allowLoops || it[0] != it[1] }
        .map { Triple(parseInt(it[0]), parseInt(it[1]), if (f.weighted) it[2].toDouble() else 1.0) }.toList()

fun graphFromFile(f: GraphFile) = fromWeightedEdges(edgesFromFile(f))