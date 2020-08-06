package de.umr

import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve

/**args[0] == File-Path for the graph.
 * args[1] == k
 * args[2] == functionID, param1, param2, ..., paramN-1, paramN
 */
fun main(args: Array<String>) {
    val k = args[1].toInt()
    val funcID = args[2].split(",").first().toInt()
    val funcParams = args[2].split(",").drop(1).map { it.toInt() }

    val fu = graphFunctionByID(funcID, k, funcParams)
    println(solve(Problem(graphFromFile(args[0]), fu)))
}