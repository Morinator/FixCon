package de.umr.fixcon.twins

import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createStar
import de.umr.core.dataStructures.Partitioning
import de.umr.core.fromUnweightedEdges
import de.umr.fixcon.critCliqueMerge
import de.umr.fixcon.critISMerge
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CriticalPartitioning {

    @Nested
    internal inner class CritCliqueMerge {

        @Test
        fun clique7() {
            val p = Partitioning<Int>().apply { (0 until 7).forEach { this += it } }
            val g = createClique(7)
            critCliqueMerge(g, p, (0 until 7).toList())
            assertEquals((0 until 7).toSet(), p[0])
        }

        @Test
        fun star13() {
            val p = Partitioning<Int>().apply { (0 until 13).forEach { this += it } }
            val g = createStar(13)
            critCliqueMerge(g, p, (0 until 13).toList())
            (0 until 13).forEach { assertEquals(setOf(it), p[it]) }     //nothing could be merged
        }

        @Test
        fun clique10() {
            val p = Partitioning<Int>().apply { (0..3).forEach { this += it }; add(setOf(4, 5, 6, 7, 8, 9)) }
            val g = createClique(10)
            critCliqueMerge(g, p, (0 until 10).toList())
            assertEquals((0 until 10).toSet(), p[9])
        }

        @Test
        fun twoPaths2() {
            val p = Partitioning<Int>().apply { (0..3).forEach { this += it } }
            val g = fromUnweightedEdges(listOf(0 to 1, 2 to 3))
            critCliqueMerge(g, p, (0..3).toList())
            assertEquals(setOf(0, 1), p[0])
            assertEquals(setOf(2, 3), p[2])
        }
    }


    @Nested
    internal inner class CritISMerge {

        @Test
        fun clique7() {
            val p = Partitioning<Int>().apply { (0 until 7).forEach { this += it } }
            val g = createClique(7)
            critISMerge(g, p, (0 until 7).toList())
            (0 until 7).forEach { assertEquals(setOf(it), p[it]) }     //nothing could be merged
        }

        @Test
        fun star13() {
            val p = Partitioning<Int>().apply { (0 until 13).forEach { this += it } }
            val g = createStar(13)
            critISMerge(g, p, (0 until 13).toList())
            assertEquals((1 until 13).toSet(), p[1])
        }

        @Test
        fun circle4() {
            val p = Partitioning<Int>().apply { (0 until 4).forEach { this += it } }
            val g = createCircle(4)
            critISMerge(g, p, (0 until 4).toList())
            assertEquals(setOf(0, 2), p[0])
            assertEquals(setOf(1, 3), p[1])
        }

    }
}