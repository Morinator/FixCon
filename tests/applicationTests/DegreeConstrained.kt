package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.fixcon.graphFunctions.standardFunctions.DegreeConstrainedFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DegreeConstrained {

    /** Degree needs to be between 3 and 5 (both inclusive) for all tests.*/
    private val t = Tester(DegreeConstrainedFunction, listOf(3, 5))

    @Nested
    internal inner class usAir {
        @Test
        fun f4() = t.test(1, InfUsAir, 4)

        @Test
        fun f5() = t.test(1, InfUsAir, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(1, InfPower, 4)

        @Test
        fun f5() = t.test(1, InfPower, 5)

        @Test
        fun f6() = t.test(1, InfPower, 6)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(0, InfEuroRoad, 4)

        @Test
        fun f5() = t.test(0, InfEuroRoad, 5)

        @Test
        fun f6() = t.test(0, InfEuroRoad, 6)

        @Test
        fun f7() = t.test(0, InfEuroRoad, 7)

        @Test
        fun f8() = t.test(0, InfEuroRoad, 8)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(1, CoPapersCiteseer, 4)

        @Test
        fun f5() = t.test(1, CoPapersCiteseer, 5)

        @Test
        fun f6() = t.test(1, CoPapersCiteseer, 6)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(1, BioDmela, 4)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f4() = t.test(1, OutDolphins, 4)

        @Test
        fun f5() = t.test(1, OutDolphins, 5)

        @Test
        fun f6() = t.test(1, OutDolphins, 6)

        @Test
        fun f7() = t.test(1, OutDolphins, 7)

        @Test
        fun f8() = t.test(1, OutDolphins, 8)

        @Test
        fun f9() = t.test(1, OutDolphins, 9)

        @Test
        fun f10() = t.test(1, OutDolphins, 10)
    }

    @Nested
    internal inner class brightkite {
        @Test
        fun f4() = t.test(1, SocBrightkite, 4)

        @Test
        fun f5() = t.test(1, SocBrightkite, 5)
    }

    @Nested
    internal inner class clique {
        @Test
        fun f3() = t.test(0, createClique(10), 3)

        @Test
        fun f4() = t.test(1, createClique(10), 4)

        @Test
        fun f5() = t.test(1, createClique(10), 5)

        @Test
        fun f6() = t.test(1, createClique(10), 6)

        @Test
        fun f7() = t.test(0, createClique(10), 7)

        @Test
        fun f7_() = t.test(0, createClique(20), 7)
    }

    @Nested
    internal inner class path {
        @Test
        fun f5() = t.test(0, createPath(10), 5)

        @Test
        fun f6() = t.test(0, createPath(100), 6)

        @Test
        fun f20() = t.test(0, createPath(100), 20)

        @Test
        fun f50() = t.test(0, createPath(500), 50)

        @Test
        fun f100() = t.test(0, createPath(100), 100)
    }
}