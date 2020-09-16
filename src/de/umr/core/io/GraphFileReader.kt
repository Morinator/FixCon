package de.umr.core.io

import de.umr.core.dataStructures.GraphFile
import de.umr.fixcon.defaultEdgeWeight
import de.umr.core.graphFromWeightedEdges
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.io.File
import java.lang.Integer.parseInt

fun edgesFromFile(file: String, allowLoops: Boolean = false) = File(file).readLines().asSequence()
        .filter { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }
        .map { it.split(Regex("""\s+""")) }
        .filter { it[0] != it[1] || allowLoops }
        .mapTo(ArrayList(), { Triple(parseInt(it[0]), parseInt(it[1]), defaultEdgeWeight) })

fun graphFromFile(file: String, allowLoops: Boolean = false) =
        graphFromWeightedEdges(edgesFromFile(file, allowLoops))

fun edgesFromFile(file: GraphFile, allowLoops: Boolean = false) = edgesFromFile(file.path, allowLoops)

fun graphFromFile(f: GraphFile): SimpleWeightedGraph<Int, DefaultEdge> = graphFromWeightedEdges(edgesFromFile(f))