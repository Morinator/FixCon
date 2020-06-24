package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Specifies an interface any function that maps a finite graph to a real number must fulfill.*/
abstract class AbstractGraphFunction(val args: List<Int> = emptyList(), var k: Int) {

    open val vertexAdditionBound: Int get() = 0

    /**An objective function *f* is vertex-addition-bounded by value *x*, if for every graph *G* and
    all graphs *G'* that are obtained by adding some vertex to *G* and making this vertex adjacent to
    some subset of *Vertices(G)*, we have *f(G')* less-or-equal *f(G)* + *x*.*/
    open fun <V> completeAdditionBound(subgraph: Graph<V, DefaultEdge>) = (k - subgraph.vertexCount) * vertexAdditionBound

    /**Applies the function to a graph and returns the resulting real number*/
    abstract fun <V> eval(g: Graph<V, DefaultEdge>): Int

    /**Returns the optimum value the function can return for a graph of the size [k]
     * [k] has a default-value because for some functions it's not needed, e.g. for decision-problems
     * The default value of this function is 1, which is the [globalOptimum] for decision-problems*/
    open fun globalOptimum(): Int = 1
}