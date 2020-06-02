package de.umr.fixcon

import de.umr.fixcon.heuristics.LowerBoundGenerator
import de.umr.fixcon.itarators.SubIterator
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution

/**
 * Solves the specified [problem] by calling [solve].
 */
class CFCOSolver(private val problem: CFCO_Problem) {

    private val globalUpperBound = problem.function.globalUpperBound(problem.targetSize)

    private val subIterator = SubIterator(problem, LowerBoundGenerator(problem).getBound())

    fun solve(): Solution {
        while (subIterator.currBestSolution.value < globalUpperBound && subIterator.isValid())
            subIterator.mutate()
        return subIterator.currBestSolution
    }
}