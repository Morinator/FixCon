package applicationTests

import applicationTests.util.Tester
import de.umr.GraphFile.*
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.AcyclicFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Acyclic {

    val t = Tester(AcyclicFunction(1234))

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)

        @Test
        fun f9() = t.test(1, g, 9)

        @Test
        fun f14() = t.test(1, g, 14)

        @Test
        fun f18() = t.test(1, g, 18)

        @Test
        fun f20() = t.test(1, g, 20)
    }

    @Nested
    internal inner class usAir {
        private val g = graphFromFile(InfUsAir)

        @Test
        fun f4() = t.test(1, g, 5)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f9() = t.test(1, g, 9)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)

        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f9() = t.test(1, g, 9)
    }

    @Nested
    internal inner class euroRoad {
        private val g = InfEuroRoad

        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f9() = t.test(1, g, 9)

        @Test
        fun f13() = t.test(1, g, 13)

        @Test
        fun f17() = t.test(1, g, 17)

        @Test
        fun f20() = t.test(1, g, 20)
    }

    @Nested
    internal inner class openFlights {
        private val g = graphFromFile(InfOpenFlights)

        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f8() = t.test(1, g, 8)
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
        val g = SocBrightkite

        @Test
        fun f5() = t.test(1, g, 5)

        @Test
        fun f10() = t.test(1, g, 10)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f14() = t.test(1, g, 14)
    }

    @Nested
    internal inner class caSandiAuths {
        private val g = graphFromFile(CaSandiAuths)

        @Test
        fun f2() = t.test(1, g, 2)

        @Test
        fun f10() = t.test(1, g, 10)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f14() = t.test(1, g, 14)

        @Test
        fun f15() = t.test(1, g, 15)
    }

    @Nested
    internal inner class MouseRetina {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f5() = t.test(1, g, 5)

        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f9() = t.test(1, g, 9)

        @Test
        fun f10() = t.test(1, g, 10)

        @Test
        fun f11() = t.test(1, g, 11)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f13() = t.test(1, g, 13)

        @Test
        fun f14() = t.test(1, g, 14)

        @Test
        fun f15() = t.test(1, g, 15)

        @Test
        fun f16() = t.test(1, g, 16)

        @Test
        fun f17() = t.test(1, g, 17)

        @Test
        fun f18() = t.test(1, g, 18)

        @Test
        fun f19() = t.test(1, g, 19)

        @Test
        fun f20() = t.test(1, g, 20)
    }

    @Nested
    internal inner class Heart2 {
        private val g = graphFromFile(Heart2)

        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f5() = t.test(1, g, 5)

        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f9() = t.test(1, g, 9)

        @Test
        fun f10() = t.test(1, g, 10)

        @Test
        fun f11() = t.test(1, g, 11)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f13() = t.test(1, g, 13)

        @Test
        fun f14() = t.test(1, g, 14)

        @Test
        fun f15() = t.test(1, g, 15)

        @Test
        fun f16() = t.test(1, g, 16)

        @Test
        fun f17() = t.test(1, g, 17)

        @Test
        fun f18() = t.test(1, g, 18)

        @Test
        fun f19() = t.test(1, g, 19)

        @Test
        fun f20() = t.test(1, g, 20)
    }

}