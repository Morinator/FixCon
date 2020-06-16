package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.*
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.TriangleFreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TriangleFree {
    private val t = Tester(TriangleFreeFunction)

    @Nested
    internal inner class infUsAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f5() = t.test(1, g, 5)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f9() = t.test(1, g, 9)
    }

    @Nested
    internal inner class dmela {
        private val g = graphFromFile(BioDmela)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f14() = t.test(1, g, 14)

        @Test
        fun f16() = t.test(1, g, 15)
    }

    @Nested
    internal inner class socAdvogato {
        private val g = graphFromFile(SocAdvogato)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f10() = t.test(1, g, 10)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f5() = t.test(1, g, 5)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f9() = t.test(1, g, 9)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f12() = t.test(1, g, 12)

        @Test
        fun f16() = t.test(1, g, 16)

        @Test
        fun f18() = t.test(1, g, 18)

        @Test
        fun f19() = t.test(1, g, 19)

        @Test
        fun f20() = t.test(1, g, 20)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f7() = t.test(1, g, 7)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f9() = t.test(1, g, 9)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f4() = t.test(1, g, 4)

        @Test
        fun f6() = t.test(1, g, 6)

        @Test
        fun f8() = t.test(1, g, 8)

        @Test
        fun f10() = t.test(1, g, 10)

        @Test
        fun f15() = t.test(1, g, 15)

        @Test
        fun f20() = t.test(1, g, 20)
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
        fun f70_40() = t.test(0, createClique(70), 40)
    }

    @Nested
    internal inner class star {
        @Test
        fun f2_2() = t.test(1, createStar(2), 2)

        @Test
        fun f3_3() = t.test(1, createStar(3), 3)

        @Test
        fun f23_5() = t.test(1, createStar(23), 5)

        @Test
        fun f10_9() = t.test(1, createStar(10), 9)

        @Test
        fun f70_40() = t.test(1, createStar(70), 40)

        @Test
        fun f500_500() = t.test(1, createStar(200), 200)
    }

    @Nested
    internal inner class path {
        @Test
        fun f3_2() = t.test(1, createPath(3), 2)

        @Test
        fun f3_3() = t.test(1, createPath(3), 3)

        @Test
        fun f23_5() = t.test(1, createPath(23), 5)

        @Test
        fun f10_9() = t.test(1, createPath(10), 9)

        @Test
        fun f70_40() = t.test(1, createPath(70), 40)

        @Test
        fun f500_500() = t.test(1, createPath(500), 500)
    }

    @Nested
    internal inner class circle {
        @Test
        fun f3_2() = t.test(1, createCircle(3), 2)

        @Test
        fun f3_3() = t.test(0, createCircle(3), 3)

        @Test
        fun f23_5() = t.test(1, createCircle(23), 5)

        @Test
        fun f10_9() = t.test(1, createCircle(10), 9)

        @Test
        fun f70_40() = t.test(1, createCircle(70), 40)

        @Test
        fun f500_500() = t.test(1, createCircle(500), 500)
    }
}