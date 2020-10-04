package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import de.umr.fixcon.graphFunctions.DegreeConstrainedFunction as dcf

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is exactly the specified Integer.*/
class RRegularFunction(args: List<Int>, k: Int = dummyK) : AbstractGraphFunction(args, k) {

    /**The number of vertices whose degree is smaller than the lower bound, but could be increased
     * enough to fit into the range given by [args] with the vertices that can yet be added
     * to the subgraph.*/
    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) = dcf(k, args).completeBound(subgraph)

    /**Counts the number of invalid vertices and negates the number,
     * so that the function increases as fewer vertices are invalid.*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = dcf(k, listOf(args[0], args[0])).eval(g)

    /**If no vertex has the wrong degree, the functional value is 0*/
    override fun globalOptimum() = 0
}