package de.umr.fixcon.graphFunctions

import org.jgrapht.Graph
import org.jgrapht.GraphTests.isTree
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the graph is a tree (connected and acyclic).*/
class AcyclicFunction(k : Int) : AbstractGraphFunction(k = k) {
    override fun <V> eval(g: Graph<V, DefaultEdge>) = if (isTree(g)) 1 else 0
}