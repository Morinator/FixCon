package de.umr.fixcon.itarators

import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph

abstract class Iterator<V>(val problem: CFCO_Problem<V>, val startVertex: V, val currBestSolution: Solution<V> = Solution()) {

    abstract val subgraph: SimpleWeightedGraph<V, DefaultEdge>

    protected val numVerticesMissing get() = problem.targetSize - subgraph.vertexCount

    val isValid get() = numVerticesMissing == 0

    protected fun currentFunctionalValue() = problem.function.eval(subgraph, problem.parameters)

    protected fun updateSolution() {
        if (isValid) currBestSolution.updateIfBetter(subgraph, currentFunctionalValue())
    }
}