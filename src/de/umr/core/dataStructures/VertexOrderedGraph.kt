package de.umr.core.dataStructures

import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.util.*

/**
 * A modified [SimpleWeightedGraph] that stores the inserted vertices in insertion-order.
 *
 * Therefore, the method [removeLastVertex] can be implemented, which removes the vertex in this graph
 * that was added latest temporally.
 */
class VertexOrderedGraph<V>() : SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java) {

    /**private field that tracks the insertion order of the vertices. The last entry in this list is the
     * vertex that was added last*/
    private val insertionStack: Deque<V> = LinkedList()

    /*** Creates the graph and adds the vertices in [elem]*/
    constructor(vararg elem: V) : this() {
        elem.forEach { addVertex(it) }
    }

    constructor(edgeList: List<Triple<V, V, Double>>) : this() {
        require(edgeList.isNotEmpty())
        edgeList.forEach { addWeightedEdge(it.first, it.second, it.third) }
    }

    /**
     * @return *True* iff the graph changed as a result of the call, so iff the vertex [elem] was not already
     * present in the graph.
     */
    override fun addVertex(elem: V): Boolean {
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

    /**@return size is the number of vertices in the graph. Therefore it can't be negative.*/
    val vertexCount: Int
        get() = vertexSet().size

    /**
     * Convenience-method that contains the weight of the edge between [vertexA] and [vertexB].
     *
     * @throws [IllegalStateException] if the requested edge is not present in the graph.
     * @return The weight of the edge between the specified vertices, if the edge exists.
     */
    fun getEdgeWeight(vertexA: V, vertexB: V): Double {
        if (!containsEdge(vertexA, vertexB)) throw IllegalStateException("These vertices aren't connected by an edge")
        return getEdgeWeight(getEdge(vertexA, vertexB))
    }

    fun addWeightedEdge(vertexA: V, vertexB: V, weight: Double): VertexOrderedGraph<V> {
        addEdgeWithVertices(this, vertexA, vertexB)
        setEdgeWeight(vertexA, vertexB, weight)
        return this
    }
}