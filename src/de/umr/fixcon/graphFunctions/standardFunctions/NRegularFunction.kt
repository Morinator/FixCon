package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.graphFunctions.standardFunctions.DegreeConstrainedFunction as dcf

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
class NRegularFunction(args: List<Int> = emptyList(), k: Int) : GraphFunction(args, k) {
    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) =
            dcf(args, k).completeAdditionBound(subgraph)

    override fun <V> eval(g: Graph<V, DefaultEdge>) =
            dcf(listOf(args[0], args[0]), k).eval(g)
}