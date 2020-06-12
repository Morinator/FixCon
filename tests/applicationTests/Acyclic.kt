package applicationTests

import de.umr.FilePaths
import de.umr.core.createClique
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.standardFunctions.AcyclicFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Acyclic {

    private val t = Tester(AcyclicFunction)

    @Test
    fun isAcyclic_9_infPower() = t.test(1, FilePaths.infPower, 9)

    @Test
    fun isAcyclic_10_Clique() =
            assertEquals(0, Solver(CFCO_Problem(createClique(23), 9, AcyclicFunction)).solve().value)
}