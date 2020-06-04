package de.umr.fixcon

import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution

/**
 * Solves the specified [problem] by calling [solve].
 */
class CFCOSolver(private val problem: CFCO_Problem) {

    private val currBestSolution = Solution()

    private val subIterator = SubIterator(problem, currBestSolution)

    fun solve(): Solution {
        while (currBestSolution.value < problem.function.globalUpperBound(problem.targetSize) && subIterator.isValid)
            subIterator.mutate()
        return currBestSolution
    }
}