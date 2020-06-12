package applicationTests

import de.umr.FilePaths
import de.umr.core.createClique
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.standardFunctions.IsAcyclicFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.genValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Acyclic {

    @Test   //0.2 vs 0.0
    fun isAcyclic_9_infPower() = assertEquals(1, genValue(FilePaths.infPower, IsAcyclicFunction, 9))

    @Test   //0.2 vs 8.3 vs noDataFromUni
    fun isAcyclic_10_Clique() {
        val problem = CFCO_Problem(createClique(23), 9, IsAcyclicFunction, emptyList())
        assertEquals(0, Solver(problem).solve().value)
    }
}