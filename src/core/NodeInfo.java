package core;

import java.util.HashSet;
import java.util.Set;

public class NodeInfo {

    private HashSet<Integer> neighbours;


    NodeInfo() {
        neighbours = new HashSet<>();
    }


    public void addNB(Integer x) {
        neighbours.add(x);
    }


    public void removeNB(Integer x) {
        neighbours.remove(x);
    }


    public Set<Integer> getNBs() {
        return neighbours;
    }


    public boolean hasNB(int x) {
        return neighbours.contains(x);
    }


    public int degree() {
        return neighbours.size();
    }
}
