package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.bioDmela
import de.umr.FilePaths.coPapersCiteseer
import de.umr.FilePaths.infEuroroad
import de.umr.FilePaths.infPower
import de.umr.FilePaths.infUsAir
import de.umr.FilePaths.outDolphins
import de.umr.FilePaths.socAdvogato
import de.umr.core.createClique
import de.umr.fixcon.graphFunctions.standardFunctions.TriangleFreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TriangleFree {
    private val t = Tester(TriangleFreeFunction)

    @Nested
    internal inner class infUsAir {
        @Test
        fun f4() = t.test(1, infUsAir, 4)

        @Test
        fun f5() = t.test(1, infUsAir, 5)

        @Test
        fun f7() = t.test(1, infUsAir, 7)

        @Test
        fun f9() = t.test(1, infUsAir, 9)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(1, bioDmela, 4)

        @Test
        fun f6() = t.test(1, bioDmela, 6)

        @Test
        fun f8() = t.test(1, bioDmela, 8)

        @Test
        fun f12() = t.test(1, bioDmela, 12)

        @Test
        fun f14() = t.test(1, bioDmela, 14)

        @Test
        fun f16() = t.test(1, bioDmela, 15)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(1, socAdvogato, 4)

        @Test
        fun f6() = t.test(1, socAdvogato, 6)

        @Test
        fun f8() = t.test(1, socAdvogato, 8)

        @Test
        fun f10() = t.test(1, socAdvogato, 10)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(1, coPapersCiteseer, 4)

        @Test
        fun f5() = t.test(1, coPapersCiteseer, 5)

        @Test
        fun f7() = t.test(1, coPapersCiteseer, 7)

        @Test
        fun f9() = t.test(1, coPapersCiteseer, 9)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(1, infPower, 4)

        @Test
        fun f8() = t.test(1, infPower, 8)

        @Test
        fun f12() = t.test(1, infPower, 12)

        @Test
        fun f16() = t.test(1, infPower, 16)

        @Test
        fun f18() = t.test(1, infPower, 18)

        @Test
        fun f19() = t.test(1, infPower, 19)

        @Test
        fun f20() = t.test(1, infPower, 20)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f6() = t.test(1, outDolphins, 6)

        @Test
        fun f7() = t.test(1, outDolphins, 7)

        @Test
        fun f8() = t.test(1, outDolphins, 8)

        @Test
        fun f9() = t.test(1, outDolphins, 9)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = t.test(1, infEuroroad, 4)

        @Test
        fun f6() = t.test(1, infEuroroad, 6)

        @Test
        fun f8() = t.test(1, infEuroroad, 8)

        @Test
        fun f10() = t.test(1, infEuroroad, 10)

        @Test
        fun f15() = t.test(1, infEuroroad, 15)

        @Test
        fun f20() = t.test(1, infEuroroad, 20)
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
}