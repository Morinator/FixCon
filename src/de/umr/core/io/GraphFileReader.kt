package de.umr.core.io

import de.umr.core.dataStructures.GraphFile
import de.umr.core.defaultEdgeWeight
import de.umr.core.fromWeightedEdges
import java.io.File
import java.lang.Integer.parseInt

private val separator = Regex("""\s+""")
private val vertex = Regex("""\w+""")

private val unweightedEdge = Regex("""$vertex$separator$vertex""")
private val weightedEdge = Regex("""$unweightedEdge$separator$vertex""")

fun edgesFromFile(file: String, allowLoops: Boolean = false, skipLines: Int = 0, weighted: Boolean = false) = File(file).readLines().asSequence().drop(skipLines)
        .filter { it.matches(if (weighted) weightedEdge else unweightedEdge) }
        .map { it.split(separator) }
        .filter { it[0] != it[1] || allowLoops }
        .mapTo(ArrayList(), { Triple(parseInt(it[0]), parseInt(it[1]), if (weighted) it[2].toDouble() else defaultEdgeWeight) })

fun graphFromFile(file: String, allowLoops: Boolean = false, skipLines: Int = 0, weighted: Boolean = false) =
        fromWeightedEdges(edgesFromFile(file, allowLoops, skipLines, weighted))



fun edgesFromFile(file: GraphFile, allowLoops: Boolean = false) = edgesFromFile(file.path, allowLoops, file.skipLines, file.weighted)

fun graphFromFile(f: GraphFile) = fromWeightedEdges(edgesFromFile(f))