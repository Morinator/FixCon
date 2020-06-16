package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Specifies an interface any function that maps a finite graph to a real number must fulfill.*/
interface GraphFunction {

    val vertexAdditionBound: Int get() = 0

    /**An objective function *f* is vertex-addition-bounded by value *x*, if for every graph *G* and
    all graphs *G'* that are obtained by adding some vertex to *G* and making this vertex adjacent to
    some subset of *Vertices(G)*, we have *f(G')* less-or-equal *f(G)* + *x*.*/
    fun <V> completeAdditionBound(subgraph: VertexOrderedGraph<V>, targetSize: Int, args: List<Int> = emptyList()) =
            (targetSize - subgraph.vertexCount) * vertexAdditionBound

    /**Applies the function to a graph and returns the resulting real number*/
    fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int> = emptyList()): Int

    /**Returns the optimum value the function can return for a graph of the size [graphSize]
     * [graphSize] has a default-value because for some functions it's not needed, e.g. for decision-problems
     * The default value of this function is 1, which is the [globalOptimum] for decision-problems*/
    fun globalOptimum(graphSize: Int = -123/*Functions that need graphSize throw exception at -123*/): Int = 1

}