package de.umr.fixcon

import de.umr.core.dataStructures.BitFlipIterator
import de.umr.core.dataStructures.OrderedGraph
import de.umr.core.dataStructures.toggleEdge
import de.umr.core.pow
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.universalGraphRuleSkips
import de.umr.useUniversalGraphRule

fun universalGraphRule(sub: OrderedGraph<Int>, verticesLeft: Int, f: AbstractGraphFunction, currBest: Int, extendables: Collection<Int> = sub.vertexSet()): Boolean =
        (useUniversalGraphRule && if (verticesLeft == 1) {
            val vList = extendables.toList()
            val newID: Int = Int.MAX_VALUE
            var beaten = false
            val bsi = BitFlipIterator()
            sub.addVertex(newID)

            for (i in 0 until (2 pow vList.size) - 1) { //checks all subsets of possible edges to newID
                bsi.next().forEach { sub.toggleEdge(newID, vList[it]) }
                if (f.eval(sub) > currBest) {
                    beaten = true
                    break
                }
            }
            sub.removeLastVertex()
            !beaten
        } else false).also { if (it) universalGraphRuleSkips++ }
