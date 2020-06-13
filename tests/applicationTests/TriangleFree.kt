package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.*
import de.umr.core.createCircle
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.createStar
import de.umr.fixcon.graphFunctions.standardFunctions.TriangleFreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TriangleFree {
    private val t = Tester(TriangleFreeFunction)

    @Nested
    internal inner class infUsAir {
        @Test
        fun f4() = t.test(1, InfUsAir, 4)

        @Test
        fun f5() = t.test(1, InfUsAir, 5)

        @Test
        fun f7() = t.test(1, InfUsAir, 7)

        @Test
        fun f9() = t.test(1, InfUsAir, 9)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(1, BioDmela, 4)

        @Test
        fun f6() = t.test(1, BioDmela, 6)

        @Test
        fun f8() = t.test(1, BioDmela, 8)

        @Test
        fun f12() = t.test(1, BioDmela, 12)

        @Test
        fun f14() = t.test(1, BioDmela, 14)

        @Test
        fun f16() = t.test(1, BioDmela, 15)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(1, SocAdvogato, 4)

        @Test
        fun f6() = t.test(1, SocAdvogato, 6)

        @Test
        fun f8() = t.test(1, SocAdvogato, 8)

        @Test
        fun f10() = t.test(1, SocAdvogato, 10)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(1, CoPapersCiteseer, 4)

        @Test
        fun f5() = t.test(1, CoPapersCiteseer, 5)

        @Test
        fun f7() = t.test(1, CoPapersCiteseer, 7)

        @Test
        fun f9() = t.test(1, CoPapersCiteseer, 9)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(1, InfPower, 4)

        @Test
        fun f8() = t.test(1, InfPower, 8)

        @Test
        fun f12() = t.test(1, InfPower, 12)

        @Test
        fun f16() = t.test(1, InfPower, 16)

        @Test
        fun f18() = t.test(1, InfPower, 18)

        @Test
        fun f19() = t.test(1, InfPower, 19)

        @Test
        fun f20() = t.test(1, InfPower, 20)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f6() = t.test(1, OutDolphins, 6)

        @Test
        fun f7() = t.test(1, OutDolphins, 7)

        @Test
        fun f8() = t.test(1, OutDolphins, 8)

        @Test
        fun f9() = t.test(1, OutDolphins, 9)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(1, InfEuroRoad, 4)

        @Test
        fun f6() = t.test(1, InfEuroRoad, 6)

        @Test
        fun f8() = t.test(1, InfEuroRoad, 8)

        @Test
        fun f10() = t.test(1, InfEuroRoad, 10)

        @Test
        fun f15() = t.test(1, InfEuroRoad, 15)

        @Test
        fun f20() = t.test(1, InfEuroRoad, 20)
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