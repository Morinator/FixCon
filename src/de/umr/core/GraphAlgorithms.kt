package de.umr.core

import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

/**
 * This object class is stateless and only provides algorithms for graphs.
 */
object GraphAlgorithms {

    /**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
     *
     * @param g The graph that is tested if it contains any triangles.
     *
     * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).
     */
    fun <E> hasTriangle(g: Graph<E, DefaultEdge>) = g.vertexSet().any { x ->
        neighborSetOf(g, x).any { y -> neighborSetOf(g, x).any { z -> g.containsEdge(y, z) } }
    }

    /**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
     *
     * It can only copy Graphs which Int-values as vertices, because if the method was totally generic it could not be
     * guaranteed that the vertices are cloneable.
     *
     * @param g The graph that should be copied.
     *
     * @return A new Integer-valued graph, which is a copy of [g]*/
    fun copyIntGraph(g: Graph<Int, DefaultEdge>): Graph<Int, DefaultEdge> {
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        g.vertexSet().forEach { neighborSetOf(g, it).forEach { y -> addEdgeWithVertices(resultGraph, it, y) } }
        return resultGraph
    }
}