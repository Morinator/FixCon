package de.umr.core

import de.umr.core.dataStructures.degreeSequence
import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.pad
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

const val defaultTableSize = 10

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

/**@return A [Set] of [numberOfIDs] vertex-IDs that are NOT already used in [g].*/
fun getNewVertexIDs(g: Graph<Int, DefaultEdge>, numberOfIDs: Int): Set<Int> {
    val firstNewID = g.vertexSet().max()!! + 1
    return (firstNewID until firstNewID + numberOfIDs).toSet()
}