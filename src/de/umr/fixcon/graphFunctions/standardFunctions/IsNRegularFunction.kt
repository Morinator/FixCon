package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
object IsNRegularFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun <V> completeAdditionBound(subgraph: VertexOrderedGraph<V>, targetSize: Int, args: List<Int>) =
            throw Exception("not implemented yet")

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = IsDegreeConstrainedFunction.eval(g, listOf(args[0], args[0]))
}