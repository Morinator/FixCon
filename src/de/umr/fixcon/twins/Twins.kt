package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.dataStructures.addByEQPredicate
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem

fun <V> getCriticalPartitioning(p: Problem<V>): SetPartitioning<V> {

    fun helper(vertices: Collection<V> = p.g.vertexSet(), hashFu: (V) -> List<Int>, nbSelector: (V) -> Set<V>) =
            SetPartitioning<V>().apply {
                for (verticesByHash in vertices.groupBy { hashFu(it) }.values) {
                    addByEQPredicate(verticesByHash) { x, y -> nbSelector(x) == nbSelector(y) }
                }
            }

    val partitioning = helper(p.g.vertexSet(), { vHashClosed(p.g, it) }, { p.g.closedNB(it) })

    val verticesLeft = partitioning.elements.filter { partitioning[it].size == 1 }.toList()
    println("Small Cliques: ${verticesLeft.size}")

    partitioning.removeAll(verticesLeft)

    helper(verticesLeft, { vHashOpen(p.g, it) }, { p.g.openNB(it) }).subsets.forEach { partitioning.addInNewSubset(it) }
    return partitioning
}
