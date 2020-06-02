package de.umr.fixcon.heuristics

import de.umr.core.GraphAlgorithms.inducedSubgraph
import de.umr.fixcon.heuristics.vertexPickers.DensePicker
import de.umr.fixcon.heuristics.vertexPickers.LaplacePicker
import de.umr.fixcon.heuristics.vertexPickers.SparsePicker
import de.umr.fixcon.heuristics.vertexPickers.VertexPicker
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.Graphs.neighborSetOf
import kotlin.math.log2
import kotlin.math.roundToInt

class LowerBoundGenerator(var problem: CFCO_Problem) {

    private val runCountMultiplicand: Double = 1.0  //only based off experience

    fun getBound(runs: Int = getRunCount()): Solution {

        require(problem.originalGraph.size >= problem.targetSize) { "Target-size may not be smaller than graph" }

        val bestLaplaceSolution = takeBestSolution(LaplacePicker(problem.originalGraph))
        val bestDenseSolution = takeBestSolution(DensePicker(problem.originalGraph))
        val bestSparseSolution = takeBestSolution(SparsePicker(problem.originalGraph))

        println("heuristic runs: $runs")
        println("laplace " + bestLaplaceSolution.value)
        println("dense " + bestDenseSolution.value)
        println("sparse " + bestSparseSolution.value)

        return listOf(bestLaplaceSolution, bestDenseSolution, bestSparseSolution).maxBy { it.value }!!
    }

    private fun generateConnectedSubgraph(picker: VertexPicker): Solution {
        val startVertex = picker.startVertex()
        val subgraphSet = HashSet<Int>(setOf(startVertex))
        val extensionSet = HashSet<Int>(neighborSetOf(problem.originalGraph, startVertex))

        while (subgraphSet.size != problem.targetSize) {
            if (extensionSet.isEmpty()) return Solution()
            val nextVertex = picker.extensionVertex(subgraphSet, extensionSet)
            subgraphSet += nextVertex
            extensionSet -= nextVertex
            extensionSet.addAll(neighborSetOf(problem.originalGraph, nextVertex) - subgraphSet)
        }

        val subGraph = inducedSubgraph(problem.originalGraph, subgraphSet)
        return Solution(subGraph, problem.function.eval(subGraph, problem.parameters))
    }

    private fun getRunCount(): Int = (log2(problem.originalGraph.size.toDouble()) * problem.targetSize * runCountMultiplicand).roundToInt()

    private fun takeBestSolution(picker: VertexPicker) = (1..getRunCount()).map { generateConnectedSubgraph(picker) }.maxBy { it.value }!!
}