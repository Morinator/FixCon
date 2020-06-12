package applicationTests

import applicationTests.util.Tester
import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.DegreeConstrainedFunction
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DegreeConstrained {

    private val t = Tester(DegreeConstrainedFunction)

    @Nested
    internal inner class brightkite {
        @Disabled
        @Test
        fun f7__3_5() = t.test(1, FilePaths.socBrightkite, 7, listOf(3, 5))
    }
}