package de.umr.core

import de.umr.core.dataStructures.degrees
import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import de.umr.paddingRight
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>): Map<Int, Double> = g.vertexSet()
        .groupingBy { g.degreeOf(it) }.eachCount()
        .mapValues { 1.0 * it.value / g.vertexCount }


fun <V> printFullAnalysis(g: Graph<V, DefaultEdge>, tableSize: Int = 3) {
    println("Vertices:".padEnd(paddingRight) + g.vertexCount)
    println("Edges:".padEnd(paddingRight) + g.edgeCount)
    println("Max-Degree:".padEnd(paddingRight) + g.degrees.maxOrNull()!!)
    degreeShareMap(g).entries.sortedByDescending { it.value }.take(tableSize).forEach {
        println("${it.key}".padEnd(paddingRight) + ("%.1f".format(it.value * 100) + " % ").padStart(0))
    }
}