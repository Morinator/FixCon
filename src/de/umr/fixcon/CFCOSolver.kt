package de.umr.fixcon

import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.*

class CFCOSolver(private val problem: CFCO_Problem) {
    private val solution = Solution()
    private val subIterator = SubIterator(problem.graph, problem.subgraphSize)
    private val globalOptimum = problem.function.optimum(problem.subgraphSize)

    fun solve(): Solution {
        while (notOptimalNorExhausted()) {
            if (currentIsBetter()) solution.update(subIterator.current(), valueOfSubgraph())
            subIterator.mutate()
        }
        return solution
    }

    private fun notOptimalNorExhausted() = solution.value < globalOptimum && subIterator.isValid()

    private fun valueOfSubgraph() = problem.function.apply(subIterator.current(), problem.parameters)

    private fun currentIsBetter() = valueOfSubgraph() > solution.value
}