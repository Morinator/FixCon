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

    /**@param g The graph that is tested if it contains any triangles.
     *
     * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).
     */
    fun <E> hasTriangle(g: Graph<E, DefaultEdge>) = g.vertexSet().any { x ->
        neighborSetOf(g, x).any { y -> neighborSetOf(g, x).any { z -> g.containsEdge(y, z) } }
    }

    /**Because the library jGraphT has no method for copying graphs from my knowledge, this method was created
     * to provide this service. It can only copy Graphs which Int-values as vertices, because if the method was
     * totally generic it could not be guaranteed that the vertices are cloneable.
     *
     * @param g The graph that should be copied.
     *
     * @return A new Integer-valued graph, which is a copy of [g]*/
    fun copyIntGraph(g: Graph<Int, DefaultEdge>): Graph<Int, DefaultEdge> {
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        g.vertexSet().forEach { neighborSetOf(g, it).forEach { y -> addEdgeWithVertices(resultGraph, it, y) } }
        return resultGraph
    }

    /**This method returns the vertex-induced subgraph of a given graph [g]. It receives a set of vertices
     * [subgraphVertexSet] which has to be a subset of the vertices in [g] and returns a graph with only
     * the vertices of [subgraphVertexSet] and the edges between vertices from [subgraphVertexSet] that were present
     * in [g].
     *
     * @param g The graph from which the subgraph is built.
     * @param subgraphVertexSet The set of vertices which induces the subgraph.
     *
     * @return A new graph which is the vertex-induced subgraph by [subgraphVertexSet] in [g].
     *
     */
    fun inducedSubgraph(g: Graph<Int, DefaultEdge>, subgraphVertexSet: Set<Int>): Graph<Int, DefaultEdge> {
        val resultGraph = SimpleGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        subgraphVertexSet.forEach { resultGraph.addVertex(it) }
        subgraphVertexSet.forEach { vertexA ->
            subgraphVertexSet.forEach { vertexB ->
                if (vertexA != vertexB && g.containsEdge(vertexA, vertexB))
                    addEdgeWithVertices(resultGraph, vertexA, vertexB)
            }
        }
        return resultGraph
    }
}

