package de.umr.fixcon.itarators

import de.umr.core.dataStructures.VertexOrderedGraph
import de.umr.fixcon.wrappers.CFCO_Problem


/**
 * Iterates through all *connected* subgraphs of *originalGraph* with *targetSize* vertices.
 * The current subgraph can be retrieved with [current], the next subgraph is generated with [mutate].
 * [isValid] returns *true* iff [current] contains a yet unseen subgraph of *targetSize*
 * and is false once this iterator is exhausted.
 *
 * After the constructor, [isValid] already returns true, except if *originalGraph* contains no fitting connected
 * subgraphs of *targetSize*
 */
class SubIterator(private val problem: CFCO_Problem) : GraphIterator<VertexOrderedGraph<Int>> {

    private var fixedIter = SubIteratorFromStart(problem, startVertex = anyVertex(), currBestValue = Int.MIN_VALUE)
    var currentBestValue: Int = Int.MIN_VALUE

    override fun isValid(): Boolean = fixedIter.isValid()

    override fun current(): VertexOrderedGraph<Int> = fixedIter.current()

    override fun mutate() {
        fixedIter.mutate()
        updateStartVertexIfNeeded()
        if (isValid()) {
            currentBestValue = kotlin.math.max(currentBestValue, fixedIter.currBestValue)
        }
    }

    private fun updateStartVertexIfNeeded() {
        while (!fixedIter.isValid() && problem.originalGraph.size > problem.targetSize) {
            problem.originalGraph.removeVertex(fixedIter.startVertex)
            fixedIter = SubIteratorFromStart(problem, anyVertex(), fixedIter.currBestValue)
        }
    }

    /**@return An arbitrary vertex from the vertex-set of this original graph*/
    private fun anyVertex(): Int = problem.originalGraph.vertexSet().first()
}