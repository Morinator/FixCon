package de.umr.fixcon

import de.umr.core.dataStructures.OrderedGraph
import de.umr.core.dataStructures.vertexCount
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.universalGraphRuleSkips
import de.umr.useUniversalGraphRule
import org.paukov.combinatorics3.Generator.subset
import kotlin.Int.Companion.MAX_VALUE

fun universalGraphRule(sub: OrderedGraph<Int>, f: AbstractGraphFunction, currBest: Int, vList: List<Int> = sub.vertexSet().toList()): Boolean {
    if (!useUniversalGraphRule || (f.k - sub.vertexCount) != 1) return false
    var beaten = false
    sub.addVertex(MAX_VALUE)
    for (toggleSet in subset(vList).simple().stream().skip(1)) {
        for (v in vList) sub.removeEdge(v, MAX_VALUE)
        toggleSet.forEach { sub.addEdge(it, MAX_VALUE) }
        if (f.eval(sub) > currBest) {
            beaten = true
            break
        }
    }

    sub.removeLastVertex()  //MAX_VALUE
    return !beaten.also { if (it) universalGraphRuleSkips++ }

}