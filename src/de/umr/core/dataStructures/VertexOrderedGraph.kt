package de.umr.core.dataStructures

import de.umr.core.addWeightedEdge
import de.umr.core.vertexCount
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.util.*

/**A modified [SimpleWeightedGraph] that stores the inserted vertices in insertion-order.
 *
 * @param V Is the type of the vertices in the graph.
 *
 * It also provides some other utilities not related to vertex-ordering*/
class VertexOrderedGraph<V> : SimpleWeightedGraph<V, DefaultEdge>(DefaultEdge::class.java) {

    /**Stores the insertion-order of the vertices in the graph*/
    private val vertexStack: Deque<V> = LinkedList()

    companion object Factory {

        private const val defaultEdgeWeight = 1.0

        /** Creates the graph and adds the vertices in [vertices]*/
        fun <V> fromVertices(vararg vertices: V) =
                VertexOrderedGraph<V>().apply { vertices.forEach { addVertex(it) } }

        fun <V> fromWeightedEdges(edges: List<Triple<V, V, Double>>): VertexOrderedGraph<V> {
            require(edges.isNotEmpty())
            return VertexOrderedGraph<V>().apply { for ((v1, v2, weight) in edges) addWeightedEdge(v1, v2, weight) }
        }

        fun <V> fromUnweightedEdges(edges: List<Pair<V, V>>) =
                fromWeightedEdges(edges.map { (v1, v2) -> Triple(v1, v2, defaultEdgeWeight) })
    }

    /**@return *True* iff the graph changed as a result of the call, so iff the vertex [elem] was not already
     * present in the graph.*/
    override fun addVertex(elem: V) = super.addVertex(elem).also { if (it) vertexStack.push(elem) }

    /**Removes the last added vertex, if there are any vertices.
     * @return **True** if the graph has changed as a result of the call, so if any vertices were present.*/
    fun removeLastVertex() = (vertexCount > 0).also { if (it) super.removeVertex(vertexStack.pop()) }
}