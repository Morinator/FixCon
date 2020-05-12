package benchmarks

import de.umr.core.GraphFileReader.graphFromNetworkRepo
import de.umr.core.StandardGraphFactory
import de.umr.fixcon.CFCOSolver
import de.umr.fixcon.graphFunctions.GraphFunction
import de.umr.fixcon.graphFunctions.standardFunctions.*
import de.umr.fixcon.wrappers.CFCO_Problem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class CFCOSolver_Test {

    private fun genValue(path: String, fu: GraphFunction, size: Int, args: List<Int> = listOf(1, 2, 3)): Int {
        val g = graphFromNetworkRepo(path)
        val solver = CFCOSolver(CFCO_Problem(g, size, fu, args))
        return solver.solve().value
    }

    @Test   //1.1 vs 49
    fun edgeCount_4_dmela() = assertEquals(6, genValue(".//graph_files//bio-dmela.mtx", EdgeCountFunction, 4))

    @Test    //3.5 vs 4.45
    fun edgeCount_6_infPower() = assertEquals(15, genValue(".//graph_files//inf-power.mtx", EdgeCountFunction, 6))

    @Test   //2.7 vs 5.84
    fun minDegree_6_infPower() = assertEquals(5, genValue(".//graph_files//inf-power.mtx", MinDegreeFunction, 6))

    @Test   //0.2 vs 0.0
    fun isAcyclic_9_infPower() = assertEquals(1, genValue(".//graph_files//inf-power.mtx", IsAcyclicFunction, 9))

    @Test   //0.2 vs 0.2 vs 0.08
    fun edgeCount_4_usAir() = assertEquals(6, genValue(".//graph_files//inf-USAir97.mtx", EdgeCountFunction, 4))

    @Test  @Disabled  //0.9 vs 7.7
    fun degreeConstrained3_5_size7_brightkite() = assertEquals(1, genValue(".//graph_files//soc-brightkite.mtx", IsDegreeConstrainedFunction, 7, listOf(3, 5)))

    @Test   //4.5 vs 20.6 vs 332
    fun edgeCount_9_dolphins() = assertEquals(23, genValue(".//graph_files//out.dolphins", EdgeCountFunction, 9))

    @Test   //2.2 vs 22 vs 458
    fun minDegree_9_dolphins() = assertEquals(4, genValue(".//graph_files//out.dolphins", MinDegreeFunction, 9))

    @Test    //8.9 vs 29 vs. 461
    fun edgeCount_10_euroRoad() = assertEquals(14, genValue(".//graph_files//inf-euroroad.edges", EdgeCountFunction, 10))

    @Test   //0.2 vs 8.3 vs unknown
    fun isAcyclic_10_Clique() {
        val problem = CFCO_Problem(StandardGraphFactory.createClique(23), 9, IsAcyclicFunction, emptyList())
        assertEquals(0, CFCOSolver(problem).solve().value)
    }
}