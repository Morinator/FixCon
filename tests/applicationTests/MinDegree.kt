package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.MinDegreeFunction
import org.junit.jupiter.api.Test

private class MinDegree {

    private val t = Tester(MinDegreeFunction)

    @Test
    fun minDegree_6_infPower() = t.test(5, FilePaths.infPower, 6)

    @Test
    fun minDegree_9_dolphins() = t.test(4, FilePaths.outDolphins, 9)
}