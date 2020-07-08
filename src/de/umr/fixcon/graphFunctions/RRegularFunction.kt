package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.graphFunctions.DegreeConstrainedFunction as dcf

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
class RRegularFunction(args: List<Int> = emptyList(), k: Int= dummyK) : AbstractGraphFunction(args, k) {

    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) = dcf(args, k).completeBound(subgraph)

    override fun <V> eval(g: Graph<V, DefaultEdge>) = dcf(listOf(args[0], args[0]), k).eval(g)

    override fun globalOptimum() = 0
}