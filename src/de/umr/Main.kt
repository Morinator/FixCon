package de.umr

import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.graphFunctionByID
import de.umr.fixcon.solve

/**args[0] == k
 * args[1] == function ID
 * args[2] == parameters for function, separated by ",". The parameters 3 and 5 for are "3,5" for example.
 * args[3] == File-Path for the graph.
 * args[4] == lines to skip in the graph-file (for example due to comments in the file).
 */
fun main(args: Array<String>) {
    val fu = graphFunctionByID(args[1].toInt(), args[0].toInt(), args[2].split(",").map { it.toInt() }.toList())
    val g = graphFromFile(args[3], skipLines = args[4].toInt())
    println(solve(Problem(g, fu)))
}