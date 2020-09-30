package de.umr.core.dataStructures

import de.umr.core.connectVertices
import org.jgrapht.Graphs.addAllVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.paukov.combinatorics3.Generator
import org.paukov.combinatorics3.Generator.combination

class CliqueTracker(val size: Int) : SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java) {

    private var lowest = -size

    init {
        (-1 downTo lowest).toList().forEach { super.addVertex(it) }
        combination((-1 downTo lowest).toList()).simple(2).forEach { addEdge(it[0], it[1]) }
    }

    override fun addVertex(v: Int): Boolean {
        require(v >= 0) { "Negative Vertex-IDs are reserved to manage the clique." }
        return super.addVertex(v).also { if (it) super.removeVertex(lowest++) }
    }

    override fun removeVertex(v: Int): Boolean {
        require(v >= 0) { "Negative Vertex-IDs are reserved to manage the clique." }
        val success = super.removeVertex(v)
        if (success) {
            super.addVertex(--lowest)
            connectVertices(this, listOf(lowest), (-1 downTo lowest + 1).toList())
        }
        return success
    }
}