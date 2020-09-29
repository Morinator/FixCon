package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class OrderedGraph<V> : SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java) {

    private var removalAllowed: Boolean = false
    val orderedVertices = ArrayList<V>()

    override fun addVertex(v: V) = super.addVertex(v).also { if (it) orderedVertices.add(v) }

    override fun removeVertex(v: V): Boolean {
        require(removalAllowed) { "This method may not be called from the outside." }
        removalAllowed = false
        return super.removeVertex(v)
    }

    fun removeLastVertex(): V {
        require(orderedVertices.isNotEmpty())
        val v = orderedVertices.removeLast()
        removalAllowed = true
        removeVertex(v)
        return v
    }
}