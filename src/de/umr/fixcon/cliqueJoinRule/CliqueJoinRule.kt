package de.umr.fixcon.cliqueJoinRule

import de.umr.core.*
import de.umr.core.extensions.vertexCount
import de.umr.fixcon.Problem
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun cliqueJoinRule(sub: Graph<Int, DefaultEdge>, p: Problem<Int>, bestVal: Int) =
        useCliqueJoinRule && cliqueJoinValue(sub, p) <= bestVal

fun cliqueJoinValue(sub: Graph<Int, DefaultEdge>, p: Problem<Int>): Int {
    val newIDs = getNewVertexIDs(p.g, p.f.k - sub.vertexCount)
    val extendable = extendableVertices(sub, p.g)
    addAsClique(sub, newIDs)
    connectVertexSets(sub, extendable, newIDs)

    val result = p.eval(sub)
    sub.removeAllVertices(newIDs)
    return result
}