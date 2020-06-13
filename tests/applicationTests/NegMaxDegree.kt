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
        fun f4() = t.test(-2, InfUsAir, 4)

        @Test
        fun f5() = t.test(-2, InfUsAir, 5)

        @Test
        fun f7() = t.test(-2, InfUsAir, 7)

        @Test
        fun f8() = t.test(-2, InfUsAir, 8)

        @Test
        fun f9() = t.test(-2, InfUsAir, 9)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(-2, BioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(-2, SocAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(-2, CoPapersCiteseer, 4)

        @Test
        fun f5() = t.test(-2, CoPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f6() = t.test(-2, InfPower, 6)

        @Test
        fun f7() = t.test(-2, InfPower, 7)

        @Test
        fun f12() = t.test(-2, InfPower, 12)

        @Test
        fun f17() = t.test(-2, InfPower, 17)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f6() = t.test(-2, OutDolphins, 6)

        @Test
        fun f8() = t.test(-2, OutDolphins, 8)

        @Test
        fun f10() = t.test(-2, OutDolphins, 10)

        @Test
        fun f12() = t.test(-2, OutDolphins, 12)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(-2, InfEuroRoad, 4)

        @Test
        fun f8() = t.test(-2, InfEuroRoad, 8)

        @Test
        fun f12() = t.test(-2, InfEuroRoad, 12)

        @Test
        fun f16() = t.test(-2, InfEuroRoad, 16)

        @Test
        fun f18() = t.test(-2, InfEuroRoad, 18)

        @Test
        fun f19() = t.test(-2, InfEuroRoad, 19)

        @Test
        fun f20() = t.test(-2, InfEuroRoad, 20)
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