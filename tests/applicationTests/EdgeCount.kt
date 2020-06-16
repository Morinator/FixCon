package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


private class EdgeCount {
    private val t = Tester(EdgeCountFunction)

    @Nested
    internal inner class usAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)
    }

    @Nested
    internal inner class dmela {
        private val g = graphFromFile(BioDmela)
        @Test
        fun f4() = t.test(6, g, 4)
    }

    @Nested
    internal inner class socAdvogato {
        private val g = graphFromFile(SocAdvogato)
        @Test
        fun f4() = t.test(6, g, 4)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f4() = t.test(6, g, 4)

        @Test
        fun f5() = t.test(10, g, 5)

        @Test
        fun f6() = t.test(15, g, 6)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f7() = t.test(17, g, 7)

        @Test
        fun f8() = t.test(20, g, 8)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f6() = t.test(8, g, 6)

        @Test
        fun f7() = t.test(9, g, 7)

        @Test
        fun f8() = t.test(11, g, 8)
    }
}