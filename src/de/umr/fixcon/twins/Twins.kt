package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartition
import de.umr.core.dataStructures.addByEQPredicate
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem

fun <V> pruneCritCliques(criticalCliques: SetPartition<V>, problem: Problem<V>) {
    criticalCliques.subsets().toList().forEach {
        while (it.size > problem.function.k) {
            val badVertex: V = it.first()
            criticalCliques.removeElem(badVertex)
            problem.g.removeVertex(badVertex)
        }
    }
}

fun <V> critCliques(p: Problem<V>): SetPartition<V> =
        SetPartition<V>().apply {
            for (verticesByHash in p.g.vertexSet().groupBy { vHashClosed(p.g, it) }.values) {
                addByEQPredicate(verticesByHash) { x, y -> p.g.closedNB(x) == p.g.closedNB(y) }
            }
        }

fun <V> critIS(p: Problem<V>, partition: SetPartition<V>) {
    val availableVertices = partition.elements().filter { partition[it].size == 1 }.toSet()
    println("IS: ${availableVertices.size}")
    availableVertices.forEach { partition.removeElem(it) }

    for (vertexByHash in availableVertices.groupBy { vHashOpen(p.g, it) }.values) {
        partition.addByEQPredicate(vertexByHash) { x, y -> p.g.openNB(x) == p.g.openNB(y) }
    }
}
