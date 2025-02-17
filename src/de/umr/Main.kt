package de.umr

import de.umr.core.*
import de.umr.core.dataStructures.*
import de.umr.fixcon.deleteUpdateTwinSet
import de.umr.fixcon.getCriticalPartitioning
import de.umr.fixcon.getHeuristic
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.fixcon.graphFunctions.graphFunctionByID
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborListOf
import org.jgrapht.graph.DefaultEdge
import org.paukov.combinatorics3.Generator
import org.paukov.combinatorics3.Generator.subset
import java.io.File
import java.nio.file.Files.createDirectories
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashSet

/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 * args[3] == time limit in seconds*/

//##### Global Settings
const val paddingRight = 25
const val defaultEdgeWeight = 1.0
const val useHeuristic = false

//##### Globals
var searchTreeNodes: Long = 0
var vertexAdditionRuleSkips: Long = 0
var cliqueJoinRuleSkips: Long = 0
var criticalTwinSkips: Long = 0
var universalGraphRuleSkips: Long = 0

fun main(args: Array<String>) {

    //##### Reading of Command-Line arguments
    val graph = graphFromFile(file = args[0]).also { g -> require(g.vertexSet().all { v -> v >= 0 }) }
    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }
    val vertexCount = graph.vCount
    val edgeCount = graph.edgeCount

    val (solution, usedTime) = solve(graph, f = graphFunctionByID(funcID, k, funcParams), timeLimit = args[3].toInt())

    createDirectories(Paths.get("results")) //logging results
    File("results/${File(args[0]).name}.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + File(args[0]).name.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + usedTime.toString().padStart(14) + solution.value.toString().padStart(6) + ("Nodes: $searchTreeNodes").padStart(20) + "     " + solution.subgraph.vertexSet().toString() + "\n")
}

fun solve(g: Graph<Int, DefaultEdge>, f: AbstractGraphFunction, timeLimit: Int = Int.MAX_VALUE): Pair<Solution<Int>, Double> {

    val startTime = System.currentTimeMillis()  //Time tracking
    fun secondsElapsed(): Double = (((System.currentTimeMillis() - startTime) / 1000.0))

    printFullAnalysis(g)

    //Preparation & Heuristic
    removeComponentsSmallerThreshold(g, f.k)
    var sol = if (useHeuristic) getHeuristic(g, f).also { println("Heuristic: $it") } else Solution()
    println("Heuristic:".padEnd(paddingRight) + "${"%.1f".format(secondsElapsed())} sec.")
    if (sol.value == f.globalOptimum()) return (sol to secondsElapsed()).also { println("Heuristic was optimal") }

    val (critPartition, x) = getCriticalPartitioning(g)
    println("Biggest crit-set: ${critPartition.subsets.map { it.size }.maxOrNull()!!}")
    trimTwinSets(g, critPartition, x, f.k)

    while (sol.value < f.globalOptimum() && g.vCount >= f.k) {     //main loop

        val startVertex = critPartition.subsets.maxByOrNull { it.size }!!.first()
        val subgraph = OrderedGraph<Int>().apply { addVertex(startVertex) }
        fun numVerticesMissing() = f.k - subgraph.vCount

        val extension = SegmentedList<Int>().apply { this += neighborListOf(g, startVertex) }
        val pointers = mutableListOf(0)

        val visitedTwins = SegmentedList<Int>().apply { repeat(2) { this += HashSet() } }

        val cliqueCompanion = CliqueTracker(f.k).apply { addVertex(startVertex) }

        fun extendable() = ArrayList<Int>().apply {
            for (i in pointers.indices.reversed())
                if (extension.segmentSizes[i] > pointers.last()) add(subgraph.orderedVertices[i])
                else break
        }.filter { subgraph.degreeOf(it) < g.degreeOf(it) }

        fun cliqueJoinRule(): Boolean {
            if (!f.edgeMonotone) return false
            connectVertices(cliqueCompanion, extendable(), cliqueCompanion.cliqueVertices())
            val isApplicable = f.eval(cliqueCompanion) <= sol.value
            disconnectVertices(cliqueCompanion, extendable(), cliqueCompanion.cliqueVertices())
            return isApplicable.also { if (it) cliqueJoinRuleSkips++ }
        }

        while (pointers.isNotEmpty()) {  //##### loops through nodes in the search-tree

            if (pointers.last() >= extension.size || numVerticesMissing() == 0 || (vertexAdditionRule(subgraph, sol, f)) || cliqueJoinRule() || (f.edgeMonotone && universalGraphRule(subgraph, f, sol.value, extendable()))) {
                if (numVerticesMissing() > 0) extension.removeLastSegment()
                val poppedVertex = subgraph.removeLastVertex()

                visitedTwins.removeLastSegment()
                visitedTwins.addToLast(critPartition[poppedVertex])

                cliqueCompanion.removeVertex(poppedVertex)

                pointers.removeAt(pointers.size - 1)

            } else if (extension[pointers.last()] in visitedTwins) {
                pointers[pointers.size - 1]++
                criticalTwinSkips++

            } else {
                if (secondsElapsed() >= timeLimit) return Pair(sol, secondsElapsed())
                if (++searchTreeNodes % 1_000_000 == 0L) println("tree-nodes:".padEnd(paddingRight) + "${searchTreeNodes / 1_000_000} million")

                val nextVertex = extension[pointers.last()]
                if (numVerticesMissing() > 1) extension += neighborListOf(g, nextVertex).filter { it !in extension && it != startVertex }

                visitedTwins += emptyList()

                subgraph.expandSubgraph(g, nextVertex)

                cliqueCompanion.expandSubgraph(g, nextVertex)

                pointers[pointers.size - 1]++
                pointers.add(pointers.last())
            }
            if (numVerticesMissing() == 0 && f.eval(subgraph) > sol.value) sol = Solution(subgraph.copy(), f.eval(subgraph))

        }
        deleteUpdateTwinSet(g, critPartition, startVertex)
    }

    println("Vertex addition:".padEnd(paddingRight) + vertexAdditionRuleSkips)
    println("Clique join rule:".padEnd(paddingRight) + cliqueJoinRuleSkips)
    println("Critical twins:".padEnd(paddingRight) + criticalTwinSkips)
    println("UniversalGraphRule".padEnd(paddingRight) + universalGraphRuleSkips)
    println("Nodes:".padEnd(paddingRight) + searchTreeNodes)
    return Pair(sol, secondsElapsed())
}

fun <V> vertexAdditionRule(curr: Graph<V, DefaultEdge>, currentBestSol: Solution<V>, f: AbstractGraphFunction): Boolean {
    val result = f.eval(curr) + f.completeBound(curr) <= currentBestSol.value
    if (result) vertexAdditionRuleSkips++
    return result
}

fun universalGraphRule(sub: OrderedGraph<Int>, f: AbstractGraphFunction, sol: Int, vList: List<Int> = sub.vertexSet().toList()): Boolean {
    if (f.k - sub.vCount != 1) return false
    var beaten = false
    sub.addVertex(Int.MAX_VALUE)
    for (toggleSet in subset(vList).simple().stream().skip(1)) {    //skips first because it's the empty subset which would disconnect that graph
        disconnectVertices(sub, setOf(Int.MAX_VALUE), vList)
        connectVertices(sub, setOf(Int.MAX_VALUE), toggleSet)
        if (f.eval(sub) > sol) {
            beaten = true
            break
        }
    }

    sub.removeLastVertex()
    return !beaten.also { if (it) universalGraphRuleSkips++ }

}