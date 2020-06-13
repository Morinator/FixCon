package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private class MinDegree {
    private val t = Tester(MinDegreeFunction)

    @Nested
    internal inner class infUsAir {
        @Test
        fun f4() = t.test(3, InfUsAir, 4)

        @Test
        fun f5() = t.test(4, InfUsAir, 5)
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
        @Test
        fun f4() = t.test(3, CoPapersCiteseer, 4)

        @Test
        fun f5() = t.test(4, CoPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(3, InfPower, 4)

        @Test
        fun f5() = t.test(4, InfPower, 5)

        @Test
        fun f6() = t.test(5, InfPower, 6)

        @Test
        fun f7() = t.test(3, InfPower, 7)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f6() = t.test(4, OutDolphins, 6)

        @Test
        fun f7() = t.test(4, OutDolphins, 7)

        @Test
        fun f8() = t.test(3, OutDolphins, 8)

        @Test
        fun f9() = t.test(4, OutDolphins, 9)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(2, InfEuroRoad, 4)

        @Test
        fun f5() = t.test(2, InfEuroRoad, 5)

        @Test
        fun f6() = t.test(2, InfEuroRoad, 6)

        @Test
        fun f7() = t.test(2, InfEuroRoad, 7)

        @Test
        fun f8() = t.test(2, InfEuroRoad, 8)

        @Test
        fun f9() = t.test(2, InfEuroRoad, 9)
    }
}