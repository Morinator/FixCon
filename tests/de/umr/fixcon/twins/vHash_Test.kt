package de.umr.fixcon.twins

import de.umr.core.dataStructures.GraphFile.BioYeast
import de.umr.core.dataStructures.GraphFile.Celegans
import de.umr.core.dataStructures.unorderedPairs
import de.umr.core.extensions.closedNB
import de.umr.core.io.graphFromFile
import org.jgrapht.Graph
import org.jgrapht.Graphs.neighborSetOf
import org.jgrapht.graph.DefaultEdge
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class vHash_Test {

    private fun <V> testHelper(g: Graph<V, DefaultEdge>, nbSelector: (V) -> Set<V>, hashFu: (V) -> List<Int>) {
        for ((v1, v2) in unorderedPairs(g.vertexSet()))
            if (nbSelector(v1) == nbSelector(v2)) assertEquals(hashFu(v1), hashFu(v2))

    }

    @Nested
    internal inner class vHashOpen {

        private fun <V> hashOpenNBTest(g: Graph<V, DefaultEdge>) = testHelper(g, { neighborSetOf(g, it) }, { vHashOpen(g, it) })

        @Test
        fun celegans() = hashOpenNBTest(graphFromFile(Celegans))

        @Test
        fun bioYeast() = hashOpenNBTest(graphFromFile(BioYeast))
    }

    @Nested
    internal inner class vHashClosed {

        private fun <V> hashClosedNBTest(g: Graph<V, DefaultEdge>) = testHelper(g, { g.closedNB(it) }, { vHashClosed(g, it) })

        @Test
        fun celegans() = hashClosedNBTest(graphFromFile(Celegans))

        @Test
        fun bioYeast() = hashClosedNBTest(graphFromFile(BioYeast))
    }

}