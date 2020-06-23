package de.umr.fixcon

import de.umr.GraphFile.CustomTree
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.io.graphFromFile
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LocalSearchKtTest {

    @Nested
    internal inner class localSearchOneStep {
        @Test
        fun localSearchOneStep() {
        }
    }


    @Nested
    internal inner class nonCutPoints {

        @Test
        fun path() = assertEquals(setOf(0, 4), nonCutPoints(createPath(5)))

        @Test
        fun star() = assertEquals(setOf(1,2,3,4), nonCutPoints(createStar(5)))

        @Test
        fun customTree() = assertEquals(setOf(8,13,16,17,18,19,20), nonCutPoints(graphFromFile(CustomTree)))
    }
}