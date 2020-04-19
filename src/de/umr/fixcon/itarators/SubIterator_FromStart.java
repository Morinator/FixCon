package de.umr.fixcon.itarators;

import com.google.common.collect.Iterables;
import com.google.common.graph.*;
import de.umr.core.Mutator;
import de.umr.core.utils.FastList;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

public final class SubIterator_FromStart implements Mutator<Graph<Integer>> {

    int searchTreeCounter = 0;
    private final int startVertex;
    private final int targetSize;
    private final MutableGraph<Integer> originalGraph;

    private final MutableGraph<Integer> subgraph = GraphBuilder.undirected().nodeOrder(ElementOrder.insertion()).build();
    private List<Integer> extension_list = new FastList<>();
    private final Deque<Integer> pointerStack = new LinkedList<>(List.of(0));
    private final Deque<Integer> privateStack = new LinkedList<>();

    public SubIterator_FromStart(MutableGraph<Integer> originalGraph, int startVertex, int targetSize) {
        checkArgument(targetSize > 1, "Only supports search for subgraphs of size greater 2");
        this.originalGraph = checkNotNull(originalGraph);
        this.startVertex = startVertex;
        this.targetSize = targetSize;
        subgraph.addNode(startVertex);
        extension_list.addAll(originalGraph.adjacentNodes(startVertex));
        privateStack.push(extension_list.size());
        mutate();
    }

    int startVertex() {
        return startVertex;
    }

    @Override
    public boolean isValid() {
        return subgraph.nodes().size() == targetSize;
    }

    @Override
    public Graph<Integer> current() {
        return subgraph;
    }

    @Override
    public void mutate() {
        do {
            if (subgraph.nodes().size() == targetSize) {
                delete_subset_head();
            } else {
                if (pointerHead_is_outOfRange()) {  //size of subset is < k for following code:
                    if (!pointerHead_is_pending())
                        delete_subset_head();
                    delete_subset_head();
                    pointerStack.pop();
                } else {                            //pivot is not out of range
                    if (pointerHead_is_pending())
                        add_pivot();
                    else
                        pointerStack.push(pointerStack.peek());
                }
            }

        } while (!isValid() && pointerStack.size() > 0);
    }

    /*for the last element in the subset it is not necessary to generate the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private void add_pivot() {
        if (subgraph.nodes().size() != targetSize - 1) {
            List<Integer> new_extension = get_new_extension(pivot_vertex());
            extension_list.addAll(new_extension);
            privateStack.push(new_extension.size());
        }
        originalGraph.adjacentNodes(pivot_vertex()).stream().filter(x -> subgraph.nodes().contains(x))
                .forEach(x -> subgraph.putEdge(pivot_vertex(), x));
        pointerStack.push(pointerStack.pop() + 1);
        searchTreeCounter++;
    }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is targetSize*/
    private void delete_subset_head() {
        if (subgraph.nodes().size() != targetSize)
            extension_list = extension_list.subList(0, extension_list.size() - privateStack.pop());
        subgraph.removeNode(Iterables.getLast(subgraph.nodes()));
    }

    private List<Integer> get_new_extension(int pivot_vertex) {
        return originalGraph.adjacentNodes(pivot_vertex).stream()
                .filter(x -> !extension_list.contains(x) && x != startVertex)
                .collect(toList());
    }

    private boolean pointerHead_is_outOfRange() {
        return pointerStack.getFirst() == extension_list.size();
    }

    private boolean pointerHead_is_pending() {
        return subgraph.nodes().size() == pointerStack.size();
    }

    private int pivot_vertex() {
        return extension_list.get(pointerStack.getFirst());
    }
}
