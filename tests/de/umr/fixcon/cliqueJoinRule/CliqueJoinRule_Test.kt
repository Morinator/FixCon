package de.umr.fixcon.cliqueJoinRule

import de.umr.core.createCircle
import de.umr.core.createPath
import de.umr.core.createStar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CliqueJoinRule_Test {

    @Nested
    internal inner class UnusedVertexSet {

        @Test
        fun size0() = assertEquals(setOf<Int>(), unusedVertexSet(createPath(4), 0))

        @Test
        fun size3() = assertEquals(setOf(5, 6, 7), unusedVertexSet(createCircle(5), 3))

        @Test
        fun size5() = assertEquals(setOf(13, 14, 15, 16, 17), unusedVertexSet(createStar(13), 5))
    }


    @Test
    fun cliqueJoinRule() {

    }
}