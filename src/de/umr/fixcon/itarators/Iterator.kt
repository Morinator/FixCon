package de.umr.fixcon.itarators

import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

abstract class Iterator<V>(val p: Problem<V>, val start: V, val sol: Solution<V> = Solution()) {

    abstract val subgraph: Graph<V, DefaultEdge>

    protected val numVerticesMissing get() = p.f.k - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0

    abstract fun mutate()
}