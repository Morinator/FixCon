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

fun <V> solve(p: Problem<V>): Solution<V> {
    removeSmallComponents(p.g, p.f.k)

    val sol = getHeuristic(p)
    if (sol.value == p.f.globalOptimum()) return sol

    val cPart = getCriticalPartitioning(p)

    var iteratorsUsed = 0

    while (sol.value < p.f.globalOptimum() && p.graphBigEnough) {

        prunePartsGreaterK(p.g, p.f.k, cPart)

        val startV = getStartVertex(cPart)
        val iterator = SimpleIter(p, startV, sol)

        while (iterator.isValid) iterator.mutate()

        val nbVertices = p.g.openNB(cPart[startV])
        p.g.removeAllVertices(cPart[startV].also { println("Partition vertices deleted:".padEnd(pad) + it.size) })
        cPart.removeSubset(startV)

        critCliqueMerge(p.g, cPart, nbVertices)
        critISMerge(p.g, cPart, nbVertices)

        iteratorsUsed++
    }
    return sol.also { println("Iterators used:".padEnd(pad) + iteratorsUsed) }
}

private fun <V> getStartVertex(critPartition: SetPartitioning<V>) =
        critPartition.subsets.maxBy { it.size }!!.random()