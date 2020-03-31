package de.umr.fixcon;

import de.umr.core.graphs.UndirectedGraph;

import java.util.Iterator;
import java.util.Set;

public class SubgraphIterator implements Iterator<Set<Integer>> {

    private final int k;
    private final UndirectedGraph graph;
    private Fixed_SubgraphIterator fixed_subgraphIterator;

    public SubgraphIterator(UndirectedGraph graph, int k) {
        this.graph = graph;
        this.k = k;
        fixed_subgraphIterator = new Fixed_SubgraphIterator(graph, graph.getVertices().iterator().next(), k);
    }


    @Override
    public boolean hasNext() {
        return fixed_subgraphIterator.hasNext();
    }

    @Override
    public Set<Integer> next() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        Set<Integer> next_subGraph = fixed_subgraphIterator.next();
        adjust_FixedSubgraphIterator();
        return next_subGraph;
    }

    private void adjust_FixedSubgraphIterator() {
        while (!fixed_subgraphIterator.hasNext() && graph.vertexCount() > k) {
            graph.removeVertex(fixed_subgraphIterator.startVertex);
            fixed_subgraphIterator = new Fixed_SubgraphIterator(graph, graph.getVertices().iterator().next(), k);
        }
    }
}