package de.umr.core.graphs.linked_hash_graph;

import de.umr.core.graphs.Abstract_UndirectedGraph;
import de.umr.core.graphs.UndirectedGraph;

import java.util.*;

/**
 * This class implements the interface UndirectedGraph using
 */
final public class LinkedHashGraph extends Abstract_UndirectedGraph implements UndirectedGraph {

    //Uses LINKEDHashSet instead of HashSet for fast iterations
    private Map<Integer, LinkedHashSet<Integer>> map;

    public LinkedHashGraph() {
        map = new LinkedHashMap<>();
    }

    public LinkedHashGraph(Collection<Integer[]> allEdges) {
        this();
        allEdges.forEach(edge -> addEdge(edge[0], edge[1]));
    }

    private boolean addVertices(Integer... s) {
        boolean has_changed = Arrays.stream(s).anyMatch(i -> !map.containsKey(i));
        Arrays.stream(s).filter(i -> !map.containsKey(i)).forEach(i -> map.put(i, new LinkedHashSet<>()));
        return has_changed;
    }

    public boolean addEdge(Integer x, Integer y) {
        boolean has_changed = !areNeighbours(x, y) && !x.equals(y);
        if (has_changed) {
            addVertices(x, y);
            map.get(x).add(y);
            map.get(y).add(x);
        }
        return has_changed;
    }

    public boolean removeVertex(Integer x) {
        boolean has_changed = getVertices().contains(x);
        if (has_changed) {
            Set<Integer> neighbours = map.get(x);
            neighbours.forEach(i -> map.get(i).remove(x));  //"x" is no longer listed as a neighbour
            map.remove(x);
            remove_vertices_if_isolated(neighbours);     //removes neighbours if they're now isolated
        }
        return has_changed;
    }

    public boolean removeEdge(Integer x, Integer y) {
        boolean has_changed = map.get(x).remove(y);
        map.get(y).remove(x);
        remove_vertices_if_isolated(x, y);
        return has_changed;
    }

    public Set<Integer> getNeighbours(Integer x) {
        return map.getOrDefault(x, null);
    }

    public Set<Integer> getVertices() {
        return map.keySet();
    }

}