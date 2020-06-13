package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.createClique
import de.umr.fixcon.graphFunctions.standardFunctions.NegMaxDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NegMaxDegree {
    private val t = Tester(NegMaxDegreeFunction)

    @Nested
    internal inner class infUsAir {
        @Test
        fun f4() = t.test(-2, infUsAir, 4)

        @Test
        fun f5() = t.test(-2, infUsAir, 5)

        @Test
        fun f7() = t.test(-2, infUsAir, 7)

        @Test
        fun f8() = t.test(-2, infUsAir, 8)

        @Test
        fun f9() = t.test(-2, infUsAir, 9)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(-2, bioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(-2, socAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(-2, coPapersCiteseer, 4)

        @Test
        fun f5() = t.test(-2, coPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f6() = t.test(-2, infPower, 6)

        @Test
        fun f7() = t.test(-2, infPower, 7)

        @Test
        fun f12() = t.test(-2, infPower, 12)

        @Test
        fun f17() = t.test(-2, infPower, 17)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f6() = t.test(-2, outDolphins, 6)

        @Test
        fun f8() = t.test(-2, outDolphins, 8)

        @Test
        fun f10() = t.test(-2, outDolphins, 10)

        @Test
        fun f12() = t.test(-2, outDolphins, 12)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(-2, infEuroroad, 4)

        @Test
        fun f8() = t.test(-2, infEuroroad, 8)

        @Test
        fun f12() = t.test(-2, infEuroroad, 12)

        @Test
        fun f16() = t.test(-2, infEuroroad, 16)

        @Test
        fun f18() = t.test(-2, infEuroroad, 18)

        @Test
        fun f19() = t.test(-2, infEuroroad, 19)

        @Test
        fun f20() = t.test(-2, infEuroroad, 20)
    }

    @Nested
    internal inner class clique {

        @Test
        fun f5() = t.test(-2, createClique(5), 3)

        @Test
        fun f10() = t.test(-7, createClique(10), 8)

        @Test
        fun f20() = t.test(-5, createClique(20), 6)

        @Test
        fun f35() = t.test(-3, createClique(30), 4)
    }
}