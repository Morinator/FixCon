package de.umr.fixcon

import de.umr.core.printFullAnalysis
import de.umr.core.vertexCount
import de.umr.fixcon.heuristics.someSolution
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution
import de.umr.removeSmallComponents
import de.umr.removeVerticesByPredicate

class Solver<V>(private val problem: Problem<V>) {

    init {
        val oldVertexCount = problem.g.vertexCount
        removeSmallComponents(problem.g, problem.k)
        println("Clearing components:".padEnd(25) + "${oldVertexCount - problem.g.vertexCount} vertices \n")
        printFullAnalysis(problem.g)
    }

    var verticesDeleted = 0
    private val s = someSolution(problem)



    fun solve(): Solution<V> {

        removePointlessVertices()

        var iter = subIterAtAnyVertex()
        fun updateStartVertexIfNeeded() {

            while (!iter.isValid ) {
                removePointlessVertices()
                if (problem.g.vertexCount <= problem.k) return

                verticesDeleted++
                problem.g.removeVertex(iter.startVertex)
                iter = subIterAtAnyVertex()
            }
        }

        while (s.value < problem.globalOptimum && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }

        println("Failed SubIterators:".padEnd(25) + verticesDeleted)

        return s
    }

    private fun removePointlessVertices() =
            removeVerticesByPredicate(problem.g) { problem.function.localOptimum(problem.k, problem.g, it) <= s.value }

    private fun subIterAtAnyVertex() = SubIterator(problem, problem.g.vertexSet().first(), s)
}