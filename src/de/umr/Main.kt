package de.umr

import de.umr.core.dataStructures.openNB
import de.umr.core.dataStructures.vertexCount
import de.umr.core.io.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun main() {
    val g = graphFromFile(GraphFile.CustomTree)
    removeVerticesByPredicate(g) { g.degreeOf(it) == 1 }
    println(g.vertexCount)
}

fun <V> removeVerticesByPredicate(g: Graph<V, DefaultEdge>, predicate: (V) -> Boolean) {
    val badVertices: MutableSet<V> = g.vertexSet().filter(predicate).toMutableSet()

    while (badVertices.isNotEmpty()) {
        val next = badVertices.first().also { badVertices.remove(it) }
        val neighbours = g.openNB(next)
        g.removeVertex(next)
        neighbours.forEach { if (predicate(it)) badVertices.add(it) else badVertices.remove(it) }
    }
}