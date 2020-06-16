package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphTests.isTree
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the graph is a tree (connected and acyclic).*/
object AcyclicFunction : GraphFunction() {
    override fun <V> eval(g: Graph<V, DefaultEdge>, args: List<Int>) = if (isTree(g)) 1 else 0
}