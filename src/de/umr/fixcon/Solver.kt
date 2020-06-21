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

    fun solve(): Solution<V> {
        removeSmallComponents(problem.g, problem.function.k)
        printFullAnalysis(problem.g)
        val sol = someSolution(problem)

        var usedIterators = 0

        while (sol.value < problem.globalOptimum && problem.g.vertexCount >= problem.function.k) {

            //removeVerticesByPredicate(problem.g) { problem.function.localOptimum(problem.g, it) < sol.value }
            val iter = SubIterator(problem, problem.g.vertexSet().first(), sol)

            while (iter.isValid) {
                iter.mutate()
            }
            problem.g.removeVertex(iter.startVertex)

            usedIterators++
        }

        println("Iterators used:".padEnd(25) + usedIterators)
        return sol
    }
}