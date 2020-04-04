package de.umr.fixcon;

import com.google.common.graph.Graph;
import com.google.common.graph.MutableGraph;
import de.umr.core.FixedIterator;

public class SubIter implements FixedIterator<Graph<Integer>> {

    private final int k;
    private final MutableGraph<Integer> graph;
    private SubIter_fromStart sub_iter;

    SubIter(MutableGraph<Integer> graph, int k) {
        this.graph = graph;
        this.k = k;
        sub_iter = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
    }

    @Override
    public boolean isValid() {
        return sub_iter.isValid();
    }

    @Override
    public Graph<Integer> current() {
        return sub_iter.current();
    }

    @Override
    public void mutate() {    //fixed_subgraphIterator throws exception if it doesn't have next element
        sub_iter.mutate();
        while (!sub_iter.isValid()) {
            if (graph.nodes().size() <= k)
                break;
            else {
                graph.removeNode(sub_iter.getStartVertex());
                sub_iter = new SubIter_fromStart(graph, graph.nodes().iterator().next(), k);
            }
        }
    }
}