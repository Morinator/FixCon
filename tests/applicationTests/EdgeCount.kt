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
        fun f4() = t.test(6, infUsAir, 4)

        @Test
        fun f5() = t.test(10, infUsAir, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(6, bioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(6, socAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(6, coPapersCiteseer, 4)

        @Test
        fun f5() = t.test(10, coPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(6, infPower, 4)

        @Test
        fun f5() = t.test(10, infPower, 5)

        @Test
        fun f6() = t.test(15, infPower, 6)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f7() = t.test(17, outDolphins, 7)

        @Test
        fun f8() = t.test(20, outDolphins, 8)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f6() = t.test(8, infEuroroad, 6)

        @Test
        fun f7() = t.test(9, infEuroroad, 7)

        @Test
        fun f8() = t.test(11, infEuroroad, 8)
    }
}