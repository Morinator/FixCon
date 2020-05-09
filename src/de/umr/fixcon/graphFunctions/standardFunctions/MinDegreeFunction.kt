package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class MinDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { g.degreeOf(it) }.min()!!.toDouble()

    override fun optimum(vararg size: Int): Double = (size[0] - 1).toDouble()
}