package de.umr.fixcon.itarators

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution

abstract class Iterator<V>(val problem: Problem<V>, val startVertex: V, val sol: Solution<V> = Solution()) {

    abstract val subgraph: VertexOrderedGraph<V>

    protected val numVerticesMissing get() = problem.function.k - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0
}