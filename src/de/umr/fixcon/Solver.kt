package de.umr.fixcon

import de.umr.core.vertexCount
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution

class Solver<V>(private val problem: Problem<V>) {

    fun solve(): Solution<V> {

        val bestSolution = Solution<V>()

        fun subIterAtAnyVertex() = SubIterator(problem, problem.g.vertexSet().first(), bestSolution)

        var iter = subIterAtAnyVertex()

        fun updateStartVertexIfNeeded() {

            while (!iter.isValid && problem.g.vertexCount > problem.targetSize) {
                problem.g.removeVertex(iter.startVertex)
                iter = subIterAtAnyVertex()
            }
        }

        while (bestSolution.value < problem.function.globalOptimum(problem.targetSize) && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }
        return bestSolution
    }
}