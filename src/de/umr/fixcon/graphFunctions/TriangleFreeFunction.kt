package de.umr.fixcon.graphFunctions

import de.umr.core.dataStructures.hasTriangle
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

/**This function returns 1 (indicator for **True**) iff the graph contains no triangles.*/
class TriangleFreeFunction(k: Int) : AbstractGraphFunction(k = k) {
    override fun <V> eval(g: Graph<V, DefaultEdge>) = if (g.hasTriangle) 0 else 1
}