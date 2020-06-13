package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths.bioDmela
import de.umr.FilePaths.coPapersCiteseer
import de.umr.FilePaths.infEuroroad
import de.umr.FilePaths.infPower
import de.umr.FilePaths.infUsAir
import de.umr.FilePaths.outDolphins
import de.umr.FilePaths.socBrightkite
import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.fixcon.graphFunctions.standardFunctions.NRegularFunction
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NRegular {

    /** Degree needs to be between 3 and 5 (both inclusive) for all tests.*/
    private val tEven = Tester(NRegularFunction, listOf(3))
    private val tOdd = Tester(NRegularFunction, listOf(4))

    @Nested
    internal inner class usAir {
        @Test
        fun f4() = tEven.test(1, infUsAir, 4)

        @Test
        fun f5() = tOdd.test(1, infUsAir, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = tEven.test(1, infPower, 4)

        @Test
        fun f5() = tOdd.test(1, infPower, 5)

        @Test
        fun f6() = tEven.test(1, infPower, 6)

        @Disabled   //takes about 17 seconds
        @Test
        fun f7() = tOdd.test(0, infPower, 7)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun f4() = tEven.test(0, infEuroroad, 4)

        @Test
        fun f5() = tOdd.test(0, infEuroroad, 5)

        @Test
        fun f6() = tEven.test(0, infEuroroad, 6)

        @Test
        fun f7() = tOdd.test(0, infEuroroad, 7)

        @Test
        fun f8() = tEven.test(0, infEuroroad, 8)

        @Disabled   //takes about 12 seconds
        @Test
        fun f9() = tOdd.test(0, infEuroroad, 9)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = tEven.test(1, coPapersCiteseer, 4)

        @Test
        fun f5() = tOdd.test(1, coPapersCiteseer, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = tEven.test(1, bioDmela, 4)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun f4() = tEven.test(1, outDolphins, 4)

        @Test
        fun f5() = tOdd.test(1, outDolphins, 5)

        @Test
        fun f6() = tEven.test(1, outDolphins, 6)

        @Test
        fun f7() = tOdd.test(0, outDolphins, 7)

        @Test
        fun f8() = tEven.test(1, outDolphins, 8)

        @Disabled   //36 vs. 365
        @Test
        fun f9() = tOdd.test(0, outDolphins, 9)
    }

    @Nested
    internal inner class brightkite {
        @Test
        fun f4() = tEven.test(1, socBrightkite, 4)
    }

    @Nested
    internal inner class clique {
        @Test
        fun f3() = tOdd.test(0, createClique(10), 3)

        @Test
        fun f4() = tEven.test(1, createClique(10), 4)

        @Test
        fun f5() = tOdd.test(1, createClique(10), 5)

        @Test
        fun f6() = tEven.test(0, createClique(10), 6)

        @Test
        fun f7() = tOdd.test(0, createClique(10), 7)

        @Test
        fun f7_() = tOdd.test(0, createClique(20), 7)
    }

    @Nested
    internal inner class path {
        @Test
        fun f5() = tOdd.test(0, createPath(10), 5)

        @Test
        fun f6() = tEven.test(0, createPath(100), 6)

        @Test
        fun f20() = tEven.test(0, createPath(100), 20)

        @Test
        fun f50() = tEven.test(0, createPath(500), 50)

        @Test
        fun f100() = tEven.test(0, createPath(100), 100)
    }
}