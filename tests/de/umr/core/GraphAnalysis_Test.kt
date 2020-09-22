package de.umr.core

import de.umr.core.dataStructures.GraphFile.BioYeast
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GraphAnalysis_Test {

    private val delta = 0.0001

    @Test
    fun degreeShareMap_test() {
        val m = degreeShareMap(createStar(4))
        assertEquals(3.0 / 4, m[1] ?: error(""), delta)
        assertEquals(1.0 / 4, m[3] ?: error(""), delta)
    }

    @Test   //checks if no error is thrown on execution
    fun testPrintFullAnalysis_test() {
        printFullAnalysis(graphFromFile(BioYeast))
    }
}