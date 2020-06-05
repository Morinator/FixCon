package de.umr.core.dataStructures

import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.util.*

/**A modified [SimpleWeightedGraph] that stores the inserted vertices in insertion-order.
 *
 * @param V Is the type of the vertices in the graph.
 *
 * It also provides some other utilities not related to vertex-ordering*/
class VertexOrderedGraph<V>() : SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java) {

    /**Stores the insertion-order of the vertices in the graph*/
    private val vertexStack: Deque<V> = LinkedList()

    /** Creates the graph and adds the vertices in [vertices]*/
    constructor(vararg vertices: V) : this() {
        vertices.forEach { addVertex(it) }
    }

    constructor(edges: List<Triple<V, V, Double>>) : this() {
        require(edges.isNotEmpty())
        edges.forEach { addWeightedEdge(it.first, it.second, it.third) }
    }

    /**@return *True* iff the graph changed as a result of the call, so iff the vertex [elem] was not already
     * present in the graph.*/
    override fun addVertex(elem: V) = super.addVertex(elem).also { hasChanged -> if (hasChanged) vertexStack.push(elem) }

    /**Removes the last added vertex, if there are any vertices.
     * @return **True** if the graph has changed as a result of the call, so if any vertices were present.*/
    fun removeLastVertex() = (vertexCount > 0).also { notEmpty -> if (notEmpty) super.removeVertex(vertexStack.pop()) }

    /**@return size is the number of vertices in the graph. Therefore it can't be negative.*/
    val vertexCount: Int get() = vertexSet().size

    /**Returns the weight of the edge between [vertexA] and [vertexB].
     *
     * @throws [IllegalStateException] if the requested edge is not present in the graph.
     * @return The weight of the edge between the specified vertices, if the edge exists.*/
    fun getEdgeWeight(vertexA: V, vertexB: V): Double {
        if (!containsEdge(vertexA, vertexB)) throw IllegalStateException("These vertices aren't connected by an edge")
        return getEdgeWeight(getEdge(vertexA, vertexB))
    }

    /**Adds an edge between [vertexA] and [vertexB] with weight [weight]
     *
     * @return [VertexOrderedGraph] The resulting graph after the call of this method*/
    fun addWeightedEdge(vertexA: V, vertexB: V, weight: Double): VertexOrderedGraph<V> {
        addEdgeWithVertices(this, vertexA, vertexB)
        setEdgeWeight(vertexA, vertexB, weight)
        return this
    }
}