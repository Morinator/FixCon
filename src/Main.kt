import de.umr.core.createClique
import de.umr.fixcon.Solver
import de.umr.fixcon.graphFunctions.standardFunctions.AcyclicFunction
import de.umr.fixcon.wrappers.CFCO_Problem

fun main() {
    println(Solver(CFCO_Problem(createClique(3), 20, AcyclicFunction)).solve().value)
}