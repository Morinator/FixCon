package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.GraphTests.isTree
import org.jgrapht.graph.DefaultEdge

/**
 * This function returns 1 (indicator for **True**) iff the graph is a tree (connected and acyclic).
 */
object IsAcyclicFunction : GraphFunction {
    override val isEdgeMonotone: Boolean = false

    override fun completeAdditionBound(subgraph: VertexOrderedGraph<Int>, targetSize: Int, args: List<Int>) = 0

    override fun eval(g: Graph<Int, DefaultEdge>, args: List<Int>) = if (isTree(g)) 1 else 0
}