package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.Graphs.addEdgeWithVertices
import org.jgrapht.graph.DefaultEdge

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 *
 * @param g The graph that is tested if it contains any triangles.
 * @param V The type of the vertices.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
fun <V> hasTriangle(g: Graph<V, DefaultEdge>) = g.vertexSet().any { v ->
    g.openNB(v).any { nb1 -> g.openNB(v).any { nb2 -> g.containsEdge(nb1, nb2) } }
}

/**This method was created because from my knowledge jGraphT doesn't provide copying of graphs.
 * It can only copy Graphs which Int-values as vertices, because if the method was totally generic it could not be
 * guaranteed that the vertices are cloneable.
 *
 * @param oldGraph The graph that should be copied.
 * @return A new Integer-valued graph, which is a copy of [oldGraph]*/
fun copyIntGraph(oldGraph: Graph<Int, DefaultEdge>) = VertexOrderedGraph<Int>().also { newGraph ->
    oldGraph.vertexSet().forEach { v -> oldGraph.openNB(v).forEach { nb -> addEdgeWithVertices(newGraph, v, nb) } }
}