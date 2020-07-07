package de.umr.core

import de.umr.core.extensions.degreeSequence
import de.umr.core.extensions.edgeCount
import de.umr.core.extensions.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>): Map<Int, Double> = g.vertexSet()
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