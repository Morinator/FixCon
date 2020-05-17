package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

/**
 * This class is stateless and only provides algorithms for
 */
object GraphAlgorithms {

    /**
     * @return **True** iff the graph contains at least 1 triangle
     */
    fun <E> hasTriangle(g: Graph<E, DefaultEdge>) = g.vertexSet().any { x ->
        neighborSetOf(g, x).any { y -> neighborSetOf(g, x).any { z -> g.containsEdge(y, z) } }
    }

    /**
     * @return A new Integer-valued graph, which is a copy of [g]*/
    fun copyIntGraph(g: Graph<Int, DefaultEdge>): Graph<Int, DefaultEdge> {
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        g.vertexSet().forEach { neighborSetOf(g, it).forEach { y -> addEdgeWithVertices(resultGraph, it, y) } }
        return resultGraph
    }

    fun inducedSubgraph(g: Graph<Int, DefaultEdge>, subgraphVertexSet: Set<Int>): Graph<Int, DefaultEdge> {
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        subgraphVertexSet.forEach { resultGraph.addVertex(it) }
        subgraphVertexSet.forEach { x -> subgraphVertexSet.forEach {
            y -> if (x != y && g.containsEdge(x, y))
            addEdgeWithVertices(resultGraph, x, y) }
        }
        return resultGraph
    }
}

