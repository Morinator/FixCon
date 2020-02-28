package core;

import java.io.*;
import java.util.*;

public class HashGraph implements UndirectedGraph_I {

    private HashMap<Integer, NodeInfo> infoMap;
    private int edgeCount;

    HashGraph() {
        infoMap = new HashMap<>();
    }


    public HashGraph(File file) throws IOException {
        this();
        if (!file.exists()) {
            throw new FileNotFoundException("The file doesn't exist!");
        } else if (file.isDirectory()) {
            throw new FileNotFoundException("The file is a directory!");
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Scanner i;
        while ((line = br.readLine()) != null) {
            //only necessary if the file format hasNode additional annotations
            if (line.equals("") || line.startsWith("c") || line.startsWith("p"))
                continue;
            i = new Scanner(line);
            addEdge(i.nextInt(), i.nextInt());
            i.close();
        }
        br.close();
    }


    public void addNodes(Set<Integer> s) {
        for (Integer i : s) {
            if (!infoMap.containsKey(i))    //Only adds nodes that don't exist yet
            infoMap.put(i, new NodeInfo());
        }
    }


    public void addEdge(Integer v, Integer y) {
        if (v.equals(y))    //Only SIMPLE graphs are used -> no loops
            return;
        addNodes(Set.of(v, y));
        if (!infoMap.get(v).hasNB(y))
            edgeCount++;
        infoMap.get(v).addNB(y);
        infoMap.get(y).addNB(v);
    }


    public void deleteNode(Integer x) {
        if (!hasNode(x))
            return;
        Set<Integer> neighbours = infoMap.get(x).getNBs();
        edgeCount -= neighbours.size();
        for (int nb : neighbours)
            infoMap.get(nb).removeNB(x);    //"x" is no longer listed as a neighbour
        infoMap.remove(x);
        flushNodes(neighbours);     //deletes neighbours if they're now isolated
    }


    public void deleteEdge(Integer x, Integer y) {
        if (hasNode(x) && infoMap.get(x).hasNB(y)) {
            infoMap.get(x).removeNB(y);
            infoMap.get(y).removeNB(x);
            flushNodes(Set.of(x, y));
            edgeCount--;
        }
    }


    public boolean hasNode(Integer x) {
        return infoMap.containsKey(x);
    }


    public Integer degree(Integer x) {
        return hasNode(x) ? infoMap.get(x).degree() : 0;
    }


    public boolean adjacent(Integer x, Integer y) {
        return hasNode(x) && infoMap.get(x).hasNB(y);
    }


    public Set<Integer> getNeighbours(Integer x) {
        return infoMap.containsKey(x) ? new HashSet<>(infoMap.get(x).getNBs()) : new HashSet<>();
    }


    public int nodeCount() {
        return infoMap.size();
    }


    public boolean isEmpty() {
        return infoMap.isEmpty();
    }


    public int edgeCount() {
        return edgeCount;
    }


    public Set<Integer> getNodes() {
        return new HashSet<>(infoMap.keySet());
    }


    /**
     * Any node from s that has no neighbours gets removed in the graph.
     * @param s Set of nodes that is checked if they have degree 0 and get removed if so.
     */
    private void flushNodes (Set<Integer> s) {
        for (int i : s) {
            if (infoMap.get(i).degree() == 0)
                infoMap.remove(i);
        }
    }
}