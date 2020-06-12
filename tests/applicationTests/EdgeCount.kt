package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


private class EdgeCount {

    private val t = Tester(EdgeCountFunction)

    @Nested
    internal inner class usAir {
        @Test
        fun f4() = t.test(6, FilePaths.infUsAir, 4)
        @Test
        fun f5() = t.test(10, FilePaths.infUsAir, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(6, FilePaths.bioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(6, FilePaths.socAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun f4() = t.test(6, FilePaths.coPapersCiteseer, 4)

        @Test
        fun f5() = t.test(10, FilePaths.coPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun f4() = t.test(6, FilePaths.infPower, 4)

        @Test
        fun f5() = t.test(10, FilePaths.infPower, 5)

        @Test
        fun f6() = t.test(15, FilePaths.infPower, 6)
    }

    @Nested
    internal inner class  dolphins {
        @Test
        fun f9() = t.test(23, FilePaths.outDolphins, 9)
    }

    @Nested
    internal inner class  euroRoad {
        @Test @Disabled //takes nearly 1 minute
        fun f10() = t.test(14, FilePaths.infEuroroad, 10)
    }

}