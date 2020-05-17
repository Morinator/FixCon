package de.umr.fixcon

import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution

/**
 * Solves the specified [problem] by calling [solve].
 */
class CFCOSolver(private val problem: CFCO_Problem) {
    /**Tracks the current maximum solution throughout the algorithm and updates it once a better one is found*/
    private val solution = Solution()

    private val subIterator = SubIterator(problem)

    /**@return the [solution] which returns the optimal subgraph and value for the input specified by [problem]*/
    fun solve(): Solution {
        while (solution.value < problem.function.globalUpperBound(problem.targetSize) && subIterator.isValid()) {
            if (subIterator.currentBestValue > solution.value)
                solution.update(subIterator.current(), subIterator.currentBestValue)
            subIterator.mutate()
        }
        return solution
    }
}