package de.umr.fixcon.universalGraphRule

import de.umr.core.dataStructures.BitFlipIterator
import de.umr.core.pow
import de.umr.core.extensions.toggleEdge
import de.umr.core.getNewVertexIDs
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

fun universalGraphRule(sub: Graph<Int, DefaultEdge>, verticesLeft: Int, f: AbstractGraphFunction, currBest: Int): Boolean =
        if (verticesLeft == 1) {
            val l = sub.vertexSet().toList()
            val newID: Int = getNewVertexIDs(sub, 1).first()
            var beaten = false
            val bsi = BitFlipIterator()
            sub.addVertex(newID)

            for (i in 0 until (2 pow l.size)-1) { //checks all subsets of possible edges to newID
                bsi.next().forEach { sub.toggleEdge(newID, l[it]) }
                if (f.eval(sub) > currBest) {
                    beaten = true
                    break
                }
            }

            sub.removeVertex(newID)
            !beaten
        } else false