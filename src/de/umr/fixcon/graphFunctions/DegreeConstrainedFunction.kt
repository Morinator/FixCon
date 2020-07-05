package de.umr.fixcon.graphFunctions

import de.umr.core.extensions.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is inside of the specified range*/
class DegreeConstrainedFunction(args: List<Int>, k: Int = dummyK) : AbstractGraphFunction(args, k) {

    override fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) =
            subgraph.vertexSet().count { (args[0] - subgraph.degreeOf(it)) <= (k - subgraph.vertexCount) }

    override fun <V> eval(g: Graph<V, DefaultEdge>) = -g.vertexSet().count { g.degreeOf(it) !in args[0]..args[1] }

    override fun globalOptimum() = 0
}