package unitTests.core

import de.umr.FilePaths
import de.umr.core.*
import de.umr.core.dataStructures.VertexOrderedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.math.PI

class JGraphT_Extensions_Test {

    @Test
    fun vertexCount_test() = assertEquals(17, graphFromFile(FilePaths.sample).vertexCount)     //17 is manually counted

    @Test
    fun edgeCount_test() {
        assertEquals(1, createClique(2).edgeCount)
        assertEquals(10, createClique(5).edgeCount)
        assertEquals(45, createClique(10).edgeCount)

        assertEquals(1, createCircle(2).edgeCount)
        assertEquals(5, createCircle(5).edgeCount)
        assertEquals(10, createCircle(10).edgeCount)

        assertEquals(1, createPath(2).edgeCount)
        assertEquals(4, createPath(5).edgeCount)
        assertEquals(9, createPath(10).edgeCount)

        assertEquals(1, createStar(2).edgeCount)
        assertEquals(4, createStar(5).edgeCount)
        assertEquals(9, createStar(10).edgeCount)
    }

    @Test
    fun weightOfEdge_test() {
        val g = VertexOrderedGraph(listOf(Triple(1, 2, 3.4), Triple(2, 4, 2.3)))
        kotlin.test.assertEquals(3.4, g.weightOfEdge(1, 2))
        kotlin.test.assertEquals(2.3, g.weightOfEdge(2, 4))
        assertThrows(IllegalStateException::class.java) { g.weightOfEdge(1, 4) }
        assertThrows(IllegalStateException::class.java) { g.weightOfEdge(0, 0) }
    }

    @Test
    fun addWeightedEdge_test() {
        val g = VertexOrderedGraph<Int>()
        g.addWeightedEdge(1, 2, PI)
        g.addWeightedEdge(2, 4, -6.4)
        kotlin.test.assertTrue(g.containsEdge(1, 2))
        kotlin.test.assertTrue(g.vertexCount == 3)
        kotlin.test.assertEquals(-6.4, g.weightOfEdge(2, 4))
    }

    @Test
    fun openNB_test() {
        assertEquals(setOf(1, 2, 3), createClique(4).openNB(0))
        assertEquals(setOf(4, 6), createPath(10).openNB(5))
        assertEquals(setOf(4, 6), createCircle(10).openNB(5))
        assertEquals(setOf(1, 9), createCircle(10).openNB(0))
        assertEquals(setOf(1, 2, 3, 4), createStar(5).openNB(0))
        assertEquals(setOf(0), createStar(5).openNB(1))
    }

    @Test
    fun closedNB_test() {
        assertEquals(setOf(0, 1, 2, 3), createClique(4).closedNB(0))
        assertEquals(setOf(4, 5, 6), createPath(10).closedNB(5))
        assertEquals(setOf(4, 5, 6), createCircle(10).closedNB(5))
        assertEquals(setOf(1, 0, 9), createCircle(10).closedNB(0))
        assertEquals(setOf(0, 1, 2, 3, 4), createStar(5).closedNB(0))
        assertEquals(setOf(0, 1), createStar(5).closedNB(1))
    }
}