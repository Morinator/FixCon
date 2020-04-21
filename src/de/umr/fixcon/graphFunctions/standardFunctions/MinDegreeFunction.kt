package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

class MinDegreeFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = true

    override fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>) = g.vertexSet().map { x -> g.degreeOf(x) }.min()!!.toDouble()

    override fun optimum(size: Int): Double = (size - 1).toDouble()
}