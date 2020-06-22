package de.umr.fixcon.itarators

import de.umr.core.addEdgeWithVertices
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.openNB
import de.umr.core.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution

abstract class Iterator<V>(val problem: Problem<V>, val startVertex: V, val currBestSolution: Solution<V> = Solution()) {

    abstract val subgraph: VertexOrderedGraph<V>

    protected val numVerticesMissing get() = problem.function.k - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0

    protected fun addToSubgraph(vertex: V) = (problem.g.openNB(vertex) intersect subgraph.vertexSet())
            .forEach { subgraph.addEdgeWithVertices(it, vertex) }

    val additionBoundApplicable: Boolean
        get() = problem.eval(subgraph) + problem.completeAdditionBound(subgraph) <= currBestSolution.value
}