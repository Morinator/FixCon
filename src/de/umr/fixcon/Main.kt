package de.umr.fixcon

import de.umr.GraphFile
import de.umr.core.pad
import de.umr.core.dataStructures.vertexCount
import de.umr.core.io.graphFromFile
import de.umr.core.printFullAnalysis
import de.umr.core.removeSmallComponents
import de.umr.fixcon.itarators.SubIterator

fun <V> solve(problem: Problem<V>): Solution<V> {
    removeSmallComponents(problem.g, problem.function.k)

    val sol = Heuristic(problem).get()

    var iteratorsUsed = 0
    while (sol.value < problem.function.globalOptimum() && problem.g.vertexCount >= problem.function.k) {
        val iterator = SubIterator(problem, problem.g.vertexSet().random(), sol)
        while (iterator.isValid)
            iterator.mutate()
        problem.g.removeVertex(iterator.startVertex)
        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

fun main() {
printFullAnalysis(graphFromFile(GraphFile.CoPapersCiteseer))
}