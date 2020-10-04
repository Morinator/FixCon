package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is inside of the specified range*/
class DegreeConstrainedFunction(k: Int = dummyK, args: List<Int>) : AbstractGraphFunction(args, k) {

    /**The number of vertices whose degree is smaller than the lower bound, but could be increased
     * enough to fit into the range given by [args] with the vertices that can yet be added
     * to the subgraph.*/
    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) =
            subgraph.vertexSet().count { (args[0] - subgraph.degreeOf(it)) <= (k - subgraph.vertexCount) }

    /**Counts the number of vertices that are outside of the specified range and negates the number,
     * so that the function increases as fewer vertices are invalid.*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = -g.vertexSet().count { g.degreeOf(it) !in args[0]..args[1] }

    /**0 vertices are outside of the specified range,*/
    override fun globalOptimum() = 0
}