package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**
 * Specifies an interface any function that maps a finite graph to a real number must fulfill.
 */
interface GraphFunction {

    /**A function fulfills [isEdgeMonotone] if the inclusion of new edges into a graph
     * can't lower the resulting value of the function on this graph*/
    val isEdgeMonotone: Boolean

    /**Applies the function to a graph and returns the resulting real number*/
    fun apply(g: Graph<Int, DefaultEdge>, args: List<Int>): Double

    /**Returns the optimum value the function can return for a graph of the size [size]*/
    fun optimum(vararg size: Int): Double = 1.0
}