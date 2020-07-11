package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.GraphTests.isTree
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the graph is a tree (connected and acyclic).*/
class AcyclicFunction(k : Int = dummyK) : AbstractGraphFunction(k = k) {

    /**1 if true, 0 if false.*/
    override fun <V> eval(g: Graph<V, DefaultEdge>) = if (isTree(g)) 1 else 0

    /**The value 1 indicates *True*.*/
    override fun globalOptimum() = 1
}