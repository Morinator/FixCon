package applicationTests

import applicationTests.util.Tester
import de.umr.core.GraphFile.*
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.DegreeConstrainedFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DegreeConstrained {

    /** Degree needs to be between 3 and 5 (both inclusive) for all tests.*/
    private val t = Tester(DegreeConstrainedFunction(listOf(3, 5), 1234))

    @Nested
    internal inner class usAir {
        private val g = graphFromFile(InfUsAir)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)

        @Test
        fun f6() = t.test(0, g, 6)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)

        @Test
        fun f4() = t.testCond({ it < 0 }, g, 4)

        @Test
        fun f5() = t.testCond({ it < 0 }, g, 5)

        @Test
        fun f6() = t.testCond({ it < 0 }, g, 6)

        @Test
        fun f7() = t.testCond({ it < 0 }, g, 7)

        @Test
        fun f8() = t.testCond({ it < 0 }, g, 8)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)

        @Test
        fun f6() = t.test(0, g, 6)
    }

    @Nested
    internal inner class dmela {
        private val g = graphFromFile(BioDmela)

        @Test
        fun f4() = t.test(0, g, 4)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)

        @Test
        fun f6() = t.test(0, g, 6)

        @Test
        fun f7() = t.test(0, g, 7)

        @Test
        fun f8() = t.test(0, g, 8)

        @Test
        fun f9() = t.test(0, g, 9)

        @Test
        fun f10() = t.test(0, g, 10)
    }

    @Nested
    internal inner class brightkite {
        private val g = graphFromFile(SocBrightkite)

        @Test
        fun f4() = t.test(0, g, 4)
    }

    @Nested
    internal inner class clique {
        @Test
        fun f3() = t.testCond({ it < 0 }, createClique(10), 3)

        @Test
        fun f4() = t.test(0, createClique(10), 4)

        @Test
        fun f5() = t.test(0, createClique(10), 5)

        @Test
        fun f6() = t.test(0, createClique(10), 6)

        @Test
        fun f7() = t.testCond({ it < 0 }, createClique(10), 7)

        @Test
        fun f7_() = t.testCond({ it < 0 }, createClique(20), 8)
    }

    @Nested
    internal inner class path {
        @Test
        fun f5() = t.testCond({ it < 0 }, createPath(10), 5)

        @Test
        fun f6() = t.testCond({ it < 0 }, createPath(100), 6)

        @Test
        fun f20() = t.testCond({ it < 0 }, createPath(100), 20)

        @Test
        fun f50() = t.testCond({ it < 0 }, createPath(500), 50)

        @Test
        fun f100() = t.testCond({ it < 0 }, createPath(100), 100)
    }

    @Nested
    internal inner class MouseRetina {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)

        @Test
        fun f6() = t.test(0, g, 6)

        @Test
        fun f7() = t.test(0, g, 7)
    }

    @Nested
    internal inner class Heart2 {
        private val g = graphFromFile(Heart2)

        @Test
        fun f4() = t.test(0, g, 4)

        @Test
        fun f5() = t.test(0, g, 5)

        @Test
        fun f6() = t.test(0, g, 6)
    }

    @Nested
    internal inner class MorenoZebra {
        private val g = graphFromFile(MorenoZebra)

        @Test
        fun f6() = t.test(0, g, 6)

        @Test
        fun f7() = t.test(0, g, 7)

        @Test
        fun f8() = t.test(0, g, 8)

        @Test
        fun f9() = t.test(0, g, 9)

        @Test
        fun f10() = t.test(0, g, 10)

        @Test
        fun f11() = t.test(0, g, 11)

        @Test
        fun f12() = t.test(0, g, 12)

        @Test
        fun f13() = t.test(0, g, 13)

        @Test
        fun f14() = t.testCond({it < 0}, g, 14)

        @Test
        fun f17() = t.testCond({it < 0}, g, 17)

        @Test
        fun f18() = t.testCond({it < 0}, g, 18)

        @Test
        fun f20() = t.testCond({it < 0}, g, 20)
    }
}