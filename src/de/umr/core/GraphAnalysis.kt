package de.umr.core

import de.umr.core.dataStructures.degreeSequence
import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import de.umr.paddingRight
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

const val defaultTableSize = 10

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>): Map<Int, Double> = g.vertexSet()
        .groupingBy { g.degreeOf(it) }.eachCount()
        .mapValues { 1.0 * it.value / g.vertexCount }

fun <V> printFullAnalysis(g: Graph<V, DefaultEdge>, tableSize: Int = defaultTableSize) {
    println("Vertices:".padEnd(paddingRight) + g.vertexCount)
    println("Edges:".padEnd(paddingRight) + g.edgeCount)
    println("Max-Degree:".padEnd(paddingRight) + g.degreeSequence.maxOrNull()!!)
    println("\nDegree-Freq:")
    degreeShareMap(g).entries.sortedByDescending { it.value }.take(tableSize).forEach {
        println(it.key.toString().padEnd(paddingRight) + "%.3f".format(it.value))
    }
    println()
}