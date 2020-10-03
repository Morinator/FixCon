package de.umr.core.dataStructures

import de.umr.core.addAsClique
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class CliqueTracker(val size: Int) : SimpleWeightedGraph<Int, DefaultEdge>(DefaultEdge::class.java) {

    init {
        addAsClique(this, (-1 downTo -size).toList())
    }

    override fun addVertex(v: Int): Boolean {
        return super.addVertex(v)
    }

    override fun removeVertex(v: Int): Boolean {
        return super.removeVertex(v)

    }
}