package de.umr.fixcon

import de.umr.core.vertexCount
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution

class Solver<V>(private val problem: CFCO_Problem<V>) {

    private val bestSolution =  Solution<V>()

    private var iter = subIterAtAnyVertex()

    fun solve(): Solution<V> {

        fun updateStartVertexIfNeeded() {
            while (!iter.isValid && problem.originalGraph.vertexCount > problem.targetSize) {
                problem.originalGraph.removeVertex(iter.startVertex)
                iter = subIterAtAnyVertex()
            }
        }

        while (bestSolution.value < problem.function.globalOptimum(problem.targetSize) && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }
        return bestSolution
    }

    private fun subIterAtAnyVertex() = SubIterator(problem, problem.originalGraph.vertexSet().first(), bestSolution)
}