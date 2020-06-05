package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Specifies an interface any function that maps a finite graph to a real number must fulfill.*/
interface GraphFunction {

    //TODO comment
    val vertexAdditionBound: Int get() = 0

    /**A function fulfills [isEdgeMonotone] if the inclusion of new edges into a graph
     * can't lower the resulting value of the function on this graph*/
    val isEdgeMonotone: Boolean

    /**An objective function *f* is vertex-addition-bounded by value *x*, if for every graph *G* and
    all graphs *G'* that are obtained by adding some vertex to *G* and making this vertex adjacent to
    some subset of *Vertices(G)*, we have *f(G')* less-or-equal *f(G)* + *x*.*/
    fun completeAdditionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>) =
            (targetSize - subgraph.vertexCount) * vertexAdditionBound

    /**Applies the function to a graph and returns the resulting real number*/
    fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>): Int

    /**Returns the optimum value the function can return for a graph of the size [size]*/
    fun globalUpperBound(vararg size: Int): Int = 1
}