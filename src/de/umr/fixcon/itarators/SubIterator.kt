package de.umr.fixcon.itarators

import de.umr.fixcon.wrappers.CFCO_Problem
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge


/**
 * Iterates through all *connected* subgraphs of *originalGraph* with *targetSize* vertices.
 * The current subgraph can be retrieved with [current], the next subgraph is generated with [mutate].
 * [isValid] returns *true* iff [current] contains a yet unseen subgraph of *targetSize*
 * and is false once this iterator is exhausted.
 *
 * After the constructor, [isValid] already returns true, except if *originalGraph* contains no fitting connected
 * subgraphs of *targetSize*
 */
class SubIterator(private val problem: CFCO_Problem) : GraphIterator<Graph<Int, DefaultEdge>> {
    private var subIteratorFromStart = SubIteratorFromStart(problem, anyVertex())

    /** @return *true* iff [current] contains a yet unseen subgraph of *targetSize*
     * and is false once this iterator is exhausted.
     */
    override fun isValid(): Boolean = subIteratorFromStart.isValid()

    /**@return The currently selected subgraph. It may return wrong results if [isValid] is false*/
    override fun current(): Graph<Int, DefaultEdge> = subIteratorFromStart.current()

    /**generates the next subgraph which can then be retrieved with [current]. It may also return wrong graphs once
     * the iterator is exhausted, in which case [isValid] turns false*/
    override fun mutate() { //fixed_subgraphIterator throws exception if it doesn't have next element
        subIteratorFromStart.mutate()
        while (!subIteratorFromStart.isValid() && problem.originalGraph.vertexSet().size > problem.subgraphSize) {
            problem.originalGraph.removeVertex(subIteratorFromStart.startVertex)
            subIteratorFromStart = SubIteratorFromStart(problem, anyVertex())
        }
    }

    /**@return An arbitrary vertex from the vertex-set of this original graph*/
    private fun anyVertex(): Int = problem.originalGraph.vertexSet().first()
}