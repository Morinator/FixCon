package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun <V> shareOfDegree(g: Graph<V, DefaultEdge>, d: Int): Double =
        g.vertexSet().count { g.degreeOf(it) == d }.toDouble() / g.vertexCount

fun <V> degreeShareMap(g: Graph<V, DefaultEdge>) = HashMap<Int, Double>().apply {
    g.vertexSet().forEach { put(g.degreeOf(it), getOrDefault(g.degreeOf(it), 0.0) + 1.0) }
    entries.forEach { put(it.key, it.value / g.vertexCount) }
}