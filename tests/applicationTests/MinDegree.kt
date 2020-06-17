package applicationTests

import applicationTests.util.Tester
import de.umr.GraphFile.*
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private class MinDegree {
    private val t = Tester(MinDegreeFunction)

    @Nested
    internal inner class infUsAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = t.test(3, g, 4)

        @Test
        fun f5() = t.test(4, g, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(3, BioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(3, SocAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = t.test(3, g, 4)

        @Test
        fun f5() = t.test(4, g, 5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f4() = t.test(3, g, 4)

        @Test
        fun f5() = t.test(4, g, 5)

        @Test
        fun f6() = t.test(5, g, 6)

        @Test
        fun f7() = t.test(3, g, 7)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f6() = t.test(4, g, 6)

        @Test
        fun f7() = t.test(4, g, 7)

        @Test
        fun f8() = t.test(3, g, 8)

        @Test
        fun f9() = t.test(4, g, 9)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f4() = t.test(2, g, 4)

        @Test
        fun f5() = t.test(2, g, 5)

        @Test
        fun f6() = t.test(2, g, 6)

        @Test
        fun f7() = t.test(2, g, 7)

        @Test
        fun f8() = t.test(2, g, 8)

        @Test
        fun f9() = t.test(2, g, 9)
    }
}