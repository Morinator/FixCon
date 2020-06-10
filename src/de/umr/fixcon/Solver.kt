package de.umr.fixcon

import de.umr.core.vertexCount
import de.umr.fixcon.heuristics.LowerBoundGenerator
import de.umr.fixcon.itarators.SubIteratorFromStart
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

/**Solves the specified [problem] by calling [solve].*/
class Solver(private val problem: CFCO_Problem) {

    private val useHeuristic: Boolean = false

    private val bestSolution = if (useHeuristic) LowerBoundGenerator(problem).getBound() else Solution()
    //private val bestSolution = Solution(SimpleGraph(DefaultEdge::class.java), 14)

    private var iter = subIterAtAnyVertex()

    fun solve(): Solution {

        fun updateStartVertexIfNeeded() {
            while (!iter.isValid && problem.originalGraph.vertexCount > problem.targetSize) {
                problem.originalGraph.removeVertex(iter.startVertex)
                iter = subIterAtAnyVertex()
            }
        }

        while (bestSolution.value < problem.function.globalUpperBound(problem.targetSize) && iter.isValid) {
            iter.mutate()
            updateStartVertexIfNeeded()
        }
        return bestSolution
    }

    private fun subIterAtAnyVertex() = SubIteratorFromStart(problem, problem.originalGraph.vertexSet().first(), bestSolution)
}