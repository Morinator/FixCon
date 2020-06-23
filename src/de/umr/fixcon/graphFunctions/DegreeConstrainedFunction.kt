package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the degree of every vertex in the graph
 * is inside of the specified range*/
class DegreeConstrainedFunction(args: List<Int> = emptyList(), k: Int) : AbstractGraphFunction(args, k) {

    override fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) =
            if (subgraph.vertexSet().any { subgraph.degreeOf(it) > args[0] }) 0 else 1

    override fun <V> eval(g: Graph<V, DefaultEdge>): Int {
        fun rangeBetween(x: Int, y: Int) = if (x <= y) x..y else y..x
        return if (g.vertexSet().all { g.degreeOf(it) in rangeBetween(args[0], args[1]) }) 1 else 0
    }
}