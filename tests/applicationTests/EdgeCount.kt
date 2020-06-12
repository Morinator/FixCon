package applicationTests

import de.umr.FilePaths
import de.umr.fixcon.graphFunctions.standardFunctions.EdgeCountFunction
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


internal class EdgeCount {

    private val t = Tester(EdgeCountFunction)

    @Test
    fun usAir_4() = t.test(6, FilePaths.infUsAir, 4)

    @Test
    fun usAir_5() = t.test(10, FilePaths.infUsAir, 5)

    @Test
    fun dmela_4() = t.test(6, FilePaths.bioDmela, 4)

    @Test
    fun socAdvogato_4() = t.test(6, FilePaths.socAdvogato, 4)

    @Test
    fun coPapers_4() = t.test(6, FilePaths.coPapersCiteseer, 4)

    @Test
    fun coPapers_5() = t.test(10, FilePaths.coPapersCiteseer, 5)

    @Test
    fun infPower_4() = t.test(6, FilePaths.infPower, 4)

    @Test
    fun infPower_5() = t.test(10, FilePaths.infPower, 5)

    @Test
    fun infPower_6() = t.test(15, FilePaths.infPower, 6)

    @Test
    fun dolphins_9() = t.test(23, FilePaths.outDolphins, 9)

    @Test @Disabled
    fun euroRoad_10() = t.test(14, FilePaths.infEuroroad, 10)
}