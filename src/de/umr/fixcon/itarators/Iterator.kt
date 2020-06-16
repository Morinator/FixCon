package de.umr.fixcon.itarators

import de.umr.core.addEdgeWithVertices
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.openNB
import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution

abstract class Iterator<V>(val problem: CFCO_Problem<V>, val startVertex: V, private val currBestSolution: Solution<V> = Solution()) {

    abstract val subgraph: VertexOrderedGraph<V>

    protected val numVerticesMissing get() = problem.targetSize - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0

    private fun currentFunctionalValue() = problem.function.eval(subgraph, problem.parameters)

    protected fun updateSolution() {
        if (isValid) currBestSolution.updateIfBetter(subgraph, currentFunctionalValue())
    }

    protected fun addToSubgraph(vertex: V) =
            (problem.originalGraph.openNB(vertex) intersect subgraph.vertexSet())
                    .forEach { subgraph.addEdgeWithVertices(it, vertex) }

    val additionBoundApplicable: Boolean
        get() = currentFunctionalValue() + problem.function.completeAdditionBound(subgraph, problem.targetSize, problem.parameters) <= currBestSolution.value
}