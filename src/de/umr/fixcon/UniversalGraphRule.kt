package de.umr.fixcon

import de.umr.core.dataStructures.OrderedGraph
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.universalGraphRuleSkips
import de.umr.useUniversalGraphRule
import org.paukov.combinatorics3.Generator.subset
import kotlin.Int.Companion.MAX_VALUE

fun universalGraphRule(sub: OrderedGraph<Int>, verticesLeft: Int, f: AbstractGraphFunction, currBest: Int): Boolean {
    if (!useUniversalGraphRule || verticesLeft != 1) return false

    val x = MAX_VALUE
    val vList = sub.vertexSet().toList()
    var beaten = false

    sub.addVertex(x)
    for (toggleSet in subset(vList).simple().stream().skip(1)) {
        for (v in vList) sub.removeEdge(v, x)
        toggleSet.forEach { sub.addEdge(it, x) }
        if (f.eval(sub) > currBest) {
            beaten = true
            break
        }
    }
    sub.removeLastVertex()  //MAX_VALUE
    return !beaten.also { if (it) universalGraphRuleSkips++ }

}