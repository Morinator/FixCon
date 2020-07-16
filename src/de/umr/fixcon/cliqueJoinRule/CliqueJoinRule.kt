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
    addAsClique(newCliqueVertices, p)

    val extendableVertices = sol.subgraph.vertexSet().filter { p.g.degreeOf(it) != sol.subgraph.degreeOf(it) }
    for (v1 in extendableVertices) {
        for (v2 in newCliqueVertices) {

        }
    }
    val applicable = true

    return applicable
}

fun addAsClique(newCliqueVertices: Set<Int>, p: Problem<Int>) {
    unorderedPairs(newCliqueVertices).forEach { p.g.addEdgeWithVertices(it.first, it.second) }
}

/**@return A [Set] of [num] vertex-IDs that are NOT already used in [g].*/
fun unusedVertexSet(g: Graph<Int, DefaultEdge>, num: Int): Set<Int> {

    val firstNewID = g.vertexSet().max()!! + 1
    return (firstNewID until firstNewID + num).toSet()

}