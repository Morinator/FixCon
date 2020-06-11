package applicationTests

import de.umr.FilePaths
import de.umr.core.createClique
import de.umr.core.graphFromFile
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.graphFunctions.standardFunctions.IsAcyclicFunction
import de.umr.fixcon.graphFunctions.standardFunctions.IsDegreeConstrainedFunction
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import de.umr.fixcon.wrappers.CFCO_Problem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CFCOSolver_Test {

    private fun genValue(path: String, fu: GraphFunction, targetSize: Int, args: List<Int> = emptyList()): Int =
            Solver(CFCO_Problem(graphFromFile(path), targetSize, fu, args)).solve().value

    @Nested
    inner class edgeCount_Tests {

        @Test   //1.1 vs 49 //data/network repository/bio/bio-dmela.mtx
        fun edgeCount_4_dmela() = assertEquals(6, genValue(FilePaths.bioDmela, EdgeCountFunction, 4))

        @Test    //3.5 vs 4.45
        fun edgeCount_6_infPower() = assertEquals(15, genValue(FilePaths.infPower, EdgeCountFunction, 6))

        @Test   //0.2 vs 0.2 vs 0.08
        fun edgeCount_4_usAir() = assertEquals(6, genValue(FilePaths.infUsAir, EdgeCountFunction, 4))

        @Disabled @Test   //4.5 vs 20.6 vs 332
        fun edgeCount_9_dolphins() = assertEquals(23, genValue(FilePaths.outDolphins, EdgeCountFunction, 9))

        @Test    //8.9 vs 29 vs. 461
        fun edgeCount_10_euroRoad() = assertEquals(14, genValue(FilePaths.infEuroroad, EdgeCountFunction, 10))
    }

    @Test   //2.7 vs 5.84
    fun minDegree_6_infPower() = assertEquals(5, genValue(FilePaths.infPower, MinDegreeFunction, 6))

    @Test   //0.2 vs 0.0
    fun isAcyclic_9_infPower() = assertEquals(1, genValue(FilePaths.infPower, IsAcyclicFunction, 9))

    @Test  @Disabled  //0.9 vs 7.7
    fun degreeConstrained3_5_size7_brightkite() = assertEquals(1, genValue(FilePaths.socBrightkite, IsDegreeConstrainedFunction, 7, listOf(3, 5)))

    @Test   //2.2 vs 22 vs 458
    fun minDegree_9_dolphins() = assertEquals(4, genValue(FilePaths.outDolphins, MinDegreeFunction, 9))

    @Test   //0.2 vs 8.3 vs noDataFromUni
    fun isAcyclic_10_Clique() {
        val problem = CFCO_Problem(createClique(23), 9, IsAcyclicFunction, emptyList())
        assertEquals(0, Solver(problem).solve().value)
    }
}