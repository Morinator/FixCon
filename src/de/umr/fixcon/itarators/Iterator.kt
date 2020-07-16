package de.umr.fixcon.itarators

import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

abstract class Iterator<V>(val p: Problem<V>, val start: V, val sol: Solution<V> = Solution()) {

    abstract val sub: Graph<V, DefaultEdge>

    protected val numVerticesMissing get() = p.f.k - sub.vertexCount

    val isValid get() = numVerticesMissing == 0
}