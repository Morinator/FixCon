package de.umr.fixcon.graphFunctions

import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.core.dataStructures.GraphFile
import de.umr.core.graphFromFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AcyclicFunction_Test {
    private val f = AcyclicFunction()

    @Test
    fun clique() = assertEquals(0, f.eval(createClique(20)))

    @Test
    fun path() = assertEquals(1, f.eval(createPath(43)))

    @Test
    fun circle() = assertEquals(0, f.eval(createCircle(37)))

    @Test
    fun customTree() = assertEquals(1, f.eval(graphFromFile(GraphFile.CustomTree)))

    @Test
    fun star() = assertEquals(1, f.eval(createStar(46)))
}