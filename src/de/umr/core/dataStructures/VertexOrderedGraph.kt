package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.util.*

class VertexOrderedGraph<E> : SimpleGraph<E, DefaultEdge>(DefaultEdge::class.java) {
    private val insertionStack = LinkedList<E>()

    override fun addVertex(elem: E): Boolean {
        return if (!vertexSet().contains(elem)) {
            super.addVertex(elem)
            insertionStack.push(elem)
            true
        } else
            false
    }

    fun removeLastVertex(): Boolean {
        return if (vertexSet().isNotEmpty()) {
            super.removeVertex(insertionStack.pop())
            true
        } else
            false
    }

    val size: Int
        get() = vertexSet().size

}