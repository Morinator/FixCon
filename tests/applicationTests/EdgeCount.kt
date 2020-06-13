package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


private class EdgeCount {
    private val t = Tester(EdgeCountFunction)

    @Nested
    internal inner class usAir {
        @Test
        fun f4() = t.test(6, InfUsAir, 4)

        @Test
        fun f5() = t.test(10, InfUsAir, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(6, BioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(6, SocAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(6, CoPapersCiteseer, 4)

        @Test
        fun f5() = t.test(10, CoPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(6, InfPower, 4)

        @Test
        fun f5() = t.test(10, InfPower, 5)

        @Test
        fun f6() = t.test(15, InfPower, 6)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f7() = t.test(17, OutDolphins, 7)

        @Test
        fun f8() = t.test(20, OutDolphins, 8)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f6() = t.test(8, InfEuroRoad, 6)

        @Test
        fun f7() = t.test(9, InfEuroRoad, 7)

        @Test
        fun f8() = t.test(11, InfEuroRoad, 8)
    }
}