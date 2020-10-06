package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import org.paukov.combinatorics3.Generator.combination

class CliqueTracker(val totalSize: Int) : SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java) {

    var cliqueSize = totalSize.also { require(it >= 0) }

    fun cliqueVertices() = (-1 downTo -cliqueSize).toList()

    init {
        cliqueVertices().forEach { super.addVertex(it) }
        combination(cliqueVertices()).simple(2).forEach { super.addEdge(it[0], it[1]) }
    }

    override fun addVertex(v: Int): Boolean {
        require(v >= 0) { "ID must be non-negative" }
        require(cliqueSize >= 0) { "Clique is already empty." }

        if (containsVertex(v)) return false
        super.removeVertex(-(cliqueSize--))
        return super.addVertex(v)
    }

    override fun removeVertex(v: Int): Boolean {
        require(v >= 0) { "ID must be non-negative" }
        if (!containsVertex(v)) return false

        super.addVertex(-(++cliqueSize))
        (-1 downTo -(cliqueSize - 1)).forEach { addEdge(-cliqueSize, it) }
        super.removeVertex(v)
        return true
    }
}