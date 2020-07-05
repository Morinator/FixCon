package de.umr.core

import de.umr.core.extensions.degreeSequence
import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vHashClosed
import de.umr.core.extensions.vertexCount
import de.umr.core.io.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

private const val defaultTableSize = 10

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>) = g.vertexSet()
        .groupingBy { g.degreeOf(it) }.eachCount()
        .mapValues { 1.0 * it.value / g.vertexCount }

fun <V> printFullAnalysis(g: Graph<V, DefaultEdge>, tableSize: Int = defaultTableSize) {
    println("Vertices:".padEnd(pad) + g.vertexCount)
    println("Edges:".padEnd(pad) + g.edgeCount)
    println("Max-Degree:".padEnd(pad) + g.degreeSequence.max()!!)
    println("\nDegree-Freq:")
    degreeShareMap(g).entries.sortedByDescending { it.value }.take(tableSize).forEach {
        println(it.key.toString().padEnd(pad) + "%.3f".format(it.value))
    }
    println()
}

fun numberOfDistinctClosedHashes() = GraphFile.values().map { graphFromFile(it) }
        .map { g -> g.vertexSet().map { g.vHashClosed(it) }.distinct().count() }.sum()