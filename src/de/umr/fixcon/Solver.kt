package de.umr.fixcon

import de.umr.core.dataStructures.SetPartition
import de.umr.core.dataStructures.addByEQPredicate
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.vHashClosed
import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.Heuristic
import de.umr.fixcon.itarators.SimpleIter

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.function.k)

    val sol = Heuristic(p).get()
    if (sol.value == p.function.globalOptimum()) return sol

    val partition = critCliques(p)
    pruneCritCliques(partition, p)

    var iteratorsUsed = 0
    while (sol.value < p.function.globalOptimum() && p.g.vertexCount >= p.function.k) {
        val startVertex = partition.subsets().maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        println("BAD VERTICES: ${partition[startVertex].size}")
        p.g.removeAllVertices(partition[startVertex])
        partition.removeSubset(startVertex)
        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

private fun <V> pruneCritCliques(criticalCliques: SetPartition<V>, problem: Problem<V>) {
    criticalCliques.subsets().toList().forEach {
        while (it.size > problem.function.k) {
            val badVertex: V = it.first()
            criticalCliques.removeElem(badVertex)
            problem.g.removeVertex(badVertex)
        }
    }
}

private fun <V> critCliques(problem: Problem<V>) =
        SetPartition<V>().apply {
            for (verticesByHash in problem.g.vertexSet().groupBy { problem.g.vHashClosed(it) }.values) {
                addByEQPredicate(verticesByHash) { x, y -> problem.g.closedNB(x) == problem.g.closedNB(y) }
            }
        }