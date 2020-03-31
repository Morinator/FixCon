package de.umr.fixcon;

import com.google.common.graph.MutableGraph;
import de.umr.core.utils.FastList;

import java.util.*;

import static de.umr.core.utils.CollectionUtil.*;
import static java.util.stream.Collectors.toList;

public class Fixed_SubgraphIterator implements Iterator<Set<Integer>> {

    final int startVertex;
    private final int k;
    private final MutableGraph<Integer> graph;

    private List<Integer> subset_list = new ArrayList<>();
    private List<Integer> extension_list = new FastList<>();
    private Deque<Integer> pointerStack = new ArrayDeque<>(List.of(0));
    private Deque<Integer> privateStack = new ArrayDeque<>();

    Fixed_SubgraphIterator(MutableGraph<Integer> graph, int startVertex, int k) {
        if (k == 1) throw new IllegalArgumentException("Does not support search for subgraphs of size 1");
        this.startVertex = startVertex;
        this.graph = graph;
        this.k = k;
        subset_list.add(startVertex);
        extension_list.addAll(graph.adjacentNodes(startVertex));
        privateStack.push(extension_list.size());
        recalibrate();
    }

    @Override
    public boolean hasNext() {
        return subset_list.size() == k;
    }

    @Override
    public Set<Integer> next() {
        if (!hasNext()) throw new NoSuchElementException("hasNext() is false");
        Set<Integer> result_set = new LinkedHashSet<>(subset_list);
        recalibrate();
        return result_set;
    }

    private void recalibrate() {
        do {
            if (subset_list.size() == k) {
                delete_subset_head();
                continue;
            }
            if (pointerHead_is_outOfRange()) {  //size of subset is < k for following code:
                if (!pointerHead_is_pending())
                    delete_subset_head();
                delete_subset_head();
                pointerStack.pop();
            } else {                            //pivot is not out of range
                if (pointerHead_is_pending())
                    add_pivot();
                else
                    duplicate_stack_head(pointerStack);
            }
        } while (!hasNext() && pointerStack.size()>0);
    }

    /*for the last element in the subset it is not necessary to generate the extension-list, because the subset
    wont be extended further. Therefore in this case the adjustment of the extension-list is omitted.*/
    private void add_pivot() {
        if (subset_list.size() != k-1) {
            List<Integer> new_extension = get_new_extension(pivot_vertex());
            extension_list.addAll(new_extension);
            privateStack.push(new_extension.size());
        }
        subset_list.add(pivot_vertex());
        change_stack_head(pointerStack, 1);
    }

    /*Because for the last element in the subset the extension-list was not adjusted, it also doesn't need
    to be delete here in this case. This is the case if the size of the subset is k*/
    private void delete_subset_head() {
        if (subset_list.size() != k)
            list_remove_lastN(extension_list, privateStack.pop());  //pop() deletes head as side-effect
        list_remove_lastN(subset_list, 1);
    }

    private List<Integer> get_new_extension(int pivot_vertex) {
        return graph.adjacentNodes(pivot_vertex).stream()
                .filter(x -> !extension_list.contains(x) && x!=startVertex)
                .collect(toList());
    }

    private boolean pointerHead_is_outOfRange() {
        return pointerStack.getFirst() == extension_list.size();
    }

    private boolean pointerHead_is_pending() {
        return subset_list.size() == pointerStack.size();
    }

    private int pivot_vertex() {
        return extension_list.get(pointerStack.getFirst());
    }
}
