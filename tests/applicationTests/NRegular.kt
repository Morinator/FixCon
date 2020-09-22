package applicationTests

import de.umr.core.createClique
import de.umr.core.createPath
import de.umr.core.dataStructures.GraphFile.*
import de.umr.core.graphFromFile
import de.umr.fixcon.graphFunctions.RRegularFunction
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NRegular {

    /** Degree needs to be between 3 and 5 (both inclusive) for all tests.*/
    private val tEven = _TestingUtil(RRegularFunction(listOf(3), 1234))
    private val tOdd = _TestingUtil(RRegularFunction(listOf(4), 1234))

    @Nested
    internal inner class usAir {
        private val g = graphFromFile(InfUsAir)
        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)
    }

    @Nested
    internal inner class infPower {
        private val g = graphFromFile(InfPower)
        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)

        @Test
        fun f6() = tEven.test(0, g,6)
    }

    @Nested
    internal inner class euroRoad {
        private val g = graphFromFile(InfEuroRoad)
        @Test
        fun f4() = tEven.testCond({ it < 0 }, g,4)

        @Test
        fun f5() = tOdd.testCond({ it < 0 }, g,5)

        @Test
        fun f6() = tEven.testCond({ it < 0 }, g,6)

        @Test
        fun f7() = tOdd.testCond({ it < 0 }, g,7)
    }

    @Nested
    internal inner class coPapers {
        private val g = graphFromFile(CoPapersCiteseer)
        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)
    }

    @Nested
    internal inner class dolphins {
        private val g = graphFromFile(OutDolphins)
        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)

        @Test
        fun f6() = tEven.test(0, g,6)

        @Test
        fun f7() = tOdd.testCond({ it < 0 }, g,7)

        @Test
        fun f8() = tEven.test(0, g,8)
    }

    @Nested
    internal inner class brightkite {
        @Test
        fun f4() = tEven.test(0, SocBrightkite,4)
    }

    @Nested
    internal inner class clique {
        @Test
        fun f3() = tOdd.testCond({ it < 0 }, createClique(10),3)

        @Test
        fun f4() = tEven.test(0, createClique(10),4)

        @Test
        fun f5() = tOdd.test(0, createClique(10),5)

        @Test
        fun f6() = tEven.testCond({ it < 0 }, createClique(10),6)

        @Test
        fun f7() = tOdd.testCond({ it < 0 }, createClique(10),7)

        @Test
        fun f7_() = tOdd.testCond({ it < 0 }, createClique(20),7)
    }

    @Nested
    internal inner class path {
        @Test
        fun f5() = tOdd.testCond({ it < 0 }, createPath(10),5)

        @Test
        fun f6() = tEven.testCond({ it < 0 }, createPath(100),6)

        @Test
        fun f20() = tEven.testCond({ it < 0 }, createPath(100),20)

        @Test
        fun f50() = tEven.testCond({ it < 0 }, createPath(500),50)

        @Test
        fun f100() = tEven.testCond({ it < 0 }, createPath(100),100)
    }

    @Nested
    internal inner class MouseRetina {
        private val g = graphFromFile(MouseRetina)

        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)
    }

    @Nested
    internal inner class Heart2 {
        private val g = graphFromFile(Heart2)

        @Test
        fun f4() = tEven.test(0, g,4)

        @Test
        fun f5() = tOdd.test(0, g,5)
    }
}