package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.IsDegreeConstrainedFunction
import de.umr.fixcon.wrappers.genValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class DegreeConstrained {

    @Disabled
    @Test //0.9 vs 7.7
    fun degreeConstrained3_5_size7_brightkite() = assertEquals(1, genValue(FilePaths.socBrightkite, IsDegreeConstrainedFunction, 7, listOf(3, 5)))

}