package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.util.*

/**
 * A modified [SimpleGraph] that stores the inserted vertices in insertion-order.
 * Therefore, [removeLastVertex] can be called.
 */
class VertexOrderedGraph<E>() : SimpleGraph<E, DefaultEdge>(DefaultEdge::class.java) {

    private val insertionStack = LinkedList<E>()

    constructor(vararg elem: E) : this() {
        elem.forEach { addVertex(it) }
    }

    override fun addVertex(elem: E): Boolean {
        return if (!vertexSet().contains(elem)) {
            super.addVertex(elem)
            insertionStack.push(elem)
            true
        } else false
    }

    /**
     * Removes the last added vertex, if there are any vertices.
     * @return **True** if the graph has changed as a result of the call, so if any vertices were present.
     */
    fun removeLastVertex(): Boolean {
        return if (vertexSet().isNotEmpty()) {
            super.removeVertex(insertionStack.pop())
        } else false
    }

    /**[size] is the number of vertices in the graph. Therefore it can't be negative.*/
    val size: Int
        get() = vertexSet().size
}