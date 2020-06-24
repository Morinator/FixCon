package de.umr.core

import de.umr.core.dataStructures.degreeSequence
import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.pad
import de.umr.core.dataStructures.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

private const val defaultTableSize = 10

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>) = HashMap<Int, Double>().apply {
    g.vertexSet().forEach { put(g.degreeOf(it), getOrDefault(g.degreeOf(it), 0.0) + 1.0) }
    entries.forEach { put(it.key, it.value / g.vertexCount) }
}

fun<V> printFullAnalysis(g: Graph<V, DefaultEdge>, tableSize: Int = defaultTableSize) {
    println("Vertices:".padEnd(pad) + g.vertexCount)
    println("Edges:".padEnd(pad) + g.edgeCount)
    println("Max-Degree:".padEnd(pad) + g.degreeSequence.max()!!)
    println("\nDegree-Freq:")
    val m = degreeShareMap(g)
    m.entries.sortedBy { it.value }.reversed().take(tableSize).forEach {
        println(it.key.toString().padEnd(pad) + "%.3f".format(it.value))
    }
    println()
}