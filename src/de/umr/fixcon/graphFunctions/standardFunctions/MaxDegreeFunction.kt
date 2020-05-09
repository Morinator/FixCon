package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class MaxDegreeFunction :  GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { g.degreeOf(it) }.max()!!.toDouble()

    override fun optimum(vararg size: Int) = (size[0] - 1).toDouble()
}