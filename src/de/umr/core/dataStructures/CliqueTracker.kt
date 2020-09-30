package de.umr.core.dataStructures

import de.umr.core.connectVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.paukov.combinatorics3.Generator.combination

class CliqueTracker(val size: Int) : SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java) {

    private var lowest = -size
    val cliqueValues: List<Int> get() = (-1 downTo lowest).toList()

    init {
        cliqueValues.forEach { super.addVertex(it) }
        combination((-1 downTo lowest).toList()).simple(2).forEach { addEdge(it[0], it[1]) }
    }

    override fun addVertex(v: Int): Boolean {
        require(v >= 0) { "Negative Vertex-IDs are reserved to manage the clique." }
        require(v in vertexSet() || lowest < 0) {"Size surpassed. Clique is already empty."}    //Either addition without effect or enough space is there.
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