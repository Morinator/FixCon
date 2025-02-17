package applicationTests

import de.umr.core.GraphFile.*
import de.umr.core.createClique
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.EdgeCountFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private class EdgeCount {
    private val t = _TestingUtil(EdgeCountFunction(1234))

    @Nested
    inner class usAir {
        private val g = graphFromFile(InfUsAir)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)
    }

    @Nested
    inner class dmela {
        private val g = graphFromFile(BioDmela)

        @Test
        fun f4() = t.test(6, g, 4)
    }

    @Nested
    inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)
    }

    @Nested
    inner class infPower {
        private val g = graphFromFile(InfPower)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)

        @Test
        fun f6() = t.test(15, g, 6)
    }

    @Nested
    inner class dolphins {
        private val g = graphFromFile(OutDolphins)

        @Test
        fun f7() = t.test(17, g, 7)

        @Test
        fun f8() = t.test(20, g, 8)
    }

    @Nested
    inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)

        @Test
        fun f6() = t.test(8, g, 6)

        @Test
        fun f7() = t.test(9, g, 7)

        @Test
        fun f8() = t.test(11, g, 8)

        @Test
        fun f9() = t.test(12, g, 9)

        @Test
        fun f10() = t.test(14, g, 10)
    }

    @Nested
    inner class MouseRetina {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f6() = t.test(15, g, 6)

        @Test
        fun f8() = t.test(28, g, 8)

        @Test
        fun f9() = t.test(36, g, 9)

        @Test
        fun f10() = t.test(45, g, 10)

        @Test
        fun f11() = t.test(55, g, 11)

        @Test
        fun f13() = t.test(78, g, 13)

        @Test
        fun f14() = t.test(91, g, 14)
    }

    @Nested
    inner class Heart2 {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)

        @Test
        fun f6() = t.test(15, g, 6)

        @Test
        fun f7() = t.test(21, g, 7)


        @Test
        fun f9() = t.test(36, g, 9)

        @Test
        fun f10() = t.test(45, g, 10)

        @Test
        fun f12() = t.test(66, g, 12)

        @Test
        fun f13() = t.test(78, g, 13)

        @Test
        fun f14() = t.test(91, g, 14)

        @Test
        fun f15() = t.test(105, g, 15)
    }

    @Nested
    inner class BioYeast {
        private val g = graphFromFile(BioYeast)

        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)

        @Test
        fun f6() = t.test(15, g, 6)
    }

    @Nested
    inner class WeirdGraphs {
        val g = createClique(3).apply { (3..100_000).forEach { addVertex(it) } }

        @Test
        fun islands() = t.test(3, g, 3)
    }
}