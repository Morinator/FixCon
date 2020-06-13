package applicationTests.util

import de.umr.FilePaths
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.graphFromFile
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import kotlin.test.assertEquals

class Tester(private val fu: GraphFunction, private val args: List<Int> = emptyList()) {

    fun test(correctValue: Int, path: String, targetSize: Int, weighted : Boolean = false) {
        val result = Solver(CFCO_Problem(graphFromFile(path, weighted), targetSize, fu, args)).solve().value
        assertEquals(correctValue, result)
    }

    fun <V> test(correctValue: Int, graph: VertexOrderedGraph<V>, targetSize: Int) {
        val result = Solver(CFCO_Problem(graph, targetSize, fu, args)).solve().value
        assertEquals(correctValue, result)
    }

    fun test(correctValue: Int, path: FilePaths, targetSize: Int, weighted : Boolean = false) {
        val result = Solver(CFCO_Problem(graphFromFile(path.path, weighted), targetSize, fu, args)).solve().value
        assertEquals(correctValue, result)
    }
}