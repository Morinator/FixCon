package de.umr.fixcon

import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.*

/**
 * Solves the specified [problem] by calling [solve].
 */
class CFCOSolver(private val problem: CFCO_Problem) {
    /**Tracks the current maximum solution throughout the algorithm and updates it once a better one is found*/
    private val solution = Solution()

    private val subIterator = SubIterator(problem)

    /**@return the [solution] which returns the optimal subgraph and value for the input specified by [problem]*/
    fun solve(): Solution {
        while (notOptimalNorExhausted()) {
            if (currentIsBetter()) solution.update(subIterator.current(), valueOfSubgraph())
            subIterator.mutate()
        }
        return solution
    }

    /**@Return *true* iff an optimal solution has not been found yet and [subIterator] isn't yet exhausted*/
    private fun notOptimalNorExhausted() =
            solution.value < problem.function.globalUpperBound(problem.targetSize) && subIterator.isValid()

    /**@Returns the value by applying the function specified by [problem] to the subgraph currently
     * selected by [subIterator]*/
    private fun valueOfSubgraph() = problem.function.apply(subIterator.current(), problem.parameters)

    /**@returns *True* iff the currently selected subgraph has a better functional value than the previous maximum*/
    private fun currentIsBetter() = valueOfSubgraph() > solution.value
}