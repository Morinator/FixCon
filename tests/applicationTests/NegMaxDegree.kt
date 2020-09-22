package applicationTests

import de.umr.core.createClique
import de.umr.core.dataStructures.GraphFile.*
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.NegMaxDegreeFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NegMaxDegree {
    private val t = _TestingUtil(NegMaxDegreeFunction(1234))

    @Nested
    internal inner class infUsAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = t.test(-2, g,4)

        @Test
        fun f5() = t.test(-2, g,5)

        @Test
        fun f7() = t.test(-2, g,7)

        @Test
        fun f8() = t.test(-2, g,8)

        @Test
        fun f9() = t.test(-2, g,9)
    }

    @Nested
    internal inner class dmela {
        @Test
        fun f4() = t.test(-2, BioDmela,4)
    }

    @Nested
    internal inner class socAdvogato {
        @Test
        fun f4() = t.test(-2, SocAdvogato,4)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = t.test(-2, g,4)

        @Test
        fun f5() = t.test(-2, g,5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f6() = t.test(-2, g,6)

        @Test
        fun f7() = t.test(-2, g,7)

        @Test
        fun f12() = t.test(-2, g,12)

        @Test
        fun f17() = t.test(-2, g,17)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f6() = t.test(-2, g,6)

        @Test
        fun f8() = t.test(-2, g,8)

        @Test
        fun f10() = t.test(-2, g,10)

        @Test
        fun f12() = t.test(-2, g,13)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f4() = t.test(-2, g,4)

        @Test
        fun f8() = t.test(-2, g,8)

        @Test
        fun f12() = t.test(-2, g,12)

        @Test
        fun f16() = t.test(-2, g,16)

        @Test
        fun f18() = t.test(-2, g,18)

        @Test
        fun f19() = t.test(-2, g,19)

        @Test
        fun f20() = t.test(-2, g,20)
    }

    @Nested
    internal inner class clique {

        @Test
        fun f5() = t.test(-2, createClique(5),3)

        @Test
        fun f10() = t.test(-7, createClique(10),8)

        @Test
        fun f20() = t.test(-5, createClique(20),6)

        @Test
        fun f30() = t.test(-3, createClique(30),4)
    }
}