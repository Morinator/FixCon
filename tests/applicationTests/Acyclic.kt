package applicationTests

import de.umr.FilePaths
import de.umr.core.createClique
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.standardFunctions.AcyclicFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Acyclic {

    private val t = Tester(AcyclicFunction)

    @Nested
    internal inner class infPower {
        @Test
        fun infPower_9() = t.test(1, FilePaths.infPower, 9)
    }

    @Nested
    internal inner class clique {
        @Test
        fun clique_10() =
                assertEquals(0, Solver(CFCO_Problem(createClique(23), 9, AcyclicFunction)).solve().value)
    }
}