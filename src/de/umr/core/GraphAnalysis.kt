package de.umr.core

import de.umr.GraphFile
import de.umr.core.io.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

private const val offset = 25

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>) = HashMap<Int, Double>().apply {
    g.vertexSet().forEach { put(g.degreeOf(it), getOrDefault(g.degreeOf(it), 0.0) + 1.0) }
    entries.forEach { put(it.key, it.value / g.vertexCount) }
}

fun printFullAnalysis(f: GraphFile, tableSize: Int = 5) {
    val g = graphFromFile(f)
    println("Name:".padEnd(offset) + f.name)
    printFullAnalysis(g, tableSize)
}

fun<V> printFullAnalysis(g: Graph<V, DefaultEdge>, tableSize: Int = 10) {
    println("Vertices:".padEnd(offset) + g.vertexCount)
    println("Edges:".padEnd(offset) + g.edgeCount)
    println("Max-Degree:".padEnd(offset) + g.degreeSequence.max()!!)
    println("\nDegree-Freq:")
    val m = degreeShareMap(g)
    m.entries.sortedBy { it.value }.reversed().take(tableSize).forEach {
        println(it.key.toString().padEnd(offset) + "%.2f".format(it.value))
    }
    println()
}