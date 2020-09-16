package de.umr.fixcon

import de.umr.core.dataStructures.Partitioning
import de.umr.core.extensions.neighbours
import de.umr.core.extensions.vertexCount
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

    val critPartition = getCriticalPartitioning(p)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.g.vertexCount > 0) {

        prunePartsGreaterK(p.g, p.f.k, critPartition)

        val startV = getStartVertex(critPartition)
        val iterator = SimpleIter(p, startV, sol)

        while (iterator.isValid) iterator.mutate()

        val nbVertices = p.g.neighbours(critPartition[startV])
        p.g.removeAllVertices(critPartition[startV])
        critPartition.removeSubset(startV)

        if (useCriticalMerging) mergeTwinSets(p, critPartition, nbVertices)

        iteratorsUsed++
    }
    return sol
}

private fun <V> mergeTwinSets(p: Problem<V>, cPart: Partitioning<V>, nbVertices: Set<V>) {
    critCliqueMerge(p.g, cPart, nbVertices)
    critISMerge(p.g, cPart, nbVertices)
}

private fun <V> getStartVertex(critPartition: Partitioning<V>) =
        critPartition.subsets.maxBy { it.size }!!.random()