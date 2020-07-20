package de.umr.fixcon.universalGraphRule

import de.umr.core.extensions.addEdgeWithVertices
import de.umr.core.getNewVertexIDs
import de.umr.fixcon.Problem

fun universalGraphRule(p: Problem<Int>, vLeft: Int, currBest: Int): Boolean =
        if (vLeft == 1) {
            var beaten = false
            val testVertex: Int = getNewVertexIDs(p.g, 1).first()

            for (v in p.g.vertexSet()) {
                p.g.addEdgeWithVertices(v, testVertex)
                if (p.f.eval(p.g) > currBest) beaten = true
                p.g.removeVertex(testVertex)
                if (beaten) break
            }
            !beaten
        } else false
