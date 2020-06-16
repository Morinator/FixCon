package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.graphFunctions.standardFunctions.DegreeConstrainedFunction as dcf

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
object NRegularFunction : GraphFunction() {
    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>, targetSize: Int, args: List<Int>) =
            dcf.completeAdditionBound(subgraph, targetSize, listOf(args[0], args[0]))

    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) =
            dcf.eval(g, listOf(args[0], args[0]))
}