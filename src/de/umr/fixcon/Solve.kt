package de.umr.fixcon

import de.umr.core.extensions.openNB
import de.umr.core.extensions.vertexCount
import de.umr.core.pad
import de.umr.core.pruneBigSubsets
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.SimpleIter
import de.umr.fixcon.twins.getCriticalPartitioning

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    if (sol.value == p.f.globalOptimum()) return sol

    val critPartition = getCriticalPartitioning(p)
    pruneBigSubsets(critPartition, p)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount >= p.f.k) {
        val startVertex = critPartition.subsets.maxBy { it.size }!!.random()
        val iterator = SimpleIter(p, startVertex, sol)

        while (iterator.isValid) iterator.mutate()

        val nb = p.g.openNB(critPartition[startVertex])
        p.g.removeAllVertices(critPartition[startVertex])
        for (i in nb) {
            for (j in nb) {
                if (p.g.degreeOf(i) == p.g.degreeOf(j) && true) {
                    //TODO
                }
            }
        }
        println("Start Vertices deleted: ${critPartition[startVertex].size}")
        critPartition.removeSubset(startVertex)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

