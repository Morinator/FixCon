package de.umr.fixcon.cliqueJoinRule

import de.umr.core.dataStructures.unorderedPairs
import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import de.umr.fixcon.Solution
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun cliqueJoinRule(p: Problem<Int>, sol: Solution<Int> = Solution()): Boolean {
    val newCliqueVertices = unusedVertexSet(sol.subgraph, p.f.k - sol.subgraph.vertexCount)

    addAsClique(sol.subgraph, newCliqueVertices)

    val extendableVertices = sol.subgraph.vertexSet().filter { p.g.degreeOf(it) != sol.subgraph.degreeOf(it) }
    connectVertexSets(p.g, newCliqueVertices, extendableVertices)
    val applicable = true

    return applicable
}

fun <V> connectVertexSets(g: Graph<V, DefaultEdge>, vCol1: Collection<V>, vCol2: Collection<V>) {
    for (v1 in vCol2) for (v2 in vCol1) g.addEdgeWithVertices(v1, v2)
}

fun <V> addAsClique(g: Graph<V, DefaultEdge>, newCliqueVertices: Set<V>) {
    unorderedPairs(newCliqueVertices).forEach { g.addEdgeWithVertices(it.first, it.second) }
}

/**@return A [Set] of [num] vertex-IDs that are NOT already used in [g].*/
fun unusedVertexSet(g: Graph<Int, DefaultEdge>, num: Int): Set<Int> {

    val firstNewID = g.vertexSet().max()!! + 1
    return (firstNewID until firstNewID + num).toSet()

}