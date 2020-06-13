package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
object NRegularFunction : GraphFunction {
    override fun <V> completeAdditionBound(subgraph: VertexOrderedGraph<V>, targetSize: Int, args: List<Int>) =
            DegreeConstrainedFunction.completeAdditionBound(subgraph, targetSize, listOf(args[0], args[0]))

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) =
            DegreeConstrainedFunction.eval(g, listOf(args[0], args[0]))
}