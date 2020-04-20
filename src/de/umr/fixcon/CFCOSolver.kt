package de.umr.fixcon

import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import java.util.*

class CFCOSolver(private val problem: CFCO_Problem) {
    private val solution: Solution = Solution()
    private val subIterator: SubIterator = SubIterator(problem.graph, problem.subgraphSize)
    private val globalOptimum: Double = problem.function.optimum(problem.subgraphSize)
    fun getSolution(): Solution {
        while (notOptimalNorExhausted()) {
            if (currentIsBetter()) solution.update(subIterator.current(), valueOfSubgraph())
            subIterator.mutate()
        }
        println("${subIterator.searchTreeCounter}\n${subIterator.sizeKSubgraphCount}")
        return solution
    }

    private fun notOptimalNorExhausted() = solution.value < globalOptimum && subIterator.isValid()

    private fun valueOfSubgraph() = problem.function.apply(subIterator.current(), *problem.parameters)

    private fun currentIsBetter(): Boolean = valueOfSubgraph() > solution.value
}