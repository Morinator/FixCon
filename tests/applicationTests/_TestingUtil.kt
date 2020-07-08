package applicationTests

import de.umr.core.GraphFile
import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.core.extensions.vertexCount
import de.umr.core.io.graphFromFile
import de.umr.fixcon.Problem
import de.umr.fixcon.graphFunctions.AbstractGraphFunction
import de.umr.fixcon.solve
import org.jgrapht.alg.connectivity.ConnectivityInspector
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class _TestingUtil(private val fu: AbstractGraphFunction) {

    fun <V> test(correctValue: Int, graph: VertexOrderedGraph<V>, k: Int) {
        fu.k = k
        val result = solve(Problem(graph, fu))

        assertEquals(correctValue, result.value)

        assertEquals(fu.eval(result.subgraph), result.value)
        assertTrue(result.value <= fu.globalOptimum())

        assertTrue(ConnectivityInspector(result.subgraph).isConnected)
        assertEquals(k, result.subgraph.vertexCount)
    }

    fun test(correctValue: Int, path: GraphFile, k: Int) = test(correctValue, graphFromFile(path), k)

    fun <V> testCond(condition: (Int) -> Boolean, graph: VertexOrderedGraph<V>, k: Int) {
        fu.k = k
        val result = solve(Problem(graph, fu))

        assertTrue(condition(result.value))

        assertEquals(fu.eval(result.subgraph), result.value)
        assertTrue(result.value <= fu.globalOptimum())

        assertTrue(ConnectivityInspector(result.subgraph).isConnected)
        assertEquals(k, result.subgraph.vertexCount)
    }
}