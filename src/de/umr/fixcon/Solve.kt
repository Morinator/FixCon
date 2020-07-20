package de.umr.fixcon

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.extensions.openNB
import de.umr.core.pad
import de.umr.core.prunePartsGreaterK
import de.umr.core.removeSmallComponents
import de.umr.fixcon.heuristic.getHeuristic
import de.umr.fixcon.itarators.SimpleIter
import de.umr.fixcon.twins.critCliqueMerge
import de.umr.fixcon.twins.critISMerge
import de.umr.fixcon.twins.getCriticalPartitioning

fun solve(p: Problem<Int>): Solution<Int> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    if (sol.value == p.f.globalOptimum()) return sol

    val critPart = getCriticalPartitioning(p)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.graphBigEnough) {

        prunePartsGreaterK(p.g, p.f.k, critPart)

        val startV = getStartVertex(critPart)
        val iterator = SimpleIter(p, startV, sol)

        while (iterator.isValid) iterator.mutate()

        val nbVertices = p.g.openNB(critPart[startV])
        p.g.removeAllVertices(critPart[startV].also { println("Partition vertices deleted:".padEnd(pad) + it.size) })
        critPart.removeSubset(startV)

        mergeTwinSets(p, critPart, nbVertices)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

private fun <V> mergeTwinSets(p: Problem<V>, cPart: SetPartitioning<V>, nbVertices: Set<V>) {
    critCliqueMerge(p.g, cPart, nbVertices)
    critISMerge(p.g, cPart, nbVertices)
}

private fun <V> getStartVertex(critPartition: SetPartitioning<V>) =
        critPartition.subsets.maxBy { it.size }!!.random()