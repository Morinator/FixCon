package de.umr.fixcon

import de.umr.core.dataStructures.SetPartition
import de.umr.core.dataStructures.addByEQPredicate
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.vHashClosed
import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.Heuristic
import de.umr.fixcon.itarators.SubIterator

fun <V> solve(problem: Problem<V>): Solution<V> {
    removeSmallComponents(problem.g, problem.function.k)

    val p = SetPartition<V>()
    for (verticesByHash in problem.g.vertexSet().groupBy { problem.g.vHashClosed(it) }.values) {
        p.addByEQPredicate(verticesByHash) { x, y -> problem.g.closedNB(x) == problem.g.closedNB(y) }
    }

    for (criticalClique in p.subsets().toList()) {
        while (criticalClique.size > problem.function.k) {
            val v: V = criticalClique.first()
            p.remove(v)
            problem.g.removeVertex(v)
            println("TWIN RULE APPLIED")
        }
    }

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