package de.umr.core.io

import de.umr.core.dataStructures.GraphFile
import de.umr.core.defaultEdgeWeight
import de.umr.core.graphFromWeightedEdges
import java.io.File
import java.lang.Integer.parseInt

private val vertex = Regex("""\w+""")                       //non-empty number
private val weight = Regex("""[+-]?([0-9]*[.])?[0-9]+""")   //float number
private val separator = Regex("""\s+""")                    //non-empty whitespace

private val unweightedEdge = Regex("""$vertex$separator$vertex""")
private val weightedEdge = Regex("""$unweightedEdge$separator$weight""")


fun edgesFromFile(file: String, allowLoops: Boolean = false, skipLines: Int = 0) = File(file).readLines().asSequence().drop(skipLines)
        .filter { it.matches(unweightedEdge) || it.matches(weightedEdge) }
        .map { it.split(separator) }
        .filter { it[0] != it[1] || allowLoops }
        .mapTo(ArrayList(), { Triple(parseInt(it[0]), parseInt(it[1]), defaultEdgeWeight) })

fun graphFromFile(file: String, allowLoops: Boolean = false, skipLines: Int = 0) =
        graphFromWeightedEdges(edgesFromFile(file, allowLoops, skipLines))


fun edgesFromFile(file: GraphFile, allowLoops: Boolean = false) = edgesFromFile(file.path, allowLoops, file.skipLines)

fun graphFromFile(f: GraphFile) = graphFromWeightedEdges(edgesFromFile(f))