package de.umr.fixcon.twins

import de.umr.core.dataStructures.SetPartitioning
import de.umr.core.dataStructures.addByEQPredicate
import de.umr.core.extensions.closedNB
import de.umr.core.extensions.openNB
import de.umr.fixcon.Problem

fun <V> pruneBigSubsets(partitioning: SetPartitioning<V>, problem: Problem<V>) {
    partitioning.subsets.toList().forEach {
        while (it.size > problem.f.k) {
            val badVertex: V = it.first()
            partitioning.removeElem(badVertex)
            problem.g.removeVertex(badVertex)
        }
    }
}

fun <V> critCliques(p: Problem<V>, vertices: Collection<V> = p.g.vertexSet()) =
        SetPartitioning<V>().apply {
            for (verticesByHash in vertices.groupBy { vHashClosed(p.g, it) }.values) {
                addByEQPredicate(verticesByHash) { x, y -> p.g.closedNB(x) == p.g.closedNB(y) }
            }
        }

fun <V> critIS(p: Problem<V>, partitioning: SetPartitioning<V>) {
    val availableVertices = partitioning.elements.filter { partitioning[it].size == 1 }.toSet()
    println("IS: ${availableVertices.size}")
    availableVertices.forEach { partitioning.removeElem(it) }

    for (vertexByHash in availableVertices.groupBy { vHashOpen(p.g, it) }.values) {
        partitioning.addByEQPredicate(vertexByHash) { x, y -> p.g.openNB(x) == p.g.openNB(y) }
    }
}
