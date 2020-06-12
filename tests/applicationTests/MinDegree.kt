package applicationTests

import de.umr.FilePaths.bioDmela
import de.umr.FilePaths.coPapersCiteseer
import de.umr.FilePaths.infEuroroad
import de.umr.FilePaths.infPower
import de.umr.FilePaths.infUsAir
import de.umr.FilePaths.outDolphins
import de.umr.FilePaths.socAdvogato
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private class MinDegree {
    private val t = Tester(MinDegreeFunction)

    @Nested
    internal inner class infUsAir {
        @Test
        fun usAir_4() = t.test(3, infUsAir, 4)
        @Test
        fun usAir_5() = t.test(4, infUsAir, 5)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun dmela_4() = t.test(3, bioDmela, 4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun socAdvogato_4() = t.test(3, socAdvogato, 4)
    }

    @Nested
    internal inner class coPapers {
        @Test
        fun coPapers_4() = t.test(3, coPapersCiteseer, 4)
        @Test
        fun coPapers_5() = t.test(4, coPapersCiteseer, 5)
    }

    @Nested
    internal inner class infPower {
        @Test
        fun infPower_4() = t.test(3, infPower, 4)
        @Test
        fun infPower_5() = t.test(4, infPower, 5)
        @Test
        fun infPower_6() = t.test(5, infPower, 6)
        @Test
        fun infPower_7() = t.test(3, infPower, 7)
    }

    @Nested
    internal inner class dolphins {
        @Test
        fun dolphins_6() = t.test(4, outDolphins, 6)
        @Test
        fun dolphins_7() = t.test(4, outDolphins, 7)
        @Test
        fun dolphins_8() = t.test(3, outDolphins, 8)
        @Test
        fun dolphins_9() = t.test(4, outDolphins, 9)
    }

    @Nested
    internal inner class euroRoad {
        @Test
        fun euroRoad_4() = t.test(2, infEuroroad, 4)
        @Test
        fun euroRoad_5() = t.test(2, infEuroroad, 5)
        @Test
        fun euroRoad_6() = t.test(2, infEuroroad, 6)
        @Test
        fun euroRoad_7() = t.test(2, infEuroroad, 7)
        @Test
        fun euroRoad_8() = t.test(2, infEuroroad, 8)
        @Test
        fun euroRoad_9() = t.test(2, infEuroroad, 9)
    }
}