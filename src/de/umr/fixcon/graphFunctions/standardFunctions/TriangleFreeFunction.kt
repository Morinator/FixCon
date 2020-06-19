package de.umr.fixcon.graphFunctions.standardFunctions

import de.umr.core.hasTriangle
import de.umr.fixcon.graphFunctions.GraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the graph contains no triangles.*/
class TriangleFreeFunction(k: Int) : GraphFunction(k = k) {
    override fun <V> eval(g: Graph<V, DefaultEdge>) = if (g.hasTriangle) 0 else 1
}