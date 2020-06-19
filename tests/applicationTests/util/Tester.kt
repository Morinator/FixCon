package applicationTests.util

import de.umr.GraphFile
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.io.graphFromFile
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.wrappers.Problem
import kotlin.test.assertEquals

class Tester(private val fu: GraphFunction) {

    fun <V> test(correctValue: Int, graph: VertexOrderedGraph<V>, k: Int) {
        fu.k = k
        val result = Solver(Problem(graph, fu)).solve().value
        assertEquals(correctValue, result)
    }

    fun test(correctValue: Int, path: GraphFile, k: Int) {
        fu.k = k
        val result = Solver(Problem(graphFromFile(path), fu)).solve().value
        assertEquals(correctValue, result)
    }
}