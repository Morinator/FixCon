package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.DegreeConstrainedFunction
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class DegreeConstrained {

    private val t = Tester(DegreeConstrainedFunction)

    @Disabled
    @Test
    fun degreeConstrained3_5_size7_brightkite() = t.test(1, FilePaths.socBrightkite, 7, listOf(3, 5))
}