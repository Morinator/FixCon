package de.umr.fixcon

import de.umr.core.printFullAnalysis
import de.umr.core.removeSmallComponents
import de.umr.core.removeVerticesByPredicate
import de.umr.core.vertexCount
import de.umr.fixcon.heuristics.someSolution
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.Problem
import de.umr.fixcon.wrappers.Solution

class Solver<V>(private val problem: Problem<V>) {

    init {
        removeSmallComponents(problem.g, problem.function.k)
        printFullAnalysis(problem.g)
    }

    var verticesDeleted = 0
    private val sol = someSolution(problem)
    lateinit var iter: SubIterator<V>

    fun solve(): Solution<V> {
        if (sol.value == problem.globalOptimum) return sol

        clearUselessVertices()
        iter = subIterAtAnyVertex()


        while (sol.value < problem.globalOptimum && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }

        println("Failed SubIterators:".padEnd(25) + verticesDeleted)
        return sol
    }

    private fun updateStartVertexIfNeeded() {

        while (!iter.isValid) {
            clearUselessVertices()
            if (problem.g.vertexCount < problem.function.k) return

            verticesDeleted++
            problem.g.removeVertex(iter.startVertex)
            iter = subIterAtAnyVertex()
        }
    }

    private fun clearUselessVertices() {
        return
        removeVerticesByPredicate(problem.g) {
            problem.function.localOptimum(problem.g, it) <= sol.value && problem.g.degreeOf(it) > 0
        }
    }

    private fun subIterAtAnyVertex() = SubIterator(problem, problem.g.vertexSet().first(), sol)
}