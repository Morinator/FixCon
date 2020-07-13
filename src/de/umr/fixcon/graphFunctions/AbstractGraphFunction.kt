package de.umr.fixcon.graphFunctions

import de.umr.core.extensions.vertexCount
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

const val dummyK = 1234 // dummy value in cases where k is irrelevant

/**Specifies an interface any function that maps a finite graph to a real number must fulfill.*/
abstract class AbstractGraphFunction(val args: List<Int> = emptyList(), var k: Int = dummyK) {

    open val vertexAdditionBound: Int get() = 0

    /**An objective function *f* is vertex-addition-bounded by value *x*, if for every graph *G* and
    all graphs *G'* that are obtained by adding some vertex to *G* and making this vertex adjacent to
    some subset of *Vertices(G)*, we have *f(G')* less-or-equal *f(G)* + *x*.*/
    open fun <V> completeBound(subgraph: Graph<V, DefaultEdge>) = (k - subgraph.vertexCount) * vertexAdditionBound

    /**Applies the function to a graph and returns the resulting real number*/
    abstract fun <V> eval(g: Graph<V, DefaultEdge>): Int

    /**Returns the optimum value the function can return for a graph of the size [k]
     * [k] has a default-value because for some functions it's not needed, e.g. for decision-problems
     * The default value of this function is 1, which is the [globalOptimum] for decision-problems*/
    abstract fun globalOptimum(): Int
}