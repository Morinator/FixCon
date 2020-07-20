package de.umr.fixcon.cliqueJoinRule

import de.umr.core.addAsClique
import de.umr.core.connectVertexSets
import de.umr.core.extendableVertices
import de.umr.core.extensions.vertexCount
import de.umr.core.getNewVertexIDs
import de.umr.fixcon.Problem
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun cliqueJoinRule(sub: Graph<Int, DefaultEdge>, p: Problem<Int>, bestVal: Int) = cliqueJoinValue(sub, p) <= bestVal

fun cliqueJoinValue(sub: Graph<Int, DefaultEdge>, p: Problem<Int>): Int {
    val newIDs = getNewVertexIDs(sub, p.f.k - sub.vertexCount)

    addAsClique(sub, newIDs)
    connectVertexSets(sub, extendableVertices(sub, p.g), newIDs)

    val result = p.eval(sub)
    sub.removeAllVertices(newIDs)
    return result
}