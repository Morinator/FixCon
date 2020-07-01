package applicationTests

import applicationTests.util.Tester
import de.umr.GraphFile.*
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.MinDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private class MinDegree {
    private val t = Tester(MinDegreeFunction(1234))

    @Nested
    internal inner class infUsAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = t.test(3, g,4)

        @Test
        fun f5() = t.test(4, g,5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(3, BioDmela,4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(3, SocAdvogato,4)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = t.test(3, g,4)

        @Test
        fun f5() = t.test(4, g,5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f4() = t.test(3, g,4)

        @Test
        fun f5() = t.test(4, g,5)

        @Test
        fun f6() = t.test(5, g,6)

        @Test
        fun f7() = t.test(3, g,7)

        @Test
        fun f8() = t.test(4, g,8)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f6() = t.test(4, g,6)

        @Test
        fun f7() = t.test(4, g,7)

        @Test
        fun f8() = t.test(3, g,8)

        @Test
        fun f9() = t.test(4, g,9)

        @Test
        fun f10() = t.test(4, g,10)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f4() = t.test(2, g,4)

        @Test
        fun f5() = t.test(2, g,5)

        @Test
        fun f6() = t.test(2, g,6)

        @Test
        fun f7() = t.test(2, g,7)

        @Test
        fun f8() = t.test(2, g,8)

        @Test
        fun f9() = t.test(2, g,9)
    }

    @Nested
    internal inner class MouseRetina {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(3, g,4)

        @Test
        fun f5() = t.test(4, g,5)

        @Test
        fun f6() = t.test(5, g,6)

        @Test
        fun f7() = t.test(6, g,7)

        @Test
        fun f8() = t.test(7, g,8)

        @Test
        fun f9() = t.test(8, g,9)

        @Test
        fun f10() = t.test(9, g,10)

        @Test
        fun f11() = t.test(10, g,11)

        @Test
        fun f12() = t.test(11, g,12)

        @Test
        fun f13() = t.test(12, g,13)

        @Test
        fun f14() = t.test(13, g,14)

        @Test
        fun f15() = t.test(14, g,15)

        @Test
        fun f16() = t.test(15, g,16)
    }

    @Nested
    internal inner class Heart2 {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = t.test(3, g,4)

        @Test
        fun f5() = t.test(4, g,5)

        @Test
        fun f6() = t.test(5, g,6)

        @Test
        fun f7() = t.test(6, g,7)

        @Test
        fun f8() = t.test(7, g,8)

        @Test
        fun f9() = t.test(8, g,9)

        @Test
        fun f10() = t.test(9, g,10)

        @Test
        fun f11() = t.test(10, g,11)

        @Test
        fun f12() = t.test(11, g,12)

        @Test
        fun f13() = t.test(12, g,13)
    }

    @Nested
    internal inner class CSphd {
        private val g = graphFromFile(CSphd)

        @Test
        fun f4() = t.test(2, g,4)

        @Test
        fun f5() = t.test(2, g,5)

        @Test
        fun f6() = t.test(2, g,6)

        @Test
        fun f7() = t.test(2, g,7)
    }

}