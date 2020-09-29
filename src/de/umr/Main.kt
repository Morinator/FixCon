package de.umr

import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve
import java.io.File
import java.nio.file.Files.createDirectories
import java.nio.file.Paths

/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 * args[3] == time limit in seconds*/

//##### Global Settings
const val paddingRight = 30
const val defaultEdgeWeight = 1.0
const val useHeuristic = false

//##### Global state variables
var searchTreeNodes: Long = 0

fun main(args: Array<String>) {

    //##### Reading of Command-Line arguments
    val graph = graphFromFile(file = args[0]).also { g -> require(g.vertexSet().all { v -> v >= 0 }) }
    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }
    val vertexCount = graph.vertexCount
    val edgeCount = graph.edgeCount

    //##### run algorithm
    val (solution, usedTime) = solve(graph, f = graphFunctionByID(funcID, k, funcParams), timeLimit = args[3].toInt())

    //##### logging results
    createDirectories(Paths.get("results"))
    File("results/${File(args[0]).name}.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + File(args[0]).name.padStart(40) + vertexCount.toString().padStart(7) + edgeCount.toString().padStart(9) + k.toString().padStart(4) + usedTime.toString().padStart(14) + solution.value.toString().padStart(6) + ("Nodes: $searchTreeNodes").padStart(20) + "     " + solution.subgraph.vertexSet().toString() + "\n")
}