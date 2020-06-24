package de.umr.core.io

import de.umr.GraphFile
import de.umr.core.dataStructures.VertexOrderedGraph.Factory.fromWeightedEdges
import de.umr.core.defaultEdgeWeight
import java.io.File
import java.lang.Integer.parseInt
import java.util.*

private val separator = Regex("""\s+""")        //some whitespace
private val vertex = Regex("""\w+""")           //at least one alphanumeric character

private val unweightedEdge = Regex("""$vertex$separator$vertex""")
private val weightedEdge = Regex("""$unweightedEdge$separator$vertex""")

fun edgesFromFile(f: GraphFile, allowLoops: Boolean = false) = File(f.path).readLines().asSequence().drop(f.dropLines)
        .filter { it.matches(if (f.weighted) weightedEdge else unweightedEdge) }
        .map { it.split(separator) }
        .filter { allowLoops || it[0] != it[1] }
        .mapTo(LinkedList(), { Triple(parseInt(it[0]), parseInt(it[1]), if (f.weighted) it[2].toDouble() else defaultEdgeWeight) })

fun graphFromFile(f: GraphFile) = fromWeightedEdges(edgesFromFile(f))