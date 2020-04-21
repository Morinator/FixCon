package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class EdgeCountFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double = g.vertexSet().map { x -> g.degreeOf(x) }.sum() / 2.0

    override fun optimum(size: Int): Double {
        require(size >= 0)
        return size * (size - 1) / 2.0
    }
}