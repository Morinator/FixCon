package de.umr

import de.umr.core.dataStructures.edgeCount
import de.umr.core.dataStructures.vertexCount
import de.umr.core.graphFromFile
import de.umr.fixcon.Instance
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve
import java.io.File
import java.lang.System.currentTimeMillis
import java.nio.file.Files.createDirectories
import java.nio.file.Paths


/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 * args[3] == time limit in seconds
 */

//##############################
//      Global Settings
//##############################

const val paddingRight = 30

const val defaultEdgeWeight = 1.0

const val useHeuristic = false




var searchTreeNodes: Long = 0

fun main(args: Array<String>) {
    val graph = graphFromFile(args[0])
    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }

    val startTime = currentTimeMillis()
    val result = solve(Instance(graph, graphFunctionByID(funcID, k, funcParams)))

    createDirectories(Paths.get("results"))
    File("results/${File(args[0]).name}.$k.$funcID.fixcon").writeText("${funcID};${funcParams.joinToString(",")}".padStart(15) + File(args[0]).name.padStart(40) + graph.vertexCount.toString().padStart(7) + graph.edgeCount.toString().padStart(9) + k.toString().padStart(4) + ((currentTimeMillis() - startTime) / 1000.0).toString().padStart(14) + result.value.toString().padStart(6) + ("Nodes: $searchTreeNodes").padStart(20) + "     " + result.subgraph.vertexSet().toString() + "\n")
}