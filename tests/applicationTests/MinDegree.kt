package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import de.umr.fixcon.wrappers.genValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private class MinDegree {

    @Test   //2.7 vs 5.84
    fun minDegree_6_infPower() = assertEquals(5, genValue(FilePaths.infPower, MinDegreeFunction, 6))

    @Test   //2.2 vs 22 vs 458
    fun minDegree_9_dolphins() = assertEquals(4, genValue(FilePaths.outDolphins, MinDegreeFunction, 9))
}