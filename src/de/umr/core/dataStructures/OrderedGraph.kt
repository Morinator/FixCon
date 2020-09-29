package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

class OrderedGraph<V> : SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java) {

    private val stack = ArrayDeque<V>()

    override fun addVertex(v: V) = super.addVertex(v).also { if (it) stack.addLast(v) }

    fun removeLastVertex(): V {
        require(stack.isNotEmpty())
        val v = stack.removeLast()
        removeVertex(v)
        return v
    }
}