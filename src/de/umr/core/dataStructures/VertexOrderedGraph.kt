package de.umr.core.dataStructures

import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.graph.SimpleWeightedGraph
import java.lang.IllegalStateException
import java.util.*

/**
 * A modified [SimpleWeightedGraph] that stores the inserted vertices in insertion-order.
 *
 * Therefore, the method [removeLastVertex] can be implemented, which removes the vertex in this graph
 * that was added latest temporally.
 */
class VertexOrderedGraph<E>() : SimpleWeightedGraph<E, DefaultEdge>(DefaultEdge::class.java) {

    /**private field that tracks the insertion order of the vertices. The last entry in this list is the
     * vertex that was added last*/
    private val insertionStack = LinkedList<E>()

    /*** Creates the graph and adds the vertices in [elem]*/
    constructor(vararg elem: E) : this() {
        elem.forEach { addVertex(it) }
    }

    /**
     * @return *True* iff the graph changed as a result of the call, so iff the vertex [elem] was not already
     * present in the graph.
     */
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

    /**
     * Convenience-method that contains the weight of the edge between [vertexA] and [vertexB].
     *
     * @throws [IllegalStateException] if the requested edge is not present in the graph.
     * @return The weight of the edge between the specified vertices, if the edge exists.
     */
    fun getEdgeWeight(vertexA: E, vertexB: E) : Double {
        if (!containsEdge(vertexA, vertexB)) throw IllegalStateException("These vertices aren't connected by an edge")
        return getEdgeWeight(getEdge(vertexA, vertexB))
    }
}