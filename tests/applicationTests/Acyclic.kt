package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.fixcon.graphFunctions.standardFunctions.AcyclicFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Acyclic {
    private val t = Tester(AcyclicFunction)

    @Nested
    internal inner class infPower {
        @Test
        fun f9() = t.test(1, infPower, 9)

        @Test
        fun f14() = t.test(1, infPower, 14)

        @Test
        fun f18() = t.test(1, infPower, 18)

        @Test
        fun f20() = t.test(1, infPower, 20)
    }

    @Nested
    internal inner class usAir {
        @Test
        fun f4() = t.test(1, infUsAir, 4)

        @Test
        fun f8() = t.test(1, infUsAir, 8)

        @Test
        fun f9() = t.test(1, infUsAir, 9)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(1, coPapersCiteseer, 4)

        @Test
        fun f7() = t.test(1, coPapersCiteseer, 7)

        @Test
        fun f9() = t.test(1, coPapersCiteseer, 9)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(1, infEuroroad, 4)

        @Test
        fun f7() = t.test(1, infEuroroad, 7)

        @Test
        fun f9() = t.test(1, infEuroroad, 9)

        @Test
        fun f13() = t.test(1, infEuroroad, 13)

        @Test
        fun f17() = t.test(1, infEuroroad, 17)

        @Test
        fun f20() = t.test(1, infEuroroad, 20)
    }

    @Nested
    internal inner class openFlights {
        @Test
        fun f4() = t.test(1, infOpenFlights, 4)

        @Test
        fun f7() = t.test(1, infOpenFlights, 7)

        @Test
        fun f8() = t.test(1, infOpenFlights, 8)
    }

    @Nested
    internal inner class clique {
        @Test
        fun f3_2() = t.test(1, createClique(3), 2)

        @Test
        fun f3_3() = t.test(0, createClique(3), 3)

        @Test
        fun f23_5() = t.test(0, createClique(23), 5)

        @Test
        fun f10_9() = t.test(0, createClique(10), 9)

        @Test
        fun f50_20() = t.test(0, createClique(50), 20)
    }

    @Nested
    internal inner class path {
        @Test
        fun f_10_5() = t.test(1, createPath(10), 5)

        @Test
        fun f_3_3() = t.test(1, createPath(3), 3)

        @Test
        fun f_23_5() = t.test(1, createPath(23), 5)

        @Test
        fun f_10_9() = t.test(1, createPath(10), 9)

        @Test
        fun f_50_20() = t.test(1, createPath(50), 20)
    }

    @Nested
    internal inner class brightkite {
        @Test
        fun f5() = t.test(1, socBrightkite, 5)

        @Test
        fun f10() = t.test(1, socBrightkite, 10)

        @Test
        fun f12() = t.test(1, socBrightkite, 12)

        @Test
        fun f14() = t.test(1, socBrightkite, 14)
    }

    @Nested
    internal inner class caSandiAuths {
        @Test
        fun f2() = t.test(1, caSandiAuths, 2, weighted = true)

        @Test
        fun f10() = t.test(1, caSandiAuths, 10, weighted = true)

        @Test
        fun f12() = t.test(1, caSandiAuths, 12, weighted = true)

        @Test
        fun f14() = t.test(1, caSandiAuths, 14, weighted = true)

        @Test
        fun f15() = t.test(1, caSandiAuths, 15, weighted = true)
    }
}