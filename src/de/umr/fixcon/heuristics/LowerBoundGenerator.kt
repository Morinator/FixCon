package de.umr.fixcon.heuristics

import de.umr.core.openNB
import de.umr.core.vertexCount
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution
import org.jgrapht.graph.AsSubgraph
import kotlin.math.log2
import kotlin.math.roundToInt

class LowerBoundGenerator<V>(private var problem: CFCO_Problem<V>) {

    private val runCountMultiplicand: Double = 1.0  //only based off experience

    fun getBound(runs: Int = getRunCount()): Solution<V> {

        fun takeBestSolution(picker: VertexPickers<V>) = (1..getRunCount()).map { generateConnectedSubgraph(picker) }.maxBy { it.value }!!

        require(problem.originalGraph.vertexCount >= problem.targetSize) { "Target-size may not be smaller than graph" }
        println("heuristic runs: $runs")

        val laplaceSolution = takeBestSolution(LaplacePicker<V>(problem.originalGraph))
        println("laplace " + laplaceSolution.value)

        val randomDenseSolution = takeBestSolution(RandomDensePicker<V>(problem.originalGraph))
        println("dense random " + randomDenseSolution.value)

        val denseSolution = takeBestSolution(GreedyDensePicker<V>(problem.originalGraph))
        println("dense greedy " + denseSolution.value)

        val randomSparseSolution = takeBestSolution(RandomSparsePicker<V>(problem.originalGraph))
        println("sparse random " + randomSparseSolution.value)

        val sparseSolution = takeBestSolution(GreedySparsePicker<V>(problem.originalGraph))
        println("sparse greedy " + sparseSolution.value)

        return listOf(laplaceSolution, randomDenseSolution, randomSparseSolution, denseSolution, sparseSolution).maxBy { it.value }!!
    }

    private fun generateConnectedSubgraph(picker: VertexPickers<V>): Solution<V> {
        val startVertex = picker.startVertex()
        val subgraphSet = hashSetOf(startVertex)
        val extensionSet = HashSet<V>(problem.originalGraph.openNB(startVertex))

        while (subgraphSet.size != problem.targetSize) {
            if (extensionSet.isEmpty()) return Solution()
            val nextVertex = picker.extensionVertex(subgraphSet, extensionSet)
            subgraphSet += nextVertex
            extensionSet -= nextVertex
            extensionSet.addAll(problem.originalGraph.openNB(nextVertex) - subgraphSet)
        }

        val subGraph = AsSubgraph(problem.originalGraph, subgraphSet)
        return Solution(subGraph, problem.function.eval(subGraph, problem.parameters))
    }

    private fun getRunCount(): Int = (log2(problem.originalGraph.vertexCount.toDouble()) * problem.targetSize * runCountMultiplicand).roundToInt()
}