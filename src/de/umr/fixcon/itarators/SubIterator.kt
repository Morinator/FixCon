package de.umr.fixcon.itarators

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.wrappers.CFCO_Problem
import de.umr.fixcon.wrappers.Solution


/**
 * Iterates through all *connected* subgraphs of *originalGraph* with *targetSize* vertices.
 * The current subgraph can be retrieved with [current], the next subgraph is generated with [mutate].
 * [isValid] returns *true* iff [current] contains a yet unseen subgraph of *targetSize*
 * and is false once this iterator is exhausted.
 *
 * After the constructor, [isValid] already returns true, except if *originalGraph* contains no fitting connected
 * subgraphs of *targetSize*
 */
class SubIterator(private val problem: CFCO_Problem, private val currBestSolution: Solution = Solution()) : GraphIterator<VertexOrderedGraph<Int>> {

    private var fixedIter = SubIteratorFromStart(problem, anyVertex())   //TODO why is currentBestValue not allowed??

    override fun isValid(): Boolean = fixedIter.isValid()

    override fun current(): VertexOrderedGraph<Int> = fixedIter.current()

    override fun mutate() {
        fixedIter.mutate()
        updateStartVertexIfNeeded()
    }

    private fun updateStartVertexIfNeeded() {
        while (!fixedIter.isValid() && problem.originalGraph.vertexCount > problem.targetSize) {
            problem.originalGraph.removeVertex(fixedIter.startVertex)
            fixedIter = SubIteratorFromStart(problem, anyVertex(), currBestSolution)
        }
    }

    /**@return An arbitrary vertex from the vertex-set of this original graph*/
    private fun anyVertex(): Int = problem.originalGraph.vertexSet().first()
}