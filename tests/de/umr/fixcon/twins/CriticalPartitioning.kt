package de.umr.fixcon.twins

import de.umr.core.createClique
import de.umr.core.createStar
import de.umr.core.dataStructures.SetPartitioning
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CriticalPartitioning {

    @Nested
    internal inner class CritCliqueMerge {

        @Test
        fun clique7() {
            val p = SetPartitioning<Int>().apply { (0 until 7).forEach { this += it } }
            val g = createClique(7)
            critCliqueMerge(g, p, (0 until 7).toList())
            assertEquals((0 until 7).toSet(), p[0])
        }

        @Test
        fun star13() {
            val p = SetPartitioning<Int>().apply { (0 until 13).forEach { this += it } }
            val g = createStar(13)
            critCliqueMerge(g, p, (0 until 13).toList())
            (0 until 13).forEach { assertEquals(setOf(it), p[it]) }     //nothing could be merged
        }

        @Test
        fun clique10() {
            val p = SetPartitioning<Int>().apply { (0 .. 3).forEach { this += it }; this += setOf(4, 5, 6, 7, 8, 9) }
            val g = createClique(10)
            critCliqueMerge(g, p, (0 until 10).toList())
            assertEquals((0 until 10).toSet(), p[9])
        }
    }


}