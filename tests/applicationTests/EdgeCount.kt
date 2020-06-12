package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.wrappers.genValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class EdgeCount {

    @Test   //1.1 vs 49 //data/network repository/bio/bio-dmela.mtx
    fun edgeCount_4_dmela() = assertEquals(6, genValue(FilePaths.bioDmela, EdgeCountFunction, 4))

    @Test    //3.5 vs 4.45
    fun edgeCount_6_infPower() = assertEquals(15, genValue(FilePaths.infPower, EdgeCountFunction, 6))

    @Test   //0.2 vs 0.2 vs 0.08
    fun edgeCount_4_usAir() = assertEquals(6, genValue(FilePaths.infUsAir, EdgeCountFunction, 4))

    @Test   //4.5 vs 20.6 vs 332
    fun edgeCount_9_dolphins() = assertEquals(23, genValue(FilePaths.outDolphins, EdgeCountFunction, 9))

    @Test    //8.9 vs 29 vs. 461
    fun edgeCount_10_euroRoad() = assertEquals(14, genValue(FilePaths.infEuroroad, EdgeCountFunction, 10))
}