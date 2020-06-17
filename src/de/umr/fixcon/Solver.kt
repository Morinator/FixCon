package de.umr.fixcon

import de.umr.core.vertexCount
import de.umr.fixcon.heuristics.someSolution
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import de.umr.removeSmallComponents

class Solver<V>(private val problem: Problem<V>) {

    fun solve(): Solution<V> {

        removeSmallComponents(problem.g, problem.k)

        val s = Solution<V>()

        fun subIterAtAnyVertex() = SubIterator(problem, problem.g.vertexSet().random(), s)

        var iter = subIterAtAnyVertex()

        fun updateStartVertexIfNeeded() {

            while (!iter.isValid && problem.g.vertexCount > problem.k) {
                problem.g.removeVertex(iter.startVertex)
                iter = subIterAtAnyVertex()
            }
        }

        while (s.value < problem.globalOptimum && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }
        return s
    }
}