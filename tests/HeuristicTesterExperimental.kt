import de.umr.FilePaths.InfPower
import de.umr.core.io.graphFromFile
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import de.umr.fixcon.heuristics.getDenseSolution
import de.umr.fixcon.wrappers.Problem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeuristicTesterExperimental {

    @Test
    fun x() {
        val p = Problem(graphFromFile(InfPower), 5, EdgeCountFunction)
        repeat(50) {
            val sol = getDenseSolution(p)
            print("${sol.value} ")
            assertEquals(sol.value, EdgeCountFunction.eval(sol.subgraph))
        }
    }
}