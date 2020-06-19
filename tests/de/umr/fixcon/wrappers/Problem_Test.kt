//package de.umr.fixcon.wrappers
//
//import de.umr.core.dataStructures.VertexOrderedGraph
//import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
//import org.jgrapht.Graphs.addEdgeWithVertices
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//
//internal class Problem_Test {
//    @Test
//    fun constructor_test() {
//        val g = VertexOrderedGraph<Int>()
//        addEdgeWithVertices(g, 1, 2)
//        var p = Problem(g, 5, EdgeCountFunction())
//
//        assertEquals(2, p.g.vertexSet().size)
//        assertEquals(5, p.k)
//
//        p = Problem(g, 3, EdgeCountFunction())
//        assertEquals(3, p.k)
//    }
//}