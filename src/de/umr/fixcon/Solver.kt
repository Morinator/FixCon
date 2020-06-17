package de.umr.fixcon

import de.umr.core.printFullAnalysis
import de.umr.core.vertexCount
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import de.umr.removeSmallComponents

class Solver<V>(private val problem: Problem<V>) {

    init {
        val oldVertexCount = problem.g.vertexCount
        removeSmallComponents(problem.g, problem.k)
        println("Clearing components:".padEnd(25) + "${oldVertexCount - problem.g.vertexCount} vertices \n")
        printFullAnalysis(problem.g)
    }

    var verticesDeleted = 0
    private val s = Solution<V>()
    var iter = subIterAtAnyVertex()

    fun solve(): Solution<V> {

        while (s.value < problem.globalOptimum && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }

        println("Failed SubIterators:".padEnd(25) + verticesDeleted)

        return s
    }

    private fun subIterAtAnyVertex() = SubIterator(problem, problem.g.vertexSet().first(), s)

    private fun updateStartVertexIfNeeded() {
        while (!iter.isValid && problem.g.vertexCount > problem.k) {
            verticesDeleted++
            problem.g.removeVertex(iter.startVertex)
            iter = subIterAtAnyVertex()
        }
    }
}