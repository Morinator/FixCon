package de.umr.core

import de.umr.core.dataStructures.VertexOrderedGraph
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**Note: jGraphT also provides this method, but it counts ALL triangles and doesn't stop once it has found one.
 *
 * @param g The graph that is tested if it contains any triangles.
 * @param V The type of the vertices.
 * @return **True** iff the graph contains at least 1 triangle (a clique with 3 vertices).*/
fun <V> hasTriangle(g: Graph<V, DefaultEdge>) = g.edgeSet()
        .any { (g.openNB(g.getEdgeSource(it)) intersect g.openNB(g.getEdgeTarget(it))).isNotEmpty() }